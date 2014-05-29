package com.ecommerce.dbo;

import java.sql.*;

import org.codehaus.jettison.json.JSONArray;

import com.ecommerce.util.ToJSON;

public class SQLibrary extends MysqlIO {
	public JSONArray queryReturnBrandParts(String brand) throws Exception {

		PreparedStatement query = null;

		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		try {
			String request ="select GroupID, GroupName " +
					"from JITShopDB.Groups " +
					"where UPPER(GroupID) = ? ";//SQL injection
			
			
			query = MysqlIO.getConnection().prepareStatement(request);
			//query.setString(1, brand.toUpperCase()); //protect against sql injection
			query.setInt(1, Integer.parseInt(brand)); //protect against sql injection
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			MysqlIO.CloseConnection();
		}

		return json;
	}


	
	public JSONArray queryReturnJArray() {
		String sqlRequest ="SELECT * FROM JITShopDB.Groups";
		ResultSet res = null;
		JSONArray json = null;
		try {
			res = MysqlIO.getConnection().createStatement().executeQuery(sqlRequest);
			ToJSON converter = new ToJSON();
			json = new JSONArray();
			json = converter.toJSONArray(res);
			
			MysqlIO.CloseConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		
		return json;
	}
}
