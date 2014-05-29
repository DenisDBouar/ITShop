package com.ecommerce.dbo;

import java.sql.*;

public class MysqlIO {
	private static Connection con;

	public static Connection getConnection() {
		if(con != null){
			return con;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/JITShopDB";
			con = DriverManager.getConnection(url, "root", "root");
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			CloseConnection();
		}
		return null;
	}
	

	public static void CloseConnection() {
		try {
			if (con != null){
				con.close();
				con = null;
			}
		} catch (SQLException e) {
		}
	}
}