package com.ecommerce.Inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.ecommerce.dbo.ItemEntry;
import com.ecommerce.dbo.SQLibrary;
import com.fasterxml.jackson.databind.ObjectMapper;

//http://localhost:8080/JITShop/rest/c/inventory?brand=1
@Path("/c/inventory")
public class InventoryCategory {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand) throws Exception {

		String returnString = null;
		JSONArray json = new JSONArray();

		try {

			//return a error is brand is missing from the url string
			if(brand == null) {
				return Response.status(400).entity("Error: please specify brand for this search").build();
			}

			SQLibrary dao = new SQLibrary();

			json = dao.queryReturnBrandParts(brand);
			returnString = json.toString();

		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}
	
	/**
	 * This method will allow you to insert data the PC_PARTS table.  
	 * This is a example of using the Jackson Processor
	 * 
	 * Note: If you look, this method addPcParts using the same URL as a GET method returnBrandParts.
	 * 			We can do this because we are using different HTTP methods for the same URL string.
	 * 
	 * @param incomingData - must be in JSON format
	 * @return String
	 * @throws Exception
	 */
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception {

		String returnString = null;
		//JSONArray jsonArray = new JSONArray(); //not needed
		SQLibrary dao = new SQLibrary();

		try {
			System.out.println("incomingData: " + incomingData);

			/*
			 * ObjectMapper is from Jackson Processor framework
			 * http://jackson.codehaus.org/
			 * 
			 * Using the readValue method, you can parse the json from the http request
			 * and data bind it to a Java Class.
			 */
			ObjectMapper mapper = new ObjectMapper();
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);

			int http_code = dao.insertIntoCategories(itemEntry.CategoryName, 
													itemEntry.Description, 
													itemEntry.GroupID );

			if( http_code == 200 ) {
				//returnString = jsonArray.toString();
				returnString = "Item inserted";
			} else {
				return Response.status(500).entity("Unable to process Item").build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}

		return Response.ok(returnString).build();
	}
}
