package databaseAccess;

import java.sql.*;



public class DBConnection{
	public static Connection getConnection() {
		String dbURL = "jdbc:mysql://databasemysql.cef4sqghyefl.us-east-1.rds.amazonaws.com:3306/bookstore";
		String dbUser = "admin";
		String dbPassword = "Etiennechoa23";
		String dbClass = "com.mysql.jdbc.Driver";
		
		Connection connection=null;
		try {
			Class.forName(dbClass);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(dbURL,dbUser,dbPassword);
		} catch(SQLException e) {
			//e.printStackTrace();
			System.out.println(e);
			System.out.println(dbPassword);
		}
		return connection;
		
	}
}