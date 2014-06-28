package com.ecommerce.dbo;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		
		if(sqlRequestType.equals("search"))
			sqlRequest ="SELECT ProductID, ProductName, Description, ImagePath, UnitPrice, CategoryID "+
						" FROM Products"+
						" WHERE LCASE(ProductName) LIKE LCASE('%"+id+"%')"+
						" OR LCASE(Description) LIKE LCASE('%"+id+"%');";
		
		if(sqlRequestType.equals("description"))
			sqlRequest ="SELECT ProductID, ProductName, Description, ImagePath, UnitPrice, CategoryID "+
						" FROM Products"+
						" WHERE ProductID = '"+ id +"';";
		
		if(sqlRequestType.equals("allProducts"))
			sqlRequest ="SELECT ProductID, ProductName, Description, ImagePath, UnitPrice, CategoryID FROM Products;";
		
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

	public JSONArray queryFindUser(String user, String pass) {
		PreparedStatement query = null;
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();

		String sqlRequest ="SELECT Token, Log, Pass" + 
							" FROM Users"+
							" WHERE Log = ? AND Token = ?;";
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, user);
			query.setString(2, pass);
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
	
	//depricated
	public String queryFindSession(String incomingData) {
		PreparedStatement query = null;

		ResultSet rs = null;
		String rez ="";
		String sqlRequest ="SELECT Sesion FROM Sesion WHERE Token = ?;";
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, incomingData);
			rs = query.executeQuery();
			rs.next();
			rez = rs.getString("Sesion");

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rez;
	}
	
	public String queryFindSession2(String incomingData) {
		PreparedStatement query = null;

		ResultSet rs = null;
		String rez ="";
		String sqlRequest ="SELECT SessionID, pubModule, pubExponent, privModule, privExponent"+
							" FROM JITShopDB.Session"+
							" WHERE pubModule = ?;";
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, incomingData);
			rs = query.executeQuery();
			rs.next();
			rez = rs.getString("SessionID");
			rez += "," + rs.getString("pubModule");
			rez += "," + rs.getString("pubExponent");
			rez += "," + rs.getString("privModule");
			rez += "," + rs.getString("privExponent");

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rez;
	}
	
	//deprecated
	public String querySessionaLive(String incomingData) {
		PreparedStatement query = null;

		ResultSet rs = null;
		String rez ="";
		String sqlRequest ="SELECT Sesion, Token FROM Sesion WHERE Sesion = ?;";
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, incomingData);
			rs = query.executeQuery();
			if (rs.next()) {
				rez = rs.getString("Token");
			}

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rez;
	}
	
	//depricated
	public int queryCreateSession(String sesionStr, String token) {
		PreparedStatement query = null;
		String sqlRequest ="INSERT INTO Sesion (Sesion, Token) VALUES (?, ?);";
		
		int rs =0;
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, sesionStr);
			query.setString(2, token);
			rs = query.executeUpdate();

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rs;
	}
	
	public int queryCreateSession(String pubmodulus, String puexponenet, String privmodulus, String privexponenet) {
		PreparedStatement query = null;
		String sqlRequest ="INSERT INTO Session (pubModule, pubExponent, privModule, privExponent) VALUES (?, ?, ?, ?);";
		
		int rs =0;
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, pubmodulus);
			query.setString(2, puexponenet);
			query.setString(3, privmodulus);
			query.setString(4, privexponenet);
			rs = query.executeUpdate();

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rs;
	}
	
	public ArrayList<String> queryGetCountCart(String sToken, String sProdID) {
		PreparedStatement query = null;

		ResultSet rs = null;
		ArrayList<String> rez = new ArrayList<String>();
		String sqlRequest ="SELECT CartItemsID, Login, Quantity FROM CartItems WHERE Login = ? AND ProductId = ?;";
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, sToken);
			query.setString(2, sProdID);
			rs = query.executeQuery();

			if (rs.next()) {
				rez.add(rs.getString("CartItemsID"));
				rez.add(rs.getString("Login"));
				rez.add(rs.getString("Quantity"));
			}
			else {
		        System.out.println("no data returned");
		    }

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rez;
	}
	
	public int queryInsertProductToCart(String login, String count, String productID) {
		PreparedStatement query = null;
		String sqlRequest ="INSERT INTO CartItems (Login, Quantity, ProductId) VALUES ( ?, ?, ?);";
		int rs =0;
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, login);
			query.setString(2, count);
			query.setString(3, productID);
			rs = query.executeUpdate();

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rs;
	}
	
	public int queryUpdateProductToCart(String count, String cartItemsID) {
		PreparedStatement query = null;
		String sqlRequest = "";
			sqlRequest ="UPDATE CartItems SET Quantity = ?  WHERE CartItemsID = ?;";
		
		int rs =0;
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, count);
			query.setString(2, cartItemsID);
			rs = query.executeUpdate();

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rs;
	}
	
	public boolean queryRemoveSession(String sesionStr) {
		PreparedStatement query = null;
		String sqlRequest ="DELETE FROM Session WHERE pubModule = ? ;";
		
		int rs =0;
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, sesionStr);
			rs = query.executeUpdate();

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rs == 1;
	}
	
	
	public int querySetNewUser(String login,String pass) {
		PreparedStatement query = null;
		int rs = 0;
		String sqlRequest ="INSERT INTO Users (Token, Log, Pass) VALUES (?, ?, 'User1');";
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, pass);
			query.setString(2, login);
			rs = query.executeUpdate();

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rs;
	}
	
	public JSONArray queryGetAllInCart(String login) {
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		String sqlRequest ="SELECT p.ProductID, p.ProductName, p.UnitPrice, c.Quantity" +
							" FROM CartItems AS c, Products AS p"+
							" WHERE c.ProductId = p.ProductID AND c.Login = '"+ login +"';";
		
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
	
	
	public boolean queryRemoveItemsCart(String login, String productID) {
		PreparedStatement query = null;
		String sqlRequest ="DELETE FROM CartItems WHERE Login = ? AND ProductId = ?;";
		
		int rs =0;
		try {
			query = MysqlIO.getConnection().prepareStatement(sqlRequest);
			query.setString(1, login);
			query.setString(2, productID);
			rs = query.executeUpdate();

			query.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rs == 1;
	}
	
}
