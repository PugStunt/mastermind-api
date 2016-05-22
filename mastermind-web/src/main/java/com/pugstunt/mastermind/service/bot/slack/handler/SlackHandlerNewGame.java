package com.pugstunt.mastermind.service.bot.slack.handler;

import java.util.Optional;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.service.GameService;
import com.pugstunt.mastermind.store.GameStore;

public class SlackHandlerNewGame implements SlackHandler {

	private static final String GAME_STARTED_MESSAGE = "Game Started";
	private static final String COMMAND_1 = "new game";
	private static final String COMMAND_2 = "start game";
	private static final String COMMAND_3 = "start";

	private GameService gameService;

	private GameStore gameStore;

	@Inject
	public SlackHandlerNewGame(GameService gameService, GameStore gameStore) {
		this.gameService = gameService;
		this.gameStore = gameStore;
	}

	@Override
	public boolean accept(String message) {
		return message.toLowerCase().startsWith(COMMAND_1)
				|| message.toLowerCase().startsWith(COMMAND_2)
				|| message.toLowerCase().startsWith(COMMAND_3);
	}

	@Override
	public SlackResponse apply(SlackRequest slackRequest) {

		final String gameKey = gameService.buildKey(slackRequest.getKeyBase());
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
