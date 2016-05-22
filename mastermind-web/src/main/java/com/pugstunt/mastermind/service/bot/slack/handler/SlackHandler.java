package com.pugstunt.mastermind.service.bot.slack.handler;

import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;

public interface SlackHandler {

	boolean accept(String message);

	SlackResponse apply(SlackRequest request);

}
