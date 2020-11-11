package com.arquitecturajava.batchbasico.jobduplicar;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class CopiarFicherosMayusculas implements Tasklet {

	@Value("${carpetaOrigen}")
	private String carpetaOrigen;
	
	@Value("${carpetaDestino}")
	private String carpetaDestino;
	
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		File destino= new File(carpetaDestino);
		if (destino.isDirectory()) {
			
			File origen= new File(carpetaOrigen);
			
			File[] ficheros= origen.listFiles();
			
			for (int i=0;i<ficheros.length;i++) {
				
				File nuevo=new File(destino,ficheros[i].getName().toUpperCase());
				nuevo.createNewFile();
			}
			
		}
		
		return RepeatStatus.FINISHED;
	}

}
