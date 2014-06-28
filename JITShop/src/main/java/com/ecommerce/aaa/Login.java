package com.ecommerce.aaa;

import java.io.File;
import java.io.IOException;
import java.net.URI;
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

import com.ecommerce.dbo.SQLibrary;

@Path("/")
public class Login {
	//http://localhost:8080/JITShop/rest/productlist?id=1
		
		@GET
		@Path("/login")
		@Produces(MediaType.TEXT_HTML)
		public String returnAbout(){
			
			String entireFileText ="null";
			try {
				 String urls = "/home/boa/Disc/D/Herguan/lectures/semester3/CS540/GIT/LocalEComerce/ITShop/JITShop/src/main/webapp/pages/LogIn.html";
				entireFileText = new Scanner(new File(urls)).useDelimiter("\\A").next();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return entireFileText;
		}
		
		
		@GET
		@Path("/getcookie")
		@Produces(MediaType.TEXT_HTML)
		public Response returnCookie(){
			
			try {
				EncoderDecoder encoderdecoder = new EncoderDecoder();
				encoderdecoder.generateKeys();
				encoderdecoder.createPubModExp();
				String pubmodulus = encoderdecoder.getpubModuls();
				String puexponenet = encoderdecoder.getpubExponent();
				String privmodulus = encoderdecoder.getPrivModuls().toString();
				String privexponenet = encoderdecoder.getPrivExponent().toString();
				
				SQLibrary dao = new SQLibrary();
				int sesionToken2 = dao.queryCreateSession(pubmodulus, puexponenet, privmodulus, privexponenet);
				if(sesionToken2 > 0){
					return Response.ok(pubmodulus+"|"+puexponenet).build();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return Response.serverError().build();
		}
		
		
		@POST
		@Path("/login2")
		@Consumes(MediaType.TEXT_HTML)
		@Produces(MediaType.TEXT_HTML)
		public Response addPcParts2(String token){
			String str = chekIfUserExist(token);
			if(!str.equals("")){
				return Response.ok(str.split("#")[0]).build();
			}
			else{
				return Response.serverError().build();
			}
		}
		
		@DELETE @Path("/logout/{id}")
		@Produces(MediaType.TEXT_HTML)
		public void remove(@PathParam("id") String id) {
			SQLibrary dao = new SQLibrary();
			dao.queryRemoveSession(id.split("\\|")[0]);
		}
		
		public String chekIfUserExist(String token){

			
			SQLibrary dao = new SQLibrary();
			
			String SessionID ="";
			String pubModule ="";
			String pubExponent ="";
			String privModule ="";
			String privExponent ="";
			JSONArray json = new JSONArray();
			
			
			
				String sesionToken = dao.queryFindSession2(token.split(",")[0]);
				if(!sesionToken.equals("")){
					String[] str = sesionToken.split(",");
					SessionID = str[0];
					pubModule = str[1];
					pubExponent = str[2];
					privModule = str[3];
					privExponent = str[4];
					EncoderDecoder encoderdecoder = new EncoderDecoder();
					try {
						String strDecoded = encoderdecoder.decription(token.split(",")[1], privModule, privExponent);
						if(!strDecoded.equals("")){
							String[] splitDecoded = strDecoded.split(",");
							json = dao.queryFindUser(splitDecoded[0], splitDecoded[1]);
							if(json.length() > 0){
								return json.getJSONObject(0).getString("Pass") +"|"+ token.split(",")[1] +"#"+ splitDecoded[0];
							}
							else{
								return "";
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				else{
					return "";
				}
			
			return "";
		
		} 
}
