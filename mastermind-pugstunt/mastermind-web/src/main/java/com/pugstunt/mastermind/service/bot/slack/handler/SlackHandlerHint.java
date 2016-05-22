package com.pugstunt.mastermind.service.bot.slack.handler;

import java.util.Optional;
import java.util.Random;

import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackRequest;
import com.pugstunt.mastermind.core.domain.bot.slack.SlackResponse;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.service.GameService;
import com.pugstunt.mastermind.store.GameStore;

public class SlackHandlerHint implements SlackHandler {

	private GameService gameService;

	private GameStore gameStore;

	@Inject
	public SlackHandlerHint(GameService gameService, GameStore gameStore) {
		this.gameService = gameService;
		this.gameStore = gameStore;
	}

	@Override
	public boolean accept(String message) {

		return message.startsWith("hint") || message.startsWith("suggestion");
	}

	@Override
	public SlackResponse apply(SlackRequest request) {

		final Optional<GameEntry> game = gameStore.findByKey(gameService.buildKey(request.getKeyBase()));

		if (game.isPresent() && game.get().isActive()) {
			Random r = new Random();
			int randomPos = r.nextInt(8);
			Color color = game.get().getAnswer().get(randomPos);
			StringBuilder sb = new StringBuilder();
			return new SlackResponse(sb.append("Hint: The correct color for position ").append(randomPos + 1)
					.append(" is ").append(color.name()).toString());
		}
		return new SlackResponse("Sorry, no active game. Y U No start a new one? ;)");
	}

}
