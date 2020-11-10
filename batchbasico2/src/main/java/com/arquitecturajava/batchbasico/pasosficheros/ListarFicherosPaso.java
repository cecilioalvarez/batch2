package com.arquitecturajava.batchbasico.pasosficheros;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class ListarFicherosPaso implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		File carpeta= new File("carpeta1");
		String[] lista= carpeta.list();
		for (int i=0;i<lista.length;i++) {
			
			System.err.println(lista[i]);
		}
		
		
		return RepeatStatus.FINISHED;
	}

}
