package com.ecommerce.inshop;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.ecommerce.aaa.Login;
import com.ecommerce.dbo.SQLibrary;

@Path("/")
public class Cart {
		
		@GET
		@Path("/cart")
		@Produces(MediaType.TEXT_HTML)
		public String returnAbout(){
			
			String entireFileText ="null";
			try {
				 String urls = "/home/boa/Disc/D/Herguan/lectures/semester3/CS540/GIT/LocalEComerce/ITShop/JITShop/src/main/webapp/pages/ShoppingCart.html";
				entireFileText = new Scanner(new File(urls)).useDelimiter("\\A").next();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return entireFileText;
		}
		
		@POST
		@Path("/cart2")
		@Consumes(MediaType.TEXT_HTML)
		@Produces(MediaType.TEXT_HTML)
		public Response addProdToCart(String login) throws Exception {
			String[] strSplit = login.split("\\|");
			String str = new Login().chekIfUserExist(strSplit[0] + "," + strSplit[3].split("_")[0]);
			if(!str.equals("")){
				SQLibrary dao = new SQLibrary();
					ArrayList<String> count = dao.queryGetCountCart(str.split("#")[1], strSplit[3].split("_")[1]);
					
					if(count.isEmpty()){
						//create new
						dao.queryInsertProductToCart(str.split("#")[1], "1",  strSplit[3].split("_")[1]);
						return Response.ok("new created").build();
					}
					else{
						//update
						int intCount = Integer.parseInt(count.get(2)) + 1;
						dao.queryUpdateProductToCart(Integer.toString(intCount),  count.get(0));
						return Response.ok("ipdated").build();
					}
			}
			else{
				return Response.serverError().build();
			}
		}

		@GET
		@Path("/cart3/{param}")
		@Produces(MediaType.TEXT_HTML)
		public String getFromCart(@PathParam("param") String cookie) {
			String returnString = null;
			JSONArray json = new JSONArray();
			
			String[] strSplit = cookie.split("\\|");
			String str = new Login().chekIfUserExist(strSplit[0] + "," + strSplit[3]);
			if(!str.equals("")){
				try {
					SQLibrary dao = new SQLibrary();
					
					json = dao.queryGetAllInCart(str.split("#")[1]);
					returnString = json.toString();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				return returnString;
					
			}
			else{
				return returnString;
			}
			
		}
		
		@DELETE
		@Path("/cart4/{cookie}")
		@Produces(MediaType.TEXT_HTML)
		public void remove(@PathParam("cookie") String cookie) {
			
			String[] strSplit = cookie.split("\\|");
			String str = new Login().chekIfUserExist(strSplit[0] + "," + strSplit[3].split(",")[0]);
			if(!str.equals("")){
				
				String[] chek = strSplit[3].split(",");
				SQLibrary dao = new SQLibrary();
				try {
					for(int i=1; i < chek.length; i++){
						String a =chek[i];
						dao.queryRemoveItemsCart(str.split("#")[1], chek[i]);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			
			
			
		}

}
