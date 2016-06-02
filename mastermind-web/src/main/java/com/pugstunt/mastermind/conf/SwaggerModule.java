package com.pugstunt.mastermind.conf;

import com.google.inject.AbstractModule;

import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

public class SwaggerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ApiListingResource.class).asEagerSingleton();
		bind(SwaggerSerializers.class).asEagerSingleton();
	}
	
}
