package com.ecommerce.dbo;

import java.sql.*;

public class MysqlIO {
	private static Connection con;
	private static Statement st;

	public static Statement getConnectionStatement() {
		if(st != null){
			return st;
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/JITShopDB";
			con = DriverManager.getConnection(url, "root", "root");
			st = con.createStatement();
			return st;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			CloseConnection();
		}
		return null;
	}

	public static void CloseConnection() {
		try {
			if (st != null){
				st.close();
			}
			if (con != null){
				con.close();
			}
		} catch (SQLException e) {
		}
	}
}