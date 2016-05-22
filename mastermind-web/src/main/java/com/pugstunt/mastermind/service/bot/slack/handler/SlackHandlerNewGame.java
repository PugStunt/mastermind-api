package com.pugstunt.mastermind.service.bot.slack.handler;

import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

public class SlackHandlerNewGame implements SlackHandler {

	@Override
	public boolean accept(String message) {
		return message.startsWith("new game");
	}

	@Override
	public SlackResponse apply(SlackRequest request) {
		
		
		// TODO implement handler for new game
		return new SlackResponse("Jogo iniciado " + request.getUsername());
	}

}
