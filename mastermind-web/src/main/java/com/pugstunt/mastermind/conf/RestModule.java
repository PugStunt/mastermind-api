package com.pugstunt.mastermind.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class RestModule extends JerseyServletModule {

	@Override
	protected void configureServlets() {
		
		bind(CorsFilter.class);
		
		final ResourceConfig rc = new PackagesResourceConfig("com.pugstunt.mastermind.api");
		for (Class<?> resource : rc.getClasses()) {
			bind(resource);
		}

		serve("/*").with(GuiceContainer.class);

	}

	@Provides
	@Singleton
	public JacksonJsonProvider getJacksonJsonProvider(final ObjectMapper mapper) {
		return new JacksonJsonProvider(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS);
	}

}
