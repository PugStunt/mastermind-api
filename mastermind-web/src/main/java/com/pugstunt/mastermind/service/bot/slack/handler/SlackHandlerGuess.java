package com.pugstunt.mastermind.service.bot.slack.handler;

import static java.lang.System.lineSeparator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.core.entity.PastResult;
import com.pugstunt.mastermind.exception.NoActiveGameException;
import com.pugstunt.mastermind.service.GameService;

public class SlackHandlerGuess implements SlackHandler {

	static final Logger logger = LoggerFactory.getLogger(SlackHandlerGuess.class);
	
	private static final String[] COMMANDS = {"guess", "my guess", "i guess"};

	private GameService gameService;

	@Inject
	public SlackHandlerGuess(GameService gameService) {
		this.gameService = gameService;
	}

	@Override
	public boolean accept(String message) {
		for (String command : COMMANDS) {
			if (message.startsWith(command)) {
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

		try {
			return new SlackResponse(responseText(game));
		} catch (NoActiveGameException ex) {
			logger.info("No active game found for gameKey={}", gameKey);
			return new SlackResponse("No active game");
		} catch (IllegalArgumentException ex) {
			logger.info("Invalid guess code {}", guess);
			return new SlackResponse("Invalid Guess");
		}
	}

	private String responseText(GameEntry game) {

		StringBuilder builder = new StringBuilder();
		builder
			.append("User: ").append(game.getPlayer())
			.append(lineSeparator())
			.append("Guesses: ").append(game.getGuesses())
			.append(lineSeparator())
			.append("Past Results: [")
			.append(lineSeparator());

		for (PastResult pastResult : game.getPastResults()) {
			builder
				.append("{ Exact: ").append(pastResult.getExact())
				.append(", Near: ").append(pastResult.getNear())
				.append(", Guess: ").append(pastResult.getGuess())
				.append(" }")
				.append(lineSeparator());
		}

		builder.append("]");

		return builder.toString();
	}

}
