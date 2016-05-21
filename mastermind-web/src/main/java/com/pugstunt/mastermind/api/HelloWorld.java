package com.pugstunt.mastermind.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorld {

	@GET
	public String sayHello() {
		return "Hello MasterMind";
	}
	
}
