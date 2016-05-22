package com.pugstunt.mastermind.core.domain.bot.slack;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SlackRequestTest {

	@Test
	public void testGetTextWithouTrigger() {
		SlackRequest slackRequest = new SlackRequest();
		
		slackRequest.setText("FOO BAR");
		assertEquals("FOO BAR", slackRequest.getTextWithouTrigger());
		
		slackRequest.setTriggerWord("FOO");
		assertEquals(" BAR", slackRequest.getTextWithouTrigger());
	}

}
