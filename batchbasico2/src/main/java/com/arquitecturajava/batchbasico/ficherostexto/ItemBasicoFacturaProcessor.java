package com.arquitecturajava.batchbasico.ficherostexto;

import org.springframework.batch.item.ItemProcessor;

public class ItemBasicoFacturaProcessor implements ItemProcessor<Factura,Factura> {

	@Override
	public Factura process(Factura item) throws Exception {
	
		
		if (item.getImporte()<=200) {
			return null;
			
		}else {
			return item;
		}
	}

}
