package com.arquitecturajava.batchbasico.jobduplicar;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CopiarCarpeta  implements Tasklet {

	@Value("${carpetaOrigen}")
	private String carpetaOrigen;
	
	@Value("${carpetaDestino}")
	private String carpetaDestino;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
	
		
		File origen= new File(carpetaOrigen);
		if (origen.isDirectory()) {
			File destino= new File(carpetaDestino);
			destino.mkdir();
		}
		
		return RepeatStatus.FINISHED;
	}

}
