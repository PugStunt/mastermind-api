package com.pugstunt.mastermind.conf;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandler;

public class SlackIntegrationModule extends AbstractModule {

	@Override
	protected void configure() {
	}

	@Provides
	@Singleton
	public List<SlackHandler> getHandlers() throws Exception {
	
		final LinkedList<SlackHandler> handlers = Lists.newLinkedList();
		Reflections reflection = 
				new Reflections(
						new ConfigurationBuilder()
							.setUrls(ClasspathHelper.forJavaClassPath())
						);
		
		Set<Class<? extends SlackHandler>> handlersClass = reflection.getSubTypesOf(SlackHandler.class);
		for (Class<? extends SlackHandler> handlerClass : handlersClass) {
			handlers.add(handlerClass.newInstance());
		}
		
		return handlers;
	}
	
}
