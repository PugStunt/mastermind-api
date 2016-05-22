package com.pugstunt.mastermind.conf;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandler;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerDefault;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerGuess;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerHint;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerNewGame;

public class SlackIntegrationModule extends AbstractModule {

	@Override
	protected void configure() {
	}

	@Provides
	@Singleton
	public List<SlackHandler> getHandlers() throws Exception {

		final LinkedList<SlackHandler> handlers = Lists.newLinkedList();
		handlers.add(handlerNewGame());
		handlers.add(handlerGuess());
		handlers.add(handlerHint());
		handlers.add(handlerDefault());
		return handlers;
	}

	@Provides
	@Singleton
	public SlackHandlerNewGame handlerNewGame() {
		return new SlackHandlerNewGame();
	}

	@Provides
	@Singleton
	public SlackHandlerGuess handlerGuess() {
		return new SlackHandlerGuess();
	}

	@Provides
	@Singleton
	public SlackHandlerHint handlerHint() {
		return new SlackHandlerHint();
	}

	@Provides
	@Singleton
	public SlackHandlerDefault handlerDefault() {
		return new SlackHandlerDefault(handlerGuess());
	}

}
