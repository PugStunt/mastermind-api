package com.pugstunt.mastermind.conf;

import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

@Provider
public class CorsFilter implements ContainerResponseFilter {

	@Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
		
		response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHttpHeaders().add("Access-Control-Allow-Headers", "content-type, accept");
		
		return response;
	}

}
