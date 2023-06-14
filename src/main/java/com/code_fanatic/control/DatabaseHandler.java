package com.code_fanatic.control;

import java.sql.*;

public class DatabaseHandler {

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/Code_Fanatic";
	static final String USER = "admin";
	static final String PASS = "admin";
	
	private Connection connection = null;
	
	public DatabaseHandler () {
		try {
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.print("Connection executed");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public ResultSet executeQuery(String SQLQuery) throws SQLException {
		
		Statement statement = connection.createStatement();
		return statement.executeQuery(SQLQuery);
	}
	
	public void closeConnection() {
		try {
		// Close the connection
		connection.close();
		} catch (SQLException se) {
		se.printStackTrace();
		}
	}	
	
}
