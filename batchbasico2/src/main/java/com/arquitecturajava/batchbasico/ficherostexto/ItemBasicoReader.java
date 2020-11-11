package com.arquitecturajava.batchbasico.ficherostexto;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class ItemBasicoReader implements ItemReader<String> {

	// iterador recorre una lista de elementos
	//iterador
	
	private Iterator<String> it;

	
	public ItemBasicoReader(List<String> datos) {
		
		// lo que hace es pone el puntero del iterador al principio de la lista
		
		
		// he hecho que la lista se muestra como una estructura recorrible
		it = datos.iterator();
	}

	// por cada lectura
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		// hay un elemento siguiente para extraer
		if (it.hasNext()) {
			// lo procesa y lo retorna
			return it.next();
		} else {
			//retorna null y por lo tanto ya no sigue procesando
			return null;
		}

	}

}
