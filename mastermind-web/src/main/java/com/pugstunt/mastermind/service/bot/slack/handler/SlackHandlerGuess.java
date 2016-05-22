package com.pugstunt.mastermind.service.bot.slack.handler;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponseAttachment;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.core.entity.PastResult;
import com.pugstunt.mastermind.service.GameService;
import com.sun.jersey.api.core.HttpRequestContext;

public class SlackHandlerGuess implements SlackHandler {

	private static final String[] COMMANDS = { "guess", "my guess", "i guess" };
	static final Logger logger = LoggerFactory.getLogger(SlackHandlerGuess.class);

	private GameService gameService;

	private HttpRequestContext context;

	@Inject
	public SlackHandlerGuess(GameService gameService, HttpRequestContext context) {
		this.gameService = gameService;
		this.context = context;
	}

	@Override
	public boolean accept(String message) {
		for (String command : COMMANDS) {
			if (message.toLowerCase().startsWith(command)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public SlackResponse apply(SlackRequest slackRequest) {

		final String[] splittedCommand = slackRequest.getText().split(" ");
		final String guess = splittedCommand[splittedCommand.length - 1];
		final List<Color> colors = Color.from(guess);
		final String gameKey = gameService.buildKey(slackRequest.getKeyBase());
		final GameEntry game = gameService.checkGuess(gameKey, colors);

		SlackResponse response = new SlackResponse();
		SlackResponseAttachment attachment = SlackResponseAttachment.info(responseText(game, guess));
		String domain = context.getRequestUri().toString();
		attachment.setImageUrl(domain + "/v1/image/" + guess.toUpperCase() + ".png");
		response.getAttachments().add(attachment);

		return response;
	}

	private String responseText(GameEntry game, String guess) {
		Optional<PastResult> lastResult = game.getLastResult();

		if (lastResult.isPresent()) {
			PastResult result = lastResult.get();
			
			return new StringBuilder()
				.append("Exact: ").append(result.getExact())
				.append(" | ")
				.append("Near: ").append(result.getNear())
				.toString();
		}

		return StringUtils.EMPTY;
	}

}
