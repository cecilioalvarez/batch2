package com.arquitecturajava.batchbasico;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

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
	
	@Autowired
	private CopiarFicherosMayusculas copiarFicherosMayusculas;
	
	@Autowired
	private FormatoDecider decisor;
	
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
	
	
	@Bean
	public Step copiarFicherosMayusculasStep() {
		
		return stepBuilderFactory.get("copiarFicherosMayusculas").tasklet(copiarFicherosMayusculas).build();

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
	
	// job que yo tengo aqui cambiara
	//porque le he asignado otra tarea
	
	@Bean 
	public Flow flujo() {
		
		// flujo es un conjunto de steps con sus tasklet
		FlowBuilder<Flow> flujoBuilder= new FlowBuilder("flujo1");
		
		 return  flujoBuilder
				
				.start(copiarStep())
				.on("COMPLETED")
				.to(decisor)
				.on("MINUSCULAS")
				.to(copiarFicherosStep())
				.from(decisor)
				.on("MAYUSCULAS")
				.to(copiarFicherosMayusculasStep())
				.on("COMPLETED")
				.end()
				.build();
	}
	
	@Bean
	public Job job() {
		
		// en el metodo start en vez de tener un conjunto de datos
		// lo que tiene es un flujo que los agrupa a todos
		
		return jobBuilderFactory.get("flujo1").start(flujo()).end().build();
		
	}
	
	
	
	
	
}
