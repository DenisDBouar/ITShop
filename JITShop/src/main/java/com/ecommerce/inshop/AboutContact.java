package com.ecommerce.inshop;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class AboutContact {
	
	@GET
	@Path("/about")
	@Produces(MediaType.TEXT_HTML)
	public String returnAbout(){
		
		String entireFileText ="null";
		try {
			 String urls = "/home/boa/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JITShop/WEB-INF/classes/pages/About.html";
			entireFileText = new Scanner(new File(urls)).useDelimiter("\\A").next();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return entireFileText;
	}
	
	@GET
	@Path("/contact")
	@Produces(MediaType.TEXT_HTML)
	public String returnContact(){
		
		String entireFileText ="null";
		try {
			 String urls = "/home/boa/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JITShop/WEB-INF/classes/pages/Contact.html";
		        
			entireFileText = new Scanner(new File(urls)).useDelimiter("\\A").next();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return entireFileText;
	}
	
	@GET
	@Path("/admin")
	@Produces(MediaType.TEXT_HTML)
	public String returnAdmin(){
		
		String entireFileText ="null";
		try {
			 String urls = "/home/boa/Disc/D/Herguan/lectures/semester3/CS540/GIT/LocalEComerce/ITShop/JITShop/src/main/webapp/pages/AdminPage.htm";
		        
			entireFileText = new Scanner(new File(urls)).useDelimiter("\\A").next();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return entireFileText;
	}

}

