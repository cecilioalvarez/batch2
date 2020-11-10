package com.arquitecturajava.batchbasico.pasos;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;


// pasos es un tasklet

public class Paso1 implements Tasklet {

	// un patron comando
	@Override
	public RepeatStatus execute(StepContribution contribution,
			ChunkContext chunkContext) throws Exception {
	
		System.out.println("hola desde Spring batch");
		return RepeatStatus.FINISHED;
	}

}
