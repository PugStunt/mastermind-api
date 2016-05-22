package com.pugstunt.mastermind.bot.slack.handlers;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pugstunt.mastermind.conf.SlackIntegrationModule;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandler;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerDefault;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerFactory;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerGuess;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerHint;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerNewGame;

public class SlackHandlerTest {

	private static SlackHandlerFactory handlerFactory;
	
	@BeforeClass
	public static void init() {
		Injector injector = Guice.createInjector(new SlackIntegrationModule());
		handlerFactory = injector.getInstance(SlackHandlerFactory.class);
	}
	
	@Test
	public void newGameCommand() {
		SlackHandler handler = handlerFactory.getHandlerFor("new game");
		Assert.assertTrue(handler instanceof SlackHandlerNewGame);
	}

	@Test
	public void guessCommand() {
		SlackHandler handler = handlerFactory.getHandlerFor("guess");
		Assert.assertTrue(handler instanceof SlackHandlerGuess);
	}
	
	@Test
	public void hintCommand() {
		SlackHandler handler = handlerFactory.getHandlerFor("hint");
		Assert.assertTrue(handler instanceof SlackHandlerHint);
	}
	
	@Test
	public void unknownCommand() {
		SlackHandler handler = handlerFactory.getHandlerFor("someInvalidCommand");
		Assert.assertTrue(handler instanceof SlackHandlerDefault);
	}
	
}
