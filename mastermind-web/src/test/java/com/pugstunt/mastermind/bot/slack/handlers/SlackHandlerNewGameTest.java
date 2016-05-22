package com.pugstunt.mastermind.bot.slack.handlers;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.pugstunt.mastermind.conf.ServiceModule;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandler;
import com.pugstunt.mastermind.service.bot.slack.handler.SlackHandlerNewGame;

public class SlackHandlerNewGameTest {

	private SlackHandler handler = new SlackHandlerNewGame();

	@BeforeClass
	public static void init() {
		Guice.createInjector(new ServiceModule());
	}
	
	@Test
	public void newGameMessage() {
		
		SlackRequest request = new SlackRequest();
		request.setText("new game");
		request.setUserId("Franklin");
		request.setTeamId("PugStunt");
		request.setChannelId("NightHack");
		
		SlackResponse response = handler.apply(request);
		
		Assert.assertEquals(response.getText(), "Game Started - Franklin");
	}
	
}
