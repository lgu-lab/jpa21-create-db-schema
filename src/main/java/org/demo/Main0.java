package org.demo;

import java.sql.SQLException;

import javax.persistence.EntityManager;

public class Main0 {

	public static void main(String[] args) throws SQLException {

		InMemoryHsqlDb db = new InMemoryHsqlDb("hsqldb-tmp", "modeltmp");

		// NB : keep HSQL engine running !!!!
		db.createSchema();
		
//		// NB : keep HSQL engine running !!!!
//		System.out.println("createEntityManager()");
//		EntityManager em = db.createEntityManager();
//		System.out.println("EntityManager ready.");
//		em.close();
//		System.out.println("EntityManager closed.");
//		
//		db.closeEntityManagerFactory(); // OK to stop HSQL engine
	}

}
