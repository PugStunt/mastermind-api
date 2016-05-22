package com.pugstunt.mastermind.service.bot.slack.handler;

import javax.inject.Inject;

import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.core.domain.enums.Color;

public class SlackHandlerDefault implements SlackHandler {

	private SlackHandlerGuess slackHandlerGuess;

	@Inject
	public SlackHandlerDefault(SlackHandlerGuess slackHandlerGuess) {
		this.slackHandlerGuess = slackHandlerGuess;
	}

	@Override
	public boolean accept(String message) {
		return false;
	}

	@Override
	public SlackResponse apply(SlackRequest request) {

		try {
			Color.from(request.getTextWithouTrigger().replaceAll("\\s", ""));
			return slackHandlerGuess.apply(request);
		} catch (Exception e) {
			return new SlackResponse("Didn't understand, captain. Use 'help' command for further instructions.");
		}
	}

}
