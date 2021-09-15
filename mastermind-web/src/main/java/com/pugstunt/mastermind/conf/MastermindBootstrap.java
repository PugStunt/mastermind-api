package com.pugstunt.mastermind.conf;

import com.google.inject.AbstractModule;
import com.pugstunt.mastermind.conf.persistence.FirestoreModule;
import com.pugstunt.mastermind.conf.persistence.RedisModule;

public class MastermindBootstrap extends AbstractModule {

	@Override
	protected void configure() {

		final String persistenceModule = System.getenv("profile.persistence.module");
		switch (persistenceModule) {
			case "redis": {
				install(new RedisModule());
			}
			break;
			case "firestore": {
				install(new FirestoreModule());
			}
			break;
			default: {
				throw new Error("No Persistence module specified (redis / firestore)");
			}
		}

		install(new RestModule());
		install(new ServiceModule());
		install(new SwaggerModule());
	}

}
