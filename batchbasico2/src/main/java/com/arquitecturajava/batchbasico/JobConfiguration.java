package com.arquitecturajava.batchbasico;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.arquitecturajava.batchbasico.jobduplicar.CopiarCarpeta;
import com.arquitecturajava.batchbasico.jobduplicar.CopiarFicheros;
import com.arquitecturajava.batchbasico.pasos.Paso1;
import com.arquitecturajava.batchbasico.pasos.Paso2;
import com.arquitecturajava.batchbasico.pasos.Paso3;
import com.arquitecturajava.batchbasico.pasosficheros.CrearCarpetaPaso;
import com.arquitecturajava.batchbasico.pasosficheros.CrearFicheroPaso;
import com.arquitecturajava.batchbasico.pasosficheros.ListarFicherosPaso;

@EnableBatchProcessing
@ComponentScan("com.arquitecturajava.batchbasico.jobduplicar")
public class JobConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	// liga el componente registrado
	
	
	
	@Autowired
	private CrearCarpetaPaso crearCarpetaPaso;
	@Autowired
	private CrearFicheroPaso crearFicheroPaso;
	@Autowired
	private ListarFicherosPaso listarFicheroPaso;
	
	@Autowired
	private CopiarCarpeta copiarCarpeta;
	
	@Autowired 
	private CopiarFicheros copiarFicheros;
	
	
	// este es el codigo que define el job
	
	
//	@Bean
//	public Step paso1() {
//		
//		return stepBuilderFactory.get("paso1").tasklet(new Tasklet() {
//
//			@Override
//			public RepeatStatus execute(StepContribution contribution, 
//					ChunkContext chunkContext) throws Exception {
//				
//				System.out.println("holas desde Spring Batch");
//				
//				return RepeatStatus.FINISHED;
//			}
//			
//		}).build();
//	}
	
	
	//defino tambien los diferentes pasos a realizar
	
	@Bean
	public Step paso1() {
		
		return stepBuilderFactory.get("paso1").tasklet(new Paso1()).build();
	}
	
	@Bean
	public Step paso2() {
		
		return stepBuilderFactory.get("paso2").tasklet(new Paso2()).build();
	}
	
	@Bean
	public Step paso3() {
		
		return stepBuilderFactory.get("paso3").tasklet(new Paso3()).build();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	@Bean
	public Step crearCarpeta() {
		
		return stepBuilderFactory.get("crearCarpeta").tasklet(crearCarpetaPaso).build();
	}
	
	@Bean
	public Step crearFichero() {
		
		return stepBuilderFactory.get("crearFichero").tasklet(crearFicheroPaso).build();
	}
	
	@Bean
	public Step copiarStep() {
		
		return stepBuilderFactory.get("copiarCarpeta").tasklet(copiarCarpeta).build();

	}
	
	@Bean
	public Step copiarFicherosStep() {
		
		return stepBuilderFactory.get("copiarFicheros").tasklet(copiarFicheros).build();

	}
	
	//paso para el tasklet

	@Bean
	public Step listarFicheros() {
		
		return stepBuilderFactory.get("listarFicheros").tasklet(listarFicheroPaso).build();
	}
	
	
	// el propio camino que va a seguir un job
//	@Bean 
//	Job job() {
//		
//		return jobBuilderFactory.get("primerTrabajo")
//				.start(paso1())
//				.next(paso2())
//				.next(paso3())
//				.build();
//		
//	}
	
//	@Bean 
//	Job job() {
//		
//		return jobBuilderFactory.get("trabajoFicheros")
//				.start(crearCarpeta())
//				.next(crearFichero())
//				.next(listarFicheros())
//				.build();
//		
//	}
	
	@Bean 
	Job job() {
		
		return jobBuilderFactory.get("duplicar").start(copiarStep()).next(copiarFicherosStep()).build();
		
	}
	
	
	
	
	
}
