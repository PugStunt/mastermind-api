package com.pugstunt.mastermind.conf;

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

		serve("/*").with(GuiceContainer.class);
	}
	
}
