package com.ecommerce.inshop;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;

import com.ecommerce.dbo.SQLibrary;

@Path("/")
public class Admin {
	@GET
	@Path("/getallproducts")
	@Produces(MediaType.TEXT_HTML)
	public String returnAllProducts() throws Exception {
		String returnString = null;
		JSONArray json = new JSONArray();

		try {
			SQLibrary dao = new SQLibrary();

			json = dao.queryMenuCreator("allProducts", "");
			returnString = json.toString();
			
		}
		catch (Exception e) {
		}
		return returnString;
	}
}
