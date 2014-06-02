package com.ecommerce.inshop;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.ecommerce.dbo.SQLibrary;

@Path("/")
public class ProductList {
	
	
	
	//http://localhost:8080/JITShop/rest/productlist?id=1
	@GET
	@Path("/productlist")
	//@Produces(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String returnProductList(@QueryParam("id") String id) throws Exception {
		String returnString = null;
		JSONArray json = new JSONArray();

		try {
		/*	if(id == null) {
				return Response.status(400).entity("Error: please specify brand for this search").build();
			}
*/
			SQLibrary dao = new SQLibrary();

			json = dao.queryReturnProductList(id);
			returnString = json.toString();
			
		}
		catch (Exception e) {
		/*	e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();*/
		}
		/*Debug*///System.out.println(">> DEBUG: productlist>>" + returnString);
		//return Response.ok(returnString).build();
		return returnString;
	}
	
	@GET
	@Path("/productlistserch")
	@Produces(MediaType.TEXT_HTML)
	public String returnProductListSearch(@QueryParam("val") String val) throws Exception {
		String returnString = null;
		JSONArray json = new JSONArray();

		try {
			SQLibrary dao = new SQLibrary();

			json = dao.queryMenuCreator("search", val);
			returnString = json.toString();
			
		}
		catch (Exception e) {
		}
		return returnString;
	}

	@GET
	@Path("/productdetails")
	@Produces(MediaType.TEXT_HTML)
	public String productdetails(@QueryParam("id") String id) throws Exception {
		String returnString = null;
		JSONArray json = new JSONArray();

		try {
			SQLibrary dao = new SQLibrary();

			json = dao.queryMenuCreator("description", id);
			returnString = json.toString();
			
		}
		catch (Exception e) {
		}
		return returnString;
	}

	

}
