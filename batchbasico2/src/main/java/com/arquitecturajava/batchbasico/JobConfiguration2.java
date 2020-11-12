package com.arquitecturajava.batchbasico;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.arquitecturajava.batchbasico.ficherostexto.Factura;
import com.arquitecturajava.batchbasico.ficherostexto.ItemBasicoFacturaProcessor;
import com.arquitecturajava.batchbasico.ficherostexto.ItemBasicoFacturaReader;
import com.arquitecturajava.batchbasico.ficherostexto.ItemBasicoFacturaWriter;
import com.arquitecturajava.batchbasico.ficherostexto.ItemBasicoReader;
import com.arquitecturajava.batchbasico.ficherostexto.ItemBasicoWriter;
import com.arquitecturajava.batchbasico.jobduplicar.CopiarCarpeta;
import com.arquitecturajava.batchbasico.jobduplicar.CopiarFicheros;
import com.arquitecturajava.batchbasico.jobduplicar.CopiarFicherosMayusculas;
import com.arquitecturajava.batchbasico.jobduplicar.FormatoDecider;
import com.arquitecturajava.batchbasico.pasos.Paso1;
import com.arquitecturajava.batchbasico.pasos.Paso2;
import com.arquitecturajava.batchbasico.pasos.Paso3;
import com.arquitecturajava.batchbasico.pasosficheros.CrearCarpetaPaso;
import com.arquitecturajava.batchbasico.pasosficheros.CrearFicheroPaso;
import com.arquitecturajava.batchbasico.pasosficheros.ListarFicherosPaso;

@EnableBatchProcessing
@ComponentScan("com.arquitecturajava.batchbasico.ficherostexto")
public class JobConfiguration2 {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	

	
	
	@Bean
	public List<String> datos() {
		
		List<String> lista= new ArrayList<String>();
		lista.add("hola");
		lista.add("adios");
		lista.add("que");
		lista.add("tal");
		lista.add("estas");
		
		
		return lista;
		
	}
	public List<Factura> datos2() {
		
		List<Factura> listaFacturas= new ArrayList<Factura>();
		listaFacturas.add(new Factura(1,"ordenador",200));
		listaFacturas.add(new Factura(2,"tablet",200));
		listaFacturas.add(new Factura(3,"auriculas",300));
		listaFacturas.add(new Factura(4,"tele",550));
		return listaFacturas;
	}
	
	
	
//	//a mano del todo
//	@Bean 
//	ItemReader<String> lector() {
//		
//		return new ItemBasicoReader(datos());
//	}
//	
//	@Bean 
//	ItemWriter<String> escritor() {
//		
//		return new ItemBasicoWriter();
//	}
//	
//	@Bean
//	public Step paso1() {
//		
//		return stepBuilderFactory.get("paso1")
//				.<String,String>chunk(5)
//				.reader(lector())
//				.writer(escritor()).build();
//	}
	
	

		@Bean 
		ItemReader<Factura> lector() {
			
			return new ItemBasicoFacturaReader(datos2());
		}
		
		@Bean 
		ItemWriter<Factura> escritor() {
			
			return new ItemBasicoFacturaWriter();
		}
		
		
		@Bean 
		ItemProcessor<Factura,Factura> procesador() {
			
			return new ItemBasicoFacturaProcessor();
		}
		
		@Bean
		public Step paso1() {
			
			return stepBuilderFactory.get("paso1")
					.<Factura,Factura>chunk(5)
					.reader(lector())
					.processor(procesador())
					.writer(escritor()).build();
		}
	

	
	@Bean
	public Flow flujo1() {
		
		
		FlowBuilder<Flow> flujoBuilder= new FlowBuilder<>("flujo1");
		return flujoBuilder.start(paso1()).on("COMPLETED").end().build();
	}
	
	
	@Bean
	public Job job() {
		
		
		return jobBuilderFactory.get("mijob").start(flujo1()).end().build();
		
		
	}
	
	
	
	
	
}
