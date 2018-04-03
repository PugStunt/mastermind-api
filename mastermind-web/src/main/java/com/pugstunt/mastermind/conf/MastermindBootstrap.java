package com.pugstunt.mastermind.conf;

import com.google.inject.AbstractModule;

public class MastermindBootstrap extends AbstractModule {

	@Override
	protected void configure() {
		install(new RestModule());
		install(new PersistenceModule());
		install(new ServiceModule());
		install(new SwaggerModule());
	}

}
