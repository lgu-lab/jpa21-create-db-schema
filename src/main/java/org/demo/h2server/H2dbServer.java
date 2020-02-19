package org.demo.h2server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.Server;

public class H2dbServer {

	private Server server;

	public void startServer() throws SQLException {
//        TCP server only.
//		server = Server.createTcpServer("-tcp -webPort 8008 -tcpPort 9008 -properties null".split(" "));
		server = Server.createTcpServer("-tcp -tcpAllowOthers -tcpPort 9008 -properties null".split(" "));
		System.out.println("Start Server AT: " + server.getURL());
		getConnection();
	}

	public void shutdownServer() {
		System.out.println("Shutown Server ...");
		server.shutdown();
		System.out.println("Done.");
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

}
