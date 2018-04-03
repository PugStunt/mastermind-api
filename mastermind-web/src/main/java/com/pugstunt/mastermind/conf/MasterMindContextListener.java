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
		return Guice.createInjector(new MastermindBootstrap());
	}

}
