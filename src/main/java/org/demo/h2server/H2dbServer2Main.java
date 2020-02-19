package org.demo.h2server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class H2dbServer2Main {

	public static void main(String[] args) throws SQLException {

		H2dbServer h2 = new H2dbServer();
		h2.startServer();
		h2.getConnection();
		h2.shutdownServer();
		
//		createTable(h2);
//		for (int i = 1; i <= 20; i++) {
//			insert(h2, i, "val"+i);
//		}
//		h2.shutdownServer();
	}
	
	public static void createTable(H2dbServer h2) throws SQLException {
		System.out.println("createTable()");
		Connection c = h2.getConnection();
		try {
			Statement stmt = c.createStatement();
			String sql = "create table test(id int primary key, name varchar(100))" ;
			System.out.println("SQL : " + sql);
			stmt.execute(sql);
		} finally {
			c.close();
		}
	}

	public static void insert(H2dbServer h2, int id, String name) throws SQLException {
		System.out.println("insert()");
		Connection c = h2.getConnection();
		try {
			Statement stmt = c.createStatement();
			String sql = "insert into test values(" + id + ", '" + name + "')" ;
			System.out.println("SQL : " + sql);
			stmt.execute(sql);
		} finally {
			c.close();
		}
	}


}
