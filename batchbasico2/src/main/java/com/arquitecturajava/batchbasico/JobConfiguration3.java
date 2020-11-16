package com.arquitecturajava.batchbasico;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.arquitecturajava.batchbasico.ficherostexto.Factura;
import com.arquitecturajava.batchbasico.ficherostexto.ItemBasicoFacturaProcessor;
import com.arquitecturajava.batchbasico.ficherostexto.ItemBasicoFacturaWriter;
import com.arquitecturajava.batchbasico.jdbc.FacturaPreparedStatementSetters;
import com.arquitecturajava.batchbasico.jdbc.FacturaRowMapper;

@EnableBatchProcessing
@ComponentScan("com.arquitecturajava.batchbasico.ficherostexto")
public class JobConfiguration3 {

	private static final String INSERCION= "Insert into Facturas2 (numero,concepto,importe) "+
									"values (?,?,?)";
	
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	ItemWriter<Factura> escritor() {

		return new ItemBasicoFacturaWriter();
	}

	@Bean
	ItemProcessor<Factura, Factura> procesador() {

		return new ItemBasicoFacturaProcessor();
	}

	public FlatFileItemReader<Factura> lectorFichero() {

		// genera el reader
		FlatFileItemReader<Factura> lector = new FlatFileItemReader<Factura>();

		// configura las linea de salto
		lector.setLinesToSkip(1);
		// decide donde va a leer el fichero
		lector.setResource(new ClassPathResource("facturas.txt"));
		// mapper de linea a objeto
		DefaultLineMapper<Factura> mapeador = new DefaultLineMapper<Factura>();
		// linea delimitadora
		DelimitedLineTokenizer separador = new DelimitedLineTokenizer();
		// que campos definen la estructa
		separador.setNames(new String[] { "numero", "concepto", "importe" });

		mapeador.setLineTokenizer(separador);

		mapeador.setFieldSetMapper(new FacturaFieldSetMapper());

		mapeador.afterPropertiesSet();

		lector.setLineMapper(mapeador);

		return lector;
	}

	@Bean
	public FlatFileItemWriter<Factura> escribirFichero() {

		FlatFileItemWriter escritor = new FlatFileItemWriter<Factura>();

		DelimitedLineAggregator<Factura> agregador = new DelimitedLineAggregator<Factura>();
		agregador.setDelimiter(",");

		// extraer los elementos con la FActura
		BeanWrapperFieldExtractor<Factura> extractor = new BeanWrapperFieldExtractor<Factura>();
		// lo que hace es selecciona los campos que el writer va a procesar

		// los campos qeu se extraen
		String[] campos = { "numero", "concepto" };
		extractor.setNames(campos);
		agregador.setFieldExtractor(extractor);
		// terminamos de configurar el agregador

		escritor.setLineAggregator(agregador);
		// escribimos el fichero
		escritor.setResource(new FileSystemResource("./facturasDestino.txt"));
		return escritor;

	}

	@Bean
	public Step paso1() throws SQLException {

		return stepBuilderFactory.get("paso1").<Factura, Factura>chunk(5).reader(lectorJDBC())
				.processor(procesador()).writer(escritorJDBC(dataSource(), plantilla(dataSource()))).build();
	}

	@Bean
	public Flow flujo1() throws SQLException {

		FlowBuilder<Flow> flujoBuilder = new FlowBuilder<>("flujo1");
		return flujoBuilder.start(paso1()).on("COMPLETED").end().build();
	}

	@Bean
	public Job job() throws SQLException {

		return jobBuilderFactory.get("mijob3").start(flujo1()).end().build();

	}
	
	//configuracion del acceso a la base de datos
	@Bean
	public DataSource dataSource() throws SQLException {

		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		dataSource.setDriver(new com.mysql.jdbc.Driver());
		dataSource.setUrl("jdbc:mysql://localhost:8889/batch2");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;

	}
	// configurar un lector de base de datos
	public JdbcCursorItemReader<Factura> lectorJDBC() throws SQLException {
		
		JdbcCursorItemReader<Factura> lector= new JdbcCursorItemReader<Factura>();
		lector.setDataSource(dataSource());
		lector.setSql("select * from Facturas");
		lector.setRowMapper(new FacturaRowMapper());
		return lector;
		
		
	}
	
	@Bean
	NamedParameterJdbcTemplate plantilla(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}
	
	
	@Bean
	ItemWriter<Factura> escritorJDBC(DataSource dataSource , NamedParameterJdbcTemplate plantilla) {
		
		JdbcBatchItemWriter<Factura> escritor=new JdbcBatchItemWriter<>();
		escritor.setDataSource(dataSource);
		escritor.setJdbcTemplate(plantilla);
		escritor.setSql(INSERCION);
		
		ItemPreparedStatementSetter<Factura> setter= new FacturaPreparedStatementSetters();
		escritor.setItemPreparedStatementSetter(setter);
		
		return escritor;
		
	}
	

}
