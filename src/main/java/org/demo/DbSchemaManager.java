package org.demo;

import java.util.Properties;

import javax.persistence.Persistence;

import org.hibernate.cfg.AvailableSettings;

public class DbSchemaManager {

	public void createSchema(String persistenceUnitName) {
		
		Properties properties = new Properties();
		
//	       // XXX force persistence properties : remove database target
//		properties.setProperty(org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO, "");
//		properties.setProperty(AvailableSettings.SCHEMA_GEN_DATABASE_ACTION, "none");
//
//        // XXX force persistence properties : define create script target from metadata to destination
//        // persistenceProperties.setProperty(AvailableSettings.SCHEMA_GEN_CREATE_SCHEMAS, "true");
//		properties.setProperty(AvailableSettings.SCHEMA_GEN_SCRIPTS_ACTION, "create");
//		properties.setProperty(AvailableSettings.SCHEMA_GEN_CREATE_SOURCE, "metadata");
//		properties.setProperty(AvailableSettings.SCHEMA_GEN_SCRIPTS_CREATE_TARGET, destination);

		properties.setProperty("javax.persistence.schema-generation.database.action", "create");
		
		/*
		 * Since JPA 2.1 : Java API which, based on the persistent unit name, can generate the schema.
		 * 
		 * Persistence.generateSchema(String persistenceUnitName, Map properties)
		 * 
		 * Create database schemas and/or tables and/or create DDL scripts as determined by the supplied properties 
		 * Called when schema generation is to occur as a separate phase from creation of the entity manager factory.
		 */
		Persistence.generateSchema(persistenceUnitName, properties);
		 
	}
	
	public void createHsqlSchema(String persistenceUnitName) {
		
		Properties properties = new Properties();

		// to override "persistence.xml" properties 
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
				
		properties.setProperty("javax.persistence.jdbc.driver",   "org.hsqldb.jdbc.JDBCDriver" ) ;
		properties.setProperty("javax.persistence.jdbc.url",      "jdbc:hsqldb:mem:telosys" ) ;
		properties.setProperty("javax.persistence.jdbc.user",     "SA" ) ;
		properties.setProperty("javax.persistence.jdbc.password", "" ) ;
		
		properties.setProperty("javax.persistence.schema-generation.database.action", "create");
		
		/*
		 * Since JPA 2.1 : Java API which, based on the persistent unit name, can generate the schema.
		 * 
		 * Persistence.generateSchema(String persistenceUnitName, Map properties)
		 * 
		 * Create database schemas and/or tables and/or create DDL scripts as determined by the supplied properties 
		 * Called when schema generation is to occur as a separate phase from creation of the entity manager factory.
		 */
		Persistence.generateSchema(persistenceUnitName, properties);
		 
	}
}
