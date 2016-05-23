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
		assertFalse(handler.accept("help"));
	}

	@Test
	public void unknownGuessTest() {
		SlackRequest request = buildRequest("AAAAA");

		SlackResponse response = new SlackHandlerDefault(null).apply(request);
		assertEquals("Didn't understand, captain. Use 'help' command for further instructions.", response.getText());
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
	public void guessInvalidSpacedMessageTest() {

		SlackRequest request = buildRequest("R G B R G B R G");

		SlackResponse response = new SlackHandlerDefault(null).apply(request);
		assertEquals("Didn't understand, captain. Use 'help' command for further instructions.", response.getText());
	}

	private SlackRequest buildRequest(String text) {
		SlackRequest request = new SlackRequest();
		request.setText(text);
		return request;
	}

}
