package com.arquitecturajava.batchbasico.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.arquitecturajava.batchbasico.ficherostexto.Factura;

public class FacturaRowMapper implements RowMapper<Factura> {

	@Override
	public Factura mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		System.out.println(rs.getString("concepto")+ rs.getString("importe"));
		
		Factura f= new Factura();
		f.setConcepto(rs.getString("concepto"));
		f.setNumero(rs.getInt("numero"));
		f.setImporte(rs.getInt("importe"));
		return f;
	}

}
