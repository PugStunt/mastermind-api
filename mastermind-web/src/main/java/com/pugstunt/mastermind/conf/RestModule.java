package com.pugstunt.mastermind.conf;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.pugstunt.mastermind.core.domain.ErrorResponse;
import com.pugstunt.mastermind.exception.MastermindException;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class RestModule extends JerseyServletModule {

	@Override
	protected void configureServlets() {
		
		bind(MastermindExceptionMapper.class).in(Singleton.class);
		
		final ResourceConfig rc = new PackagesResourceConfig("com.pugstunt.mastermind.api");
		for (Class<?> resource : rc.getClasses()) {
			bind(resource);
		}

		filter("/mastermind/*").through(CORSResponseFilter.class);
		
		serve("/mastermind/*").with(GuiceContainer.class);

	}

	@Provides
	@Singleton
	public JacksonJsonProvider getJacksonJsonProvider(final ObjectMapper mapper) {
		return new JacksonJsonProvider(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
	}
	
	@Provider
	public static class MastermindExceptionMapper implements ExceptionMapper<MastermindException> {

		@Override
		public Response toResponse(MastermindException ex) {
			
			ErrorResponse response = new ErrorResponse();
			response.setStatus("Internal Server Error");
			response.setMessage(ex.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}

}
