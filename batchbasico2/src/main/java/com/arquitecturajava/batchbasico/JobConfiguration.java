package com.arquitecturajava.batchbasico;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.arquitecturajava.batchbasico.pasos.Paso1;
import com.arquitecturajava.batchbasico.pasos.Paso2;
import com.arquitecturajava.batchbasico.pasos.Paso3;
import com.arquitecturajava.batchbasico.pasosficheros.CrearCarpetaPaso;
import com.arquitecturajava.batchbasico.pasosficheros.CrearFicheroPaso;
import com.arquitecturajava.batchbasico.pasosficheros.ListarFicherosPaso;

@EnableBatchProcessing
public class JobConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	
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
	
	
	@Bean
	public Step crearCarpeta() {
		
		return stepBuilderFactory.get("crearCarpeta").tasklet(new CrearCarpetaPaso()).build();
	}
	
	@Bean
	public Step crearFichero() {
		
		return stepBuilderFactory.get("crearFichero").tasklet(new CrearFicheroPaso()).build();
	}
	

	@Bean
	public Step listarFicheros() {
		
		return stepBuilderFactory.get("listarFicheros").tasklet(new ListarFicherosPaso()).build();
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
	
	@Bean 
	Job job() {
		
		return jobBuilderFactory.get("trabajoFicheros")
				.start(crearCarpeta())
				.next(crearFichero())
				.next(listarFicheros())
				.build();
		
	}
	
	
	
	
	
}
