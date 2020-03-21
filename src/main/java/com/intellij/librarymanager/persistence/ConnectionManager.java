package com.intellij.librarymanager.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcDataSource;
/*create a manager*/
public class ConnectionManager {
	private static final String DB_CONNECTION = "jdbc:h2:~/libraryManagerDatabase";//the address of our database
	private static final String DB_USER = "huyufei";
	private static final String DB_PASSWORD = "123456";

	private static JdbcDataSource datasource = null;

	private static void init() {
		if (datasource == null) {
			datasource = new JdbcDataSource();
			datasource.setURL(DB_CONNECTION);
			datasource.setUser(DB_USER);
			datasource.setPassword(DB_PASSWORD);
		}
	}

	public static Connection getConnection() throws SQLException {
		init();
		return datasource.getConnection();
	}

}
