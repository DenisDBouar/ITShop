package com.ecommerce.app;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.ecommerce.rest.MessageRestService;

public class MessageApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public MessageApplication() {
		System.out.println("<< MessageApplication >>");
		singletons.add(new MessageRestService());
	}

	@Override
	public Set<Object> getSingletons() {
		System.out.println("<<getSingletons>>");
		return singletons;
	}
}
