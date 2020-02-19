package org.demo.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class HsqlServerTest {

	public Connection getConnection() throws SQLException {
		System.out.println("getConnection()");
		 try {
		     Class.forName("org.hsqldb.jdbc.JDBCDriver" );
		 } catch (Exception e) {
		     System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
		     e.printStackTrace();
		 }
		return DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
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
			System.out.println("SQL : " + sql);
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
			
			System.out.println("COUNT : " + count);
			stmt.execute(sql);
		} finally {
			c.close();
		}
		return count;
	}


	@Test
	public void test() throws SQLException {
		System.out.println("test()");
		
		createTable();
		
		for (int i = 1; i <= 20; i++) {
			insert(i, "val"+i);
		}
		count();
	}

}
