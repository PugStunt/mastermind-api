package com.pugstunt.mastermind.service.bot.slack.handler;

import static java.lang.System.lineSeparator;

import java.util.List;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.core.entity.PastResult;
import com.pugstunt.mastermind.exception.MastermindException;
import com.pugstunt.mastermind.service.GameService;

public class SlackHandlerGuess implements SlackHandler {

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

		String[] splittedCommand = slackRequest.getText().split(" ");
		String guess = splittedCommand[splittedCommand.length - 1];
		List<Color> colors = Color.from(guess);
		String gameKey = gameService.buildKey(slackRequest.getKeyBase());
		GameEntry game = gameService.checkGuess(gameKey, colors);

		try {
			return new SlackResponse(responseText(game));
		} catch (MastermindException mex) {
			return new SlackResponse("No active game");
		} catch (IllegalArgumentException ex) {
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
