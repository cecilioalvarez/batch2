package com.arquitecturajava.batchbasico;



import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.arquitecturajava.batchbasico.ficherostexto.Factura;

public class FacturaFieldSetMapper implements FieldSetMapper<Factura> {

	@Override
	public Factura mapFieldSet(FieldSet fieldSet) throws BindException {
		//mapea la linea del fichero con un objeto
		Factura f= new Factura(fieldSet.readInt("numero"),
				fieldSet.readString("concepto"),
				fieldSet.readInt("importe"));
		return f;
	}

}
