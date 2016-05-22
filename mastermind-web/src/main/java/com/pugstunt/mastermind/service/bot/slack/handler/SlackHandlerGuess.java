package com.pugstunt.mastermind.service.bot.slack.handler;

import java.util.List;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.core.entity.PastResult;
import com.pugstunt.mastermind.service.GameService;
import com.pugstunt.mastermind.service.bot.slack.SlackService;

public class SlackHandlerGuess implements SlackHandler {

	private static final String COMMAND = "guess";
	
	@Inject
	private SlackService slackService;
	
	@Inject
	private GameService gameService;
	
	@Override
	public boolean accept(String message) {
		return message.startsWith(COMMAND);
	}

	@Override
	public SlackResponse apply(SlackRequest slackRequest) {

		String userId = slackRequest.getUserId();
		String channelId = slackRequest.getChannelId();
		String teamId = slackRequest.getTeamId();

		String gameKey = slackService.buildKey(userId, channelId, teamId);
		
		String[] splittedCommand = slackRequest.getText().split(" ");
		if (splittedCommand.length < 2) {
			return new SlackResponse("Invalid Guess");
		}
		GameEntry game = gameService.checkGuess(gameKey, parseColors(splittedCommand[1]));
		
		return new SlackResponse(responseText(game));
	}

	private List<Color> parseColors(String guess) {
		return Color.from(guess);
	}
	
	private String responseText(GameEntry game) {

		StringBuilder builder = new StringBuilder();
		builder
			.append("User: ").append(game.getPlayer())
			.append("\n")
			.append("Guesses: ").append(game.getGuesses())
			.append("\n")
			.append("Past Results: [")
			.append("\n");
		
		for (PastResult pastResult : game.getPastResults()) {
			builder
				.append("{ Exact: ").append(pastResult.getExact())
				.append(", Near: ").append(pastResult.getNear())
				.append(", Guess: ").append(pastResult.getGuess())
				.append(" }")
				.append("\n");
		}
		
		builder.append("]");
		
		return builder.toString();
	}
	
}
