package com.arquitecturajava.batchbasico;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.arquitecturajava.batchbasico.ficherostexto.Factura;
import com.arquitecturajava.batchbasico.ficherostexto.ItemBasicoFacturaProcessor;
import com.arquitecturajava.batchbasico.ficherostexto.ItemBasicoFacturaWriter;

@EnableBatchProcessing
@ComponentScan("com.arquitecturajava.batchbasico.ficherostexto")
public class JobConfiguration3 {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	

		
		@Bean 
		ItemWriter<Factura> escritor() {
			
			return new ItemBasicoFacturaWriter();
		}
		
		
		@Bean 
		ItemProcessor<Factura,Factura> procesador() {
			
			return new ItemBasicoFacturaProcessor();
		}
		

		
		public FlatFileItemReader<Factura> lectorFichero() {
			
			//genera el reader
			FlatFileItemReader<Factura> lector= new FlatFileItemReader<Factura>();
			
			//configura las linea de salto
			lector.setLinesToSkip(1);
			//decide donde va a leer el fichero
			lector.setResource(new ClassPathResource("facturas.txt"));
			//mapper de linea a objeto
			DefaultLineMapper<Factura> mapeador= new DefaultLineMapper<Factura>();
			//linea delimitadora
			DelimitedLineTokenizer separador= new DelimitedLineTokenizer();
			// que campos definen la estructa
			separador.setNames(new String[]{"numero","concepto","importe"});
			
			mapeador.setLineTokenizer(separador);
			
			mapeador.setFieldSetMapper(new FacturaFieldSetMapper() );
			
			mapeador.afterPropertiesSet();
			
			lector.setLineMapper(mapeador);
			
			return lector;
		}
		@Bean
		public FlatFileItemWriter<Factura> escribirFichero() {
			
			FlatFileItemWriter escritor= new FlatFileItemWriter<Factura>();
			
			DelimitedLineAggregator<Factura> agregador= new DelimitedLineAggregator<Factura>();
			agregador.setDelimiter(",");
			
			// extraer los elementos con la FActura
			BeanWrapperFieldExtractor<Factura> extractor= new BeanWrapperFieldExtractor<Factura>();
			//lo que hace es selecciona los campos que el writer va a procesar
			
			//los campos qeu se extraen
			String[] campos= {"numero","concepto"};
			extractor.setNames(campos);
			agregador.setFieldExtractor(extractor);
			//terminamos de configurar el agregador
			
			escritor.setLineAggregator(agregador);
			//escribimos el fichero
			escritor.setResource(new FileSystemResource("./facturasDestino.txt"));
			return escritor;
			
			
		}
		
		
		
		
		
		@Bean
		public Step paso1() {
			
			return stepBuilderFactory.get("paso1")
					.<Factura,Factura>chunk(5)
					.reader(lectorFichero())
					.processor(procesador())
					.writer(escribirFichero()).build();
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
