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
			/*String request ="select GroupID, GroupName " +
					"from JITShopDB.Groups " +
					"where UPPER(GroupID) = ? ";//SQL injection
*/			
			String request ="SELECT CategoryID, CategoryName, Description, GroupID"+
							" FROM JITShopDB.Categories"+
							" WHERE UPPER(GroupID) = ?;";
			
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

	
	/**
	 * This method will insert a record into the Categories table. 
	 * 
	 * @param CategoryName
	 * @param Description
	 * @param GroupID - integer column
	 * @return integer 200 for success, 500 for error
	 * @throws Exception
	 */
	public int insertIntoCategories(String CategoryName, 
									String Description, 
									String GroupID )throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before starting to insert data into the database.
			 */
			String request ="INSERT INTO Categories(CategoryName, Description, GroupID)"+
							" VALUES(?, ?, ?);";
			conn = MysqlIO.getConnection();
			query = conn.prepareStatement(request);

			query.setString(1, CategoryName);
			query.setString(2, Description);
			query.setInt(3, Integer.parseInt(GroupID));

			query.executeUpdate();

		} catch(Exception e) {
			e.printStackTrace();
			return 500; 
		}
		finally {
			MysqlIO.CloseConnection();
		}

		return 200;
	}
	
	
	
	
	
	
	
	public JSONArray queryReturnProductList(String id) {
		PreparedStatement query = null;
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		String sqlRequest ="SELECT ProductID, ProductName, Description, ImagePath, "+
															"UnitPrice, CategoryID "+
						   	"FROM Products"+
							" WHERE CategoryID = ?;";
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setInt(1, Integer.parseInt(id));
			ResultSet rs = query.executeQuery();

			json = converter.toJSONArray(rs);
			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		
		return json;
	}

	public JSONArray queryMenuCreator(String sqlRequestType, String id) {
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		String sqlRequest="";
		if(sqlRequestType.equals("groups"))
			sqlRequest ="SELECT GroupID, GroupName FROM Groups;";
		if(sqlRequestType.equals("category"))
			sqlRequest ="SELECT CategoryID, CategoryName FROM Categories WHERE GroupID = '"+ id +"';";
		
		ResultSet res = null;
		try {
			res = MysqlIO.getConnection().createStatement().executeQuery(sqlRequest);
			json = converter.toJSONArray(res);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		
		return json;
	}

}
