package com.arquitecturajava.batchbasico.jobduplicar;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FormatoDecider implements JobExecutionDecider {

	// la variable con el formato que necesitamos
	@Value("${formato}")
	private String formato;

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

		if (formato.equalsIgnoreCase("minusculas")) {

			System.out.println("pasamos por la eleccion de minusculas");
			
			return new FlowExecutionStatus("MINUSCULAS");
		
		} else {
		
			System.out.println("pasamos por la eleccion de mayusculas");
			return new FlowExecutionStatus("MAYUSCULAS");
		}

	}

}
