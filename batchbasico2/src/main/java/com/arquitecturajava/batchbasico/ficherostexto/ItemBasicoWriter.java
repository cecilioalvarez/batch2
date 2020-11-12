package com.arquitecturajava.batchbasico.ficherostexto;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class ItemBasicoWriter implements ItemWriter<String>{

	@Override
	public void write(List<? extends String> items) throws Exception {
		
		//recorriendo la lista e imprimiendola por la consola
		
		for (String item: items) {
			
			System.out.println("el item es :"+ item);
		}
		
	}

}
