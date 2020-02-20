package org.demo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;

public class Main2 {

	public static void main(String[] args) throws SQLException {

		InMemoryHsqlDb db = new InMemoryHsqlDb("hsqldb-tmp", "modeltmp");
		
		// NB : keep HSQL engine running !!!!
		db.createSchema();
		
		// NB : keep HSQL engine running !!!!
//		EntityManager em = db.createEntityManager();
//		em.close();

		Connection con = db.getConnection();
		
//		String createTable = "create table COUNTRY (" + 
//				"CODE varchar(255) not null, " + 
//				"NAME varchar(50), " + 
//				"primary key (CODE) " + 
//				")";
//		db.execSQL(createTable);
		
		insert(con, "FR", "France");

		try {
			DatabaseMetaData md = con.getMetaData();
			System.out.println(
					"Connection OK : database = " + md.getDatabaseProductName() + " " + md.getDatabaseProductVersion());
			String catalog = null;
			String schemaPattern = null;
			String tableNamePattern = null;
			String[] types = null;

			ResultSet rs = md.getTables(catalog, schemaPattern, tableNamePattern, types);
			while (rs.next()) {
				System.out.println(" . " + rs.getObject(1) + " " + rs.getObject(2) + " " + rs.getObject(3));
			}

//			shutdown(con);

			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void insert(Connection con, String code, String name) throws SQLException {
		System.out.println("insert()");
		Statement stmt = con.createStatement();
		String sql = "insert into COUNTRY values('" + code + "', '" + name + "')";
		System.out.println("SQL : " + sql);
		stmt.execute(sql);
	}

	public static void shutdown(Connection con) throws SQLException {
		System.out.println("shutdown()");
		Statement stmt = con.createStatement();
		stmt.execute("SHUTDOWN");
	}
}
