package org.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class InMemoryHsqlDb {

	private final String persistenceUnitName ;
	
	private EntityManagerFactory entityManagerFactory ;
	
	public InMemoryHsqlDb(String persistenceUnitName) {
		super();
		this.persistenceUnitName = persistenceUnitName;
		this.entityManagerFactory = null ;
	}

	public String getPersistenceUnitName() {
		return persistenceUnitName;
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
		Persistence.generateSchema(persistenceUnitName, properties);
	}

	public Connection getConnection() {
		try {
			// Check JDBC driver is present in ClassPath
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (Exception e) {
			throw new RuntimeException("Cannot load HSQLDB JDBC driver");
		}
		
		try {
			return DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
		} catch (SQLException e) {
			throw new RuntimeException("Cannot get HSQLDB connection (SQLException)", e);
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

}
