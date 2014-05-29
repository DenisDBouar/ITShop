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

import com.ecommerce.dbo.SQLibrary;

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
}
