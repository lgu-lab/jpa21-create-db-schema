package org.demo.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Hsqldb {

	private final String dbName ;
	private final String jdbcURL ;
	
	public Hsqldb(String dbName) {
		super();
		this.dbName = dbName;
		this.jdbcURL = "jdbc:hsqldb:mem:" + dbName ;
	}

	public Connection getConnection() throws SQLException {
		System.out.println("getConnection()");
		 try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver" );
		 } catch (Exception e) {
		     System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
		     e.printStackTrace();
		 }
		return DriverManager.getConnection(this.jdbcURL, "SA", "");
	}

	public void createTable() throws SQLException {
		System.out.println("--- createTable()");
		Connection c = getConnection();
		try {
			Statement stmt = c.createStatement();
			String sql = "create table test(id int primary key, name varchar(100))" ;
			System.out.println("SQL : " + sql);
			stmt.execute(sql);
		} finally {
			c.close();
		}
	}

	public void insert(int id, String name) throws SQLException {
		System.out.println("--- insert()");
		Connection c = getConnection();
		try {
			Statement stmt = c.createStatement();
			String sql = "insert into test values(" + id + ", '" + name + "')" ;
			stmt.execute(sql);
		} finally {
			c.close();
		}
	}

	public int count() throws SQLException {
		System.out.println("--- count()");
		int count = -1;
		Connection c = getConnection();
		try {
			String sql = "SELECT COUNT(*) FROM test AS count";
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery(sql);	
			rs.next();
			count = rs.getInt(1);
			rs.close();
		} finally {
			c.close();
		}
		return count;
	}

}
