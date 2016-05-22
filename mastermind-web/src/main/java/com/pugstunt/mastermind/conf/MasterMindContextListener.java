package com.pugstunt.mastermind.conf;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MasterMindContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
	
		final String environment = System.getenv("environment");
		if ("local".equals(environment)) {
			System.out.println("local module");
			return Guice.createInjector(new MastermindLocal());
		}
		System.out.println("production module");
		return Guice.createInjector(new MastermindProduction());
	}

}
