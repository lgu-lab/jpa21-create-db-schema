package org.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class InMemoryHsqlDb {

	private final String persistenceUnitName ;
	
	private final String databaseName ;
	
	private EntityManagerFactory entityManagerFactory ;
	
	/**
	 * Constructor 
	 * @param persistenceUnitName persistence unit name in 'persistence.xml' file
	 * @param databaseName database name used in jdbc url defined in 'persistence.xml' file
	 */
	public InMemoryHsqlDb(String persistenceUnitName, String databaseName) {
		super();
		this.persistenceUnitName = persistenceUnitName;
		this.databaseName = databaseName;
		this.entityManagerFactory = null ;
	}

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}
	public String getDatabaseName() {
		return databaseName;
	}


	public void createSchema() {

		Properties properties = new Properties();
		properties.setProperty("javax.persistence.schema-generation.database.action", "create");

		/*
		 * Since JPA 2.1 : Java API which, based on the persistent unit name, can
		 * generate the schema.
		 * 
		 * Persistence.generateSchema(String persistenceUnitName, Map properties)
		 * 
		 * Create database schemas and/or tables and/or create DDL scripts as determined
		 * by the supplied properties Called when schema generation is to occur as a
		 * separate phase from creation of the entity manager factory.
		 */
		// Do not use "Persistence.generateSchema()" because it's not possible to stop HSQL engine
		// Persistence.generateSchema(persistenceUnitName, properties); // NB : keep HSQL engine running !!!!
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName, properties);
		emf.close(); // Close the factory, releasing any resources that it holds => HSQL can stop at the end of 'Main'
	}

	public Connection getConnection() {
		try {
			// Check JDBC driver is present in ClassPath
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (Exception e) {
			throw new RuntimeException("Cannot load HSQLDB JDBC driver");
		}
		
		try {
			String url = "jdbc:hsqldb:mem:" + this.databaseName ;
			return DriverManager.getConnection(url, "SA", "");
		} catch (SQLException e) {
			throw new RuntimeException("Cannot get HSQLDB connection (SQLException)", e);
		}
	}

	public void execSQL(String sql) {
//		Connection con = getConnection() ;
		try ( Connection con = getConnection() ){
			Statement stmt = con.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException("SQL error (SQLException)", e);
		}
	}

	public EntityManagerFactory createEntityManagerFactory() {
		return Persistence.createEntityManagerFactory(persistenceUnitName);
	}

	public EntityManagerFactory getEntityManagerFactory() {
		if ( entityManagerFactory != null ) {
			return entityManagerFactory ;
		}
		else {
			entityManagerFactory = createEntityManagerFactory();
			return entityManagerFactory ;
		}
	}

	public EntityManager createEntityManager() {
		EntityManagerFactory emf = getEntityManagerFactory();
		return emf.createEntityManager();
	}

	public void closeEntityManagerFactory() {
		if ( entityManagerFactory != null ) {
			// Close the factory, releasing any resources that it holds
			entityManagerFactory.close();
		}
	}

}
