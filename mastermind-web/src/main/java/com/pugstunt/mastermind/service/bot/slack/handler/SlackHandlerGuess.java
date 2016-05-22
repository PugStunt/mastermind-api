package com.pugstunt.mastermind.service.bot.slack.handler;

import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

public class SlackHandlerGuess implements SlackHandler {

	@Override
	public boolean accept(String message) {
		return message.startsWith("guess");
	}

	@Override
	public SlackResponse apply(SlackRequest request) {

		
		// TODO implements handler for guess
		return null;
	}

}
