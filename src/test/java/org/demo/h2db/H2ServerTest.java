package org.demo.h2db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class H2ServerTest {

	private Server server;

	@Before
	public void startServer() throws SQLException {
//        TCP server only.
//		server = Server.createTcpServer("-tcp -webPort 8008 -tcpPort 9008 -properties null".split(" "));
		server = Server.createTcpServer("-tcp -tcpPort 9008 -properties null".split(" "));
		System.out.println("Start Server AT: " + server.getURL());
	}

	public Connection getConnection() throws SQLException {
		System.out.println("getConnection()");
		/*
		 * NB : add "DB_CLOSE_DELAY=-1"
		 * By default, closing the last connection to a database closes the database. 
		 * For an in-memory database, this means the content is lost. 
		 * To keep the database open, add ;DB_CLOSE_DELAY=-1 to the database URL. 
		 * To keep the content of an in-memory database as long as the virtual machine is alive, 
		 * use jdbc:h2:mem:test;DB_CLOSE_DELAY=-1. 
		 */
		return DriverManager.getConnection("jdbc:h2:" + server.getURL() + "/mem:db;DB_CLOSE_DELAY=-1", "sa", "");
	}

	public void createTable() throws SQLException {
		System.out.println("createTable()");
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
		System.out.println("insert()");
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


	@Test
	public void test() throws SQLException {
		System.out.println("test()");
		server.start();
		
		createTable();
		
//		Connection c = getConnection();
//		Statement stmt = c.createStatement();
//		stmt.execute("create table test(id int primary key, val varchar(100))");
		for (int i = 1; i <= 20; i++) {
			insert(i, "val"+i);
//			if (i == 500) {
//				server.stop();
//			}
//			stmt.execute("insert into test values(" + i + ", 'values')");
		}
	}

	@After
	public void shutdownServer() {
		System.out.println("Shutown Server ...");
		server.shutdown();
		System.out.println("Done.");
	}
}
