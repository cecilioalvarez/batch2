package com.arquitecturajava.batchbasico.pasosficheros;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class CrearFicheroPaso  implements Tasklet{

	@Value("${carpetaOrigen}")
	private String carpetaOrigen;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		System.out.println("creando un fichero");
		File fichero= new File(carpetaOrigen+"/hola.txt");
		fichero.createNewFile();
		
		return RepeatStatus.FINISHED;
	}

}
