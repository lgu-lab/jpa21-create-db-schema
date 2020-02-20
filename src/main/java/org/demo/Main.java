package org.demo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.persistence.EntityManager;

public class Main {

	public static void main(String[] args) {
		
		InMemoryHsqlDb db = new InMemoryHsqlDb("hsqldb-tmp");
		
		db.createSchema();
		
		Connection con = db.getConnection();
		
		try {
			DatabaseMetaData md = con.getMetaData();
			System.out.println("Connection OK : database = " + md.getDatabaseProductName() + " " 
					+ md.getDatabaseProductVersion() );
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EntityManager em = db.createEntityManager();
		em.close();
	}

}
