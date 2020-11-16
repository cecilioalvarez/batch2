package com.arquitecturajava.batchbasico.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import com.arquitecturajava.batchbasico.ficherostexto.Factura;

public class FacturaPreparedStatementSetters implements ItemPreparedStatementSetter<Factura> {

	@Override
	public void setValues(Factura factura, PreparedStatement ps) throws SQLException {
		
		// los datos que estan en el objeto Factura
		// se los paso a la consulta preparada de insert
		ps.setInt(1, factura.getNumero());
		ps.setString(2, factura.getConcepto());
		ps.setDouble(3, factura.getImporte());
		
	}

}
