package com.pugstunt.mastermind.conf;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.pugstunt.mastermind.core.domain.ErrorResponse;
import com.pugstunt.mastermind.exception.GameNotFoundException;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class RestModule extends JerseyServletModule {

	@Override
	protected void configureServlets() {
		
		final ResourceConfig rc = new PackagesResourceConfig("com.pugstunt.mastermind.api");
		for (Class<?> resource : rc.getClasses()) {
			bind(resource);
		}
		
		bind(MastermindExceptionMapper.class).in(Singleton.class);
		
		filter("/*").through(CORSResponseFilter.class);
		serve("/*").with(GuiceContainer.class);

	}

	@Provides
	@Singleton
	public JacksonJsonProvider getJacksonJsonProvider(final ObjectMapper mapper) {
		return new JacksonJsonProvider(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
	}
	
	@Provider
	public static class MastermindExceptionMapper implements ExceptionMapper<RuntimeException> {

		static final Logger logger = LoggerFactory.getLogger(MastermindExceptionMapper.class);
		
		@Override
		public Response toResponse(RuntimeException ex) {

			logger.error("Error", ex);
			
			ErrorResponse response = new ErrorResponse();
			response.setStatus("Internal Server Error");
			response.setText(ex.getMessage());
			return Response.status(500).entity(response).build();
		}
		
	}
	
	@Provider
	public static class GameNotFoundExceptionMapper implements ExceptionMapper<GameNotFoundException> {

		@Override
		public Response toResponse(GameNotFoundException ex) {
			
			ErrorResponse response = new ErrorResponse();
			response.setStatus("Internal Server Error");
			response.setText("No active game");
			return Response.status(500).entity(response).build();
		}
		
	}
	
	
}
