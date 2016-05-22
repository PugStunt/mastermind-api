package com.pugstunt.mastermind.conf;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.pugstunt.mastermind.service.GameService;
import com.pugstunt.mastermind.service.SlackService;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandler;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerDefault;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerFactory;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerGuess;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerHelp;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerHint;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerNewGame;
import com.pugstunt.mastermind.store.GameStore;
import com.sun.jersey.api.core.HttpRequestContext;

public class SlackIntegrationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SlackHandlerFactory.class).in(Singleton.class);
		bind(SlackService.class).in(Singleton.class);
	}

	@Provides
	@Singleton
	public List<SlackHandler> getHandlers(GameService gameService, GameStore gameStore, HttpRequestContext context)
			throws Exception {

		final LinkedList<SlackHandler> handlers = Lists.newLinkedList();

		SlackHandlerNewGame handlerNewGame = new SlackHandlerNewGame(gameService, gameStore);
		SlackHandlerGuess handlerGuess = new SlackHandlerGuess(gameService, context);
		SlackHandlerHint handlerHint = new SlackHandlerHint(gameService, gameStore);
		SlackHandlerDefault handlerDefault = new SlackHandlerDefault(handlerGuess);
		SlackHandlerHelp handlerHelp = new SlackHandlerHelp();
		handlers.add(handlerNewGame);
		handlers.add(handlerGuess);
		handlers.add(handlerHint);
		handlers.add(handlerDefault);
		handlers.add(handlerHelp);
		return handlers;
	}

}