package com.pugstunt.mastermind.service.bot.slack.handler;

import static java.lang.System.lineSeparator;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.core.entity.PastResult;
import com.pugstunt.mastermind.exception.MastermindException;
import com.pugstunt.mastermind.service.GameService;

public class SlackHandlerGuess implements SlackHandler {

	private static final String COMMAND = "guess";


	private GameService gameService;

	@Inject
	public SlackHandlerGuess(GameService gameService) {
		this.gameService = gameService;

	}

	@Override
	public boolean accept(String message) {
		return message.startsWith(COMMAND);
	}

	@Override
	public SlackResponse apply(SlackRequest slackRequest) {

		String gameKey = gameService.buildKey(slackRequest.getKeyBase());

		String command = slackRequest.getText();
		String guess;
		if (command.startsWith(COMMAND)) {
			String[] splittedCommand = slackRequest.getText().split(" ");
			if (splittedCommand.length < 2) {
				return new SlackResponse("Invalid Guess");
			}
			guess = splittedCommand[1];
		} else {
			guess = command;
		}
		try {
			GameEntry game = gameService.checkGuess(gameKey, Color.from(guess));
			return new SlackResponse(responseText(game));
		} catch(MastermindException mex) {
			return new SlackResponse("No active game");
		} catch(IllegalArgumentException ex) {
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
