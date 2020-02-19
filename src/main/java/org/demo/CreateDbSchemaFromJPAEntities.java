package org.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.telosys.tools.db.metadata.DbInfo;
import org.telosys.tools.db.metadata.MetaDataManager;

public class CreateDbSchemaFromJPAEntities {
	
	 public static void main(String[] args) 
	 { 
		 
		 System.out.println("--- Persistence.createEntityManagerFactory(xx)...");
		 EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2db"); 
		 
		 EntityManager em = emf.createEntityManager();
		 Session session = em.unwrap(Session.class);
		 session.doWork(new Work() {
			@Override
			public void execute(Connection con) throws SQLException {
				postCreation(con); 				
			}			 
		 });
		 
		 System.out.println("--- closing EntityManagerFactory...");
		 emf.close();		

	 }

	 private static void postCreation(Connection con) throws SQLException {
		 System.out.println("--- work with connection");
		 MetaDataManager metaDataManager = new MetaDataManager();
		 DbInfo dbInfo = metaDataManager.getDatabaseInfo(con);
		 System.out.println(" . Database Product Name : " + dbInfo.getDatabaseProductName() );
	 }
	 
	 private static void createSchema2(String persistenceUnitName) {
		 Map<String,String> properties = new HashMap<>();
		 Persistence.generateSchema(persistenceUnitName, properties);
	 }

}
