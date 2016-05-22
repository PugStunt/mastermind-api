package com.pugstunt.mastermind.service;

import static java.util.stream.Collectors.joining;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.core.entity.PastResult;
import com.pugstunt.mastermind.exception.MastermindException;
import com.pugstunt.mastermind.store.GameStore;

public class GameService {
	
	private static final int CODE_LENGTH = 8;
	public static final long GAME_DURATION_TIME = System.getProperty("game.duration.milliseconds") != null ?
		Long.valueOf(System.getProperty("game.duration.milliseconds")) : TimeUnit.MINUTES.toMillis(5);
	
	private final GameStore gameStore;
	
	@Inject
	public GameService(final GameStore gameStore) {
		this.gameStore = gameStore;
	}

	public GameEntry newGame(String player) {

		return newGame(player, UUID.randomUUID().toString());
	}
	
	public GameEntry newGame(String player, String gameKey) {
		
		final GameEntry game = 
				GameEntry.builder(gameKey)
					.playerName(player)
					.guessesNumber(0)
					.solved(false)
					.answer(makeChallenge())
					.startTime(new Date().getTime())
					.active(true)
					.build();
			
			gameStore.save(game);
			
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
	
	public GameEntry checkGuess(String gameKey, List<Color> guess) {

		final Optional<GameEntry> game = gameStore.findByKey(gameKey);

		if (game.isPresent() && game.get().isActive()) {
			final GameEntry currentGame = check(game.get(), guess);
			gameStore.save(currentGame);
			return currentGame;
		}
		throw new MastermindException("No active game");
	}
	
	private GameEntry check(GameEntry game, List<Color> guess) {
		
		final List<Color> answer = game.getAnswer();
		
		if (isActive(game)) {
			
			int exact = 0;
			int near  = 0;
			
			for (int i = 0; i < answer.size(); i++) {
				if (guess.contains(answer.get(i))) {
					near++;
				}
				
				if (guess.get(i) == answer.get(i)) {
					exact++;
				}
			}
			
			PastResult pastResult = 
					PastResult.builder()
						.exact(exact)
						.guess(guess.stream().map(c -> String.valueOf(c.getValue())).collect(joining()))
						.near(near - exact)
						.build();
			
			List<PastResult> pastResults = Lists.newArrayList();
			pastResults.addAll(game.getPastResults());
			pastResults.add(pastResult);
			
			return GameEntry
					.builder(game.getGameKey())
					.playerName(game.getPlayer())
					.solved(exact == answer.size())
					.active(game.isActive())
					.answer(answer)
					.guessesNumber(game.getGuesses() + 1)
					.pastResults(pastResults)
					.startTime(game.getStartTime())
					.build();
		}
		
		return GameEntry
				.builder(game.getGameKey())
				.playerName(game.getPlayer())
				.solved(game.isSolved())
				.active(false)
				.answer(answer)
				.guessesNumber(game.getGuesses())
				.pastResults(game.getPastResults())
				.startTime(game.getStartTime())
				.build();
		
	}
	
	private boolean isActive(GameEntry game) {
		final long currentTime = new Date().getTime();
		return currentTime - game.getStartTime() < GAME_DURATION_TIME;
	}

	public String buildKey(String key) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			return new String(digest.digest(key.getBytes()), Charsets.UTF_8);
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}
}
