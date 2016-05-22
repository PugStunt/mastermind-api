package com.pugstunt.mastermind.service.bot.slack.handler;

import java.util.Optional;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.service.GameService;
import com.pugstunt.mastermind.service.bot.slack.SlackService;
import com.pugstunt.mastermind.store.GameStore;

public class SlackHandlerNewGame implements SlackHandler {

	private static final String GAME_STARTED_MESSAGE = "GAME STARTED";
	private static final String COMMAND = "new game";
	
	@Inject
	private SlackService slackService;
	
	@Inject
	private GameService gameService;
	
	@Inject
	private GameStore gameStore;
	
	@Override
	public boolean accept(String message) {
		return message.startsWith(COMMAND);
	}

	@Override
	public SlackResponse apply(SlackRequest slackRequest) {

		String userId = slackRequest.getUserId();
		String channelId = slackRequest.getChannelId();
		String teamId = slackRequest.getTeamId();

		final String gameKey = slackService.buildKey(userId, channelId, teamId);
		final Optional<GameEntry> game = gameStore.findByKey(gameKey);
		if (game.isPresent()) {
			finishGame(game.get());
		}
		gameService.newGame(slackRequest.getUsername(), gameKey);
		return new SlackResponse(GAME_STARTED_MESSAGE + " - " + slackRequest.getUsername());
	}
	
	private void finishGame(GameEntry game) {
		gameStore.remove(game.getGameKey());
	}

}
