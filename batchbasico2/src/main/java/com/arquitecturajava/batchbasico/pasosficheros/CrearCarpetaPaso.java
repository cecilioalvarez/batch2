package com.arquitecturajava.batchbasico.pasosficheros;

import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CrearCarpetaPaso implements Tasklet {
	
	//ligamos el fichero de properties con la clase
	// de tal forma que spring framework sea capaz
	// de leerlo y ligarlo
	
	@Value("${carpetaOrigen}")
	private String carpetaOrigen;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		System.out.println("crear carpeta :"+ carpetaOrigen);
		File fichero= new File(carpetaOrigen);
		fichero.mkdir();
		return RepeatStatus.FINISHED;
	}

}
