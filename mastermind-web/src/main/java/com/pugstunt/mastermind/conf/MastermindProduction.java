package com.pugstunt.mastermind.conf;

import com.google.inject.AbstractModule;
import com.pugstunt.mastermind.conf.env.ConnectionRedis;
import com.pugstunt.mastermind.conf.env.RedisHeroku;

public class MastermindProduction extends AbstractModule {

	@Override
	protected void configure() {
		bind(ConnectionRedis.class).to(RedisHeroku.class);
		
		install(new RestModule());
		install(new PersistenceModule());
		install(new ServiceModule());
		install(new SlackIntegrationModule());
	}

}
