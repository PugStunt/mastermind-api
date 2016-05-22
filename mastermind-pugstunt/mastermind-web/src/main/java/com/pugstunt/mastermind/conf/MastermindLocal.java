package com.pugstunt.mastermind.conf;

import com.google.inject.AbstractModule;
import com.pugstunt.mastermind.conf.env.ConnectionRedis;
import com.pugstunt.mastermind.conf.env.RedisLocal;

public class MastermindLocal extends AbstractModule {

	@Override
	protected void configure() {
		bind(ConnectionRedis.class).to(RedisLocal.class);
		
		install(new RestModule());
		install(new PersistenceModule());
		install(new ServiceModule());
		install(new SlackIntegrationModule());
	}

}
