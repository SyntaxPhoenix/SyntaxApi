package com.syntaxphoenix.syntaxapi.utils.java;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	public static Connection connectSQLite(File file) throws SQLException {
		return DriverManager.getConnection("jdbc:sqlite:" + (file.isAbsolute() ? file.getAbsolutePath() : file.getPath()));
	}
	
	public static Connection connectMySQL(String url, int port, String database, String user, String password) throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/" + database, user, password);
	}

}
