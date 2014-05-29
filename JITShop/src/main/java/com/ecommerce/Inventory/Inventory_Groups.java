package com.ecommerce.Inventory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ecommerce.dbo.SQLibrary;

@Path("/g/inventory")
public class Inventory_Groups {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPCParts(){
		
		String retStr ="pusto";
		Response rb = null;
		
			SQLibrary sqlibrary = new SQLibrary();
			retStr = sqlibrary.queryReturnJArray().toString();
			rb = Response.ok(retStr).build();
		
		return rb;
	}
}