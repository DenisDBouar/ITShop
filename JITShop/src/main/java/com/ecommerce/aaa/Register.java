package com.ecommerce.aaa;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import com.ecommerce.dbo.SQLibrary;

@Path("/")
public class Register {
		
		@GET
		@Path("/register")
		@Produces(MediaType.TEXT_HTML)
		public String returnAbout(){
			
			String entireFileText ="null";
			try {
				 String urls = "/home/boa/Disc/D/Herguan/lectures/semester3/CS540/GIT/LocalEComerce/ITShop/JITShop/src/main/webapp/pages/Register.html";
				entireFileText = new Scanner(new File(urls)).useDelimiter("\\A").next();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			return entireFileText;
		}
		
		@POST
		@Path("/register2")
		@Consumes(MediaType.TEXT_HTML)
		@Produces(MediaType.TEXT_HTML)
		public Response addPcParts2(String token) throws Exception {
			/*JSONArray json = new JSONArray();
			int status = 0;
			SQLibrary dao = new SQLibrary();
			//json = dao.queryFindUser(token);
			if(json.length() == 0){
				status = dao.querySetNewUser(token);
			}
			
			return Response.ok(Integer.toString(status)).build();*/
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
							if(json.length() == 0){
								int rez = dao.querySetNewUser(splitDecoded[0], splitDecoded[1]);
								return Response.ok(String.valueOf(rez)).build();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
				else{
					return Response.serverError().build();
				}
			
			return Response.serverError().build();
		}
}
