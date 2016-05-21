package com.pugstunt.mastermind.conf;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MasterMindContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(
				new RestModule(),
				new PersistenceModule(),
				new ServiceModule()
			);
	}

}
