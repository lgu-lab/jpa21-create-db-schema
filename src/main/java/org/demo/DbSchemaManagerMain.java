package org.demo;

public class DbSchemaManagerMain {

	public static void main(String[] args) {
		DbSchemaManager dbsm = new DbSchemaManager();
		dbsm.createSchema("telosys-tmp-db");
	}

}
