package com.arquitecturajava.batchbasico.ficherostexto;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class ItemBasicoFacturaWriter implements ItemWriter<Factura> {

	@Override
	public void write(List<? extends Factura> items) throws Exception {

		for (Factura item : items) {

			System.out.println("el item es :" + item.getNumero() + "," + item.getConcepto() + item.getImporte());
		}

	}

}
