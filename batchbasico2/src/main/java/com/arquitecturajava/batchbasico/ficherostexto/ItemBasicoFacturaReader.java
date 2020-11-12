package com.arquitecturajava.batchbasico.ficherostexto;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemBasicoFacturaReader implements ItemReader<Factura> {

	private Iterator<Factura> datos;
	
	
	
	public ItemBasicoFacturaReader( @Autowired List<Factura> datos) {
		super();
		this.datos = datos.iterator();
	}



	@Override
	public Factura read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
	
	if (this.datos.hasNext()) {
			
			return this.datos.next();
		}else {
			return null;
		}
	}

	
}
