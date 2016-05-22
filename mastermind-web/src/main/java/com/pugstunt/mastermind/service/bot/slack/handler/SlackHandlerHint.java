package com.pugstunt.mastermind.service.bot.slack.handler;

import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

public class SlackHandlerHint implements SlackHandler {

	@Override
	public boolean accept(String message) {
		// TODO also apply for 'suggest'
		// TODO create a handler for help
		return message.startsWith("hint");
	}

	@Override
	public SlackResponse apply(SlackRequest request) {
		return null;
	}

}
