package com.pugstunt.mastermind.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MasterMindContextListener extends GuiceServletContextListener {

	static final Logger logger = LoggerFactory.getLogger(MasterMindContextListener.class);
	
	@Override
	protected Injector getInjector() {
	
		final String environment = System.getenv("environment");
		if ("local".equals(environment)) {
			logger.info("Starting env={} with injector MastermindLocal", environment);
			return Guice.createInjector(new MastermindLocal());
		}
		logger.info("Starting env={} with injector ProductionModule", environment);
		return Guice.createInjector(new MastermindProduction());
	}

}
