package com.arquitecturajava.batchbasico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import(JobConfiguration2.class)
public class BatchbasicoApplication {

	// nuestro proyecto de spring boot

	public static void main(String[] args) {
		SpringApplication.run(BatchbasicoApplication.class, args);
	}

}
