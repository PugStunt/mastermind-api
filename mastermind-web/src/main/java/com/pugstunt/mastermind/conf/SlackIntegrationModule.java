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
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerCredits;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerDefault;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerFactory;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerGuess;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerHelp;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerHint;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerNewGame;
import com.pugstunt.mastermind.store.GameStore;

public class SlackIntegrationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SlackHandlerFactory.class).in(Singleton.class);
		bind(SlackService.class).in(Singleton.class);
	}

	@Provides
	@Singleton
	public List<SlackHandler> getHandlers(GameService gameService, GameStore gameStore)
			throws Exception {

		final LinkedList<SlackHandler> handlers = Lists.newLinkedList();

		SlackHandlerNewGame handlerNewGame = new SlackHandlerNewGame(gameService, gameStore);
		SlackHandlerGuess handlerGuess = new SlackHandlerGuess(gameService);
		SlackHandlerHint handlerHint = new SlackHandlerHint(gameService, gameStore);
		SlackHandlerDefault handlerDefault = new SlackHandlerDefault(handlerGuess);
		SlackHandlerHelp handlerHelp = new SlackHandlerHelp();
		SlackHandlerCredits handlerCredits = new SlackHandlerCredits();
		handlers.add(handlerNewGame);
		handlers.add(handlerGuess);
		handlers.add(handlerHint);
		handlers.add(handlerDefault);
		handlers.add(handlerHelp);
		handlers.add(handlerCredits);
		return handlers;
	}

}