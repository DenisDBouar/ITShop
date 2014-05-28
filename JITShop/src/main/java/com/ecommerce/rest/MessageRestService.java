package com.ecommerce.rest;

import java.awt.PageAttributes.MediaType;
import java.sql.ResultSet;

import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.ecommerce.dbo.*;

//http://localhost:8080/JITShop/rest/message/hello%20world
@Path("/message")
public class MessageRestService {

	@GET
	@Path("/{param}")
	public Response printMessage(@PathParam("param") String msg) {

		String result = "Restful example : " + msg;

		return Response.status(200).entity(result).build();

	}
	@GET
	@Produces("text/html")
	public String retTitle(){
		return "GHJGJHGJKHGKI";
	}
	
	@GET
	@Path("/a")
	//@Produces(MediaType.TEXT_HTML)
	public String retTitle2(){
		return "G2222222222333333333333";
	}
	
	
	@GET
	@Path("/data")
	//@Produces(MediaType.TEXT_HTML)
	public String getData(){
		String sqlRequest ="SELECT * FROM JITShopDB.Groups";
		String retStr ="";
		
		ResultSet res = null;
		try {
			res = MysqlIO.getConnectionStatement().executeQuery(sqlRequest);
			while (res.next()) {
				retStr += res.getString(2);
			}
			MysqlIO.CloseConnection();
		} catch (Exception e) {
			MysqlIO.CloseConnection();
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
		}
		return retStr;
	}

}