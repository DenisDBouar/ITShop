package com.ecommerce.Inventory;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.ecommerce.dbo.MysqlIO;
import com.ecommerce.util.ToJSON;

@Path("/inventory")
public class Inventory_Groups {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPCParts(){
		String sqlRequest ="SELECT * FROM JITShopDB.Groups";
		String retStr ="pusto";
		Response rb = null;
		
		ResultSet res = null;
		try {
			res = MysqlIO.getConnectionStatement().executeQuery(sqlRequest);
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			json = converter.toJSONArray(res);
			
			MysqlIO.CloseConnection();
			
			retStr = json.toString();
			rb = Response.ok(retStr).build();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			MysqlIO.CloseConnection();
		}
		return rb;
	}
}