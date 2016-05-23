package com.pugstunt.mastermind.service.bot.slack.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.core.entity.PastResult;
import com.pugstunt.mastermind.service.GameService;

public class SlackHandlerGuessTest {

	@Test
	public void testAccept() {
		SlackHandlerGuess handler = new SlackHandlerGuess(null);
		assertFalse(handler.accept(""));
		assertFalse(handler.accept("new game"));
		assertFalse(handler.accept("newgame"));
		assertFalse(handler.accept("hint"));
		assertFalse(handler.accept("help"));

		assertTrue(handler.accept("guess"));
		assertTrue(handler.accept("I guess"));
		assertTrue(handler.accept("my guess"));
	}

	@Test
	@Ignore
	public void testApply() {
		GameService service = Mockito.mock(GameService.class);
		GameEntry game = Mockito.mock(GameEntry.class);
		PastResult result = PastResult.builder().exact(2).near(3).guess("RGBRGBYY").build();
		Optional<PastResult> lastResult = Optional.of(result);
		
		Mockito.when(service.buildKey("USER-IDCHANNEL-IDTEAM-ID")).thenReturn("GAME-KEY");
		Mockito.when(service.checkGuess("GAME-KEY", Color.from("RGBRGBYY"))).thenReturn(game);
		Mockito.when(game.getLastResult()).thenReturn(lastResult);

		SlackHandlerGuess handler = new SlackHandlerGuess(service);

		SlackRequest request = buildRequest("Guess RGBRGBYY");

		SlackResponse response = handler.apply(request);
		assertEquals("Exact: 2 | Near: 3", response.getText());
	}

	private SlackRequest buildRequest(String text) {
		SlackRequest request = new SlackRequest();
		request.setText(text);
		request.setChannelId("CHANNEL-ID");
		request.setTeamId("TEAM-ID");
		request.setUserId("USER-ID");

		return request;
	}

}
