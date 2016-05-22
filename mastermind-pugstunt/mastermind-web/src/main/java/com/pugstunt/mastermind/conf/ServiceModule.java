package com.pugstunt.mastermind.conf;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.pugstunt.mastermind.service.GameService;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(GameService.class).in(Scopes.SINGLETON);
	}

}
