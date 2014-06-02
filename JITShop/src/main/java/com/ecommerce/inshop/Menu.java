package com.ecommerce.inshop;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import com.ecommerce.dbo.SQLibrary;

@Path("/")
public class Menu {

	//http://localhost:8080/JITShop/rest/menu?id=1
		@GET
		@Path("/menu")
		//@Produces(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_HTML)
		public String returnProductList() throws Exception {
			String returnString = null;
			JSONArray json = new JSONArray();
			try {
			/*	if(id == null) {
					return Response.status(400).entity("Error: please specify brand for this search").build();
				}
	*/
				SQLibrary dao = new SQLibrary();
				json = dao.queryMenuCreator("groups", "");
				
				
				
				for (int i = 0; i < json.length(); i++) {
					String str = returnCategorytList(json.getJSONObject(i).getString("GroupID")).toString();
				     json.getJSONObject(i).put("Category", str);
				}
				
				returnString = json.toString();
			}
			catch (Exception e) {
			/*	e.printStackTrace();
				return Response.status(500).entity("Server was not able to process your request").build();*/
			}
			/*Debug*///System.out.println(">> DEBUG: menue>>" + returnString);
			//return Response.ok(returnString).build();
			return returnString;
		}

		public String returnCategorytList(String id) throws Exception {
			String returnString = null;
			JSONArray json = new JSONArray();
			try {
				SQLibrary dao = new SQLibrary();
				json = dao.queryMenuCreator("category", id);
				returnString = json.toString();
				/*Debug*///System.out.println(">> DEBUG: menue category>>" + returnString);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return returnString;
		}

}
