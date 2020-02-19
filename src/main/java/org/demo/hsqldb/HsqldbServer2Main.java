package org.demo.hsqldb;

import java.sql.SQLException;

public class HsqldbServer2Main {

	public static void main(String[] args) throws SQLException {
		Hsqldb db = new Hsqldb("foo");
		Hsqldb db2 = new Hsqldb("bar");
		
		db.getConnection();
		db.createTable();
		
		db.getConnection();
		db.getConnection();
		db.getConnection();

		db2.createTable();
		
		for (int i = 1; i <= 20; i++) {
			db.insert(i, "val"+i);
		}
		System.out.println("COUNT db = " + db.count());
		
		System.out.println("COUNT db2 = " + db2.count());
		
	}

}
