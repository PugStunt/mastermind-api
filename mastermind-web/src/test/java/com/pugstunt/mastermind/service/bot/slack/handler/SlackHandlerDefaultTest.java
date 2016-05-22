package com.pugstunt.mastermind.service.bot.slack.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.mockito.Mockito;

import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

public class SlackHandlerDefaultTest {

	SlackHandlerGuess handlerGuess;

	@Test
	public void noAcceptanceTest() {

		SlackHandlerDefault handler = new SlackHandlerDefault(null);
		assertFalse(handler.accept(null));
		assertFalse(handler.accept(""));
		assertFalse(handler.accept("new game"));
		assertFalse(handler.accept("newgame"));
		assertFalse(handler.accept("guess"));
		assertFalse(handler.accept("hint"));
	}

	@Test
	public void unknownGuessTest() {
		SlackRequest request = buildRequest("AAAAA");

		SlackResponse response = new SlackHandlerDefault(null).apply(request);
		String text = response.getText();

		assertEquals("Didn't understand, captain", text);
	}

	@Test
	public void guessOnlyMessageTest() {

		SlackRequest request = buildRequest("RGBRGBRG");

		SlackHandlerGuess handlerGuess = Mockito.mock(SlackHandlerGuess.class);
		Mockito.when(handlerGuess.apply(request)).thenReturn(null);

		new SlackHandlerDefault(handlerGuess).apply(request);
		Mockito.verify(handlerGuess).apply(request);
	}

	@Test
	public void guessOnlySpacedMessageTest() {

		SlackRequest request = buildRequest("R G B R G B R G");

		SlackHandlerGuess handlerGuess = Mockito.mock(SlackHandlerGuess.class);
		Mockito.when(handlerGuess.apply(request)).thenReturn(null);

		new SlackHandlerDefault(handlerGuess).apply(request);
		Mockito.verify(handlerGuess).apply(request);
	}

	private SlackRequest buildRequest(String text) {
		SlackRequest request = new SlackRequest();
		request.setChannelId("channelId1");
		request.setChannelName("hannelName");
		request.setText(text);
		request.setUsername("username");
		request.setTriggerWord("mm");
		return request;
	}
}
