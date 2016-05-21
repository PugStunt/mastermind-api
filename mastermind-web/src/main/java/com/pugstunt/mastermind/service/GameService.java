package com.pugstunt.mastermind.service;

import static java.util.UUID.randomUUID;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.store.GameStore;

public class GameService {
	
	private static final int CODE_LENGTH = 8;
	private static final long TTL = TimeUnit.MINUTES.toSeconds(5);
	
	private final GameStore gameStore;
	
	@Inject
	public GameService(final GameStore gameStore) {
		this.gameStore = gameStore;
	}

	public GameEntry newGame(String user) {
		
		final GameEntry game = 
			GameEntry.builder(randomUUID().toString())
				.playerName(user)
				.guessesNumber(0)
				.solved(false)
				.answer(makeChallenge())
				.build();
		
		gameStore.save(game, TTL);
		
		return game;
	}

	private List<Color> makeChallenge() {

		final Color[] colors = Color.values();
		final List<Color> challenge = Lists.newArrayListWithExpectedSize(CODE_LENGTH);
		while (challenge.size() < CODE_LENGTH) {
			int color = (int) (Math.random() * colors.length);
			challenge.add(colors[color]);
		}
		return challenge;
	}
	
	
}
