package com.pugstunt.mastermind.service.bot.slack.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Test;

import com.google.common.io.Files;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

public class SlackHandlerHelpTest {

	@Test
	public void acceptanceTest() {

		SlackHandlerHelp handler = new SlackHandlerHelp();
		assertFalse(handler.accept(""));
		assertFalse(handler.accept("new game"));
		assertFalse(handler.accept("newgame"));
		assertFalse(handler.accept("guess"));
		assertFalse(handler.accept("hint"));
		assertTrue(handler.accept("help"));
		assertTrue(handler.accept("HELP"));
	}

	@Test
	public void helpRequestTest() throws IOException {

		StringBuilder sb = new StringBuilder();
		List<String> readLines = Files.readLines(new File(SlackHandlerHelpTest.class.getResource("help.info").getPath()),
				Charset.defaultCharset());
		readLines.forEach(line -> sb.append(line).append(System.lineSeparator()));

		SlackResponse response = new SlackHandlerHelp().apply(buildRequest("help"));
		assertEquals(sb.toString(), response.getText());
	}

	private SlackRequest buildRequest(String triggerWord) {
		SlackRequest request = new SlackRequest();
		return request;
	}

}
