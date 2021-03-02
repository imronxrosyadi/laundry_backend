package com.lawencon.laundry.config;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Imron Rosyadi
 */

public class DBConnection {

	public static void main(String[] args) {
		new DBConnection().conn();
	}

	public Connection conn() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/db_laundry",
					"postgres", "admin");
//			System.out.println("Database Laundry Connected");
			return connection;
		} catch (Exception e) {
			System.out.println("Connection failure.");
			e.printStackTrace();
		}
		return null;
	}

}
