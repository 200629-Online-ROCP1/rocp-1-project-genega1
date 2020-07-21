package com.revature.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionUtil {
	
	/*
	public static Connection getConnection() throws SQLException {
		
		
		System.out.println("Getting Connection");
		
		String url = "jdbc:postgresql://localhost:5432/banking";
		String username = "postgres";
		String password = "5677"; 
		
		return DriverManager.getConnection(url, username, password);
		
	}
	*/
	public static Connection getConnection() throws SQLException
	{
		try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:postgresql://localhost:5432/banking";
        String username ="postgres";
        String password= "5677";

        return DriverManager.getConnection(url,username,password);
	}
	
	

}
