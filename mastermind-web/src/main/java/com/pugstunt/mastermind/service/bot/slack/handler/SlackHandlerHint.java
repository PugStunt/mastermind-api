package com.pugstunt.mastermind.service.bot.slack.handler;

import static java.util.Objects.isNull;

import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

public class SlackHandlerHint implements SlackHandler {

	@Override
	public boolean accept(String message) {

		return !isNull(message) && (message.startsWith("hint") || message.startsWith("suggestion"));
	}

	@Override
	public SlackResponse apply(SlackRequest request) {

		// TODO
		return null;
	}

}
