package com.pugstunt.mastermind.service;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.joining;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.core.entity.PastResult;
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
				.startTime(new Date().getTime())
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
		if (game.isPresent()) {
			return check(game.get(), guess);
		}
		
		throw new RuntimeException();
	}
	
	private GameEntry check(GameEntry game, List<Color> guess) {
		
		final List<Color> answer = game.getAnswer();
		
		if (isActive(game)) {
			
			int exact = 0;
			int near  = 0;
		
			final List<Color> nearAnswerList = Lists.newArrayList();
			final List<Color> nearGuessList = Lists.newArrayList();
			
			for (int index = 0; index < answer.size(); index++) {
				if (answer.get(index) == guess.get(index)) {
					exact++;
				} else {
					nearAnswerList.add(answer.get(index));
					nearGuessList.add(guess.get(index));
				}
			}
			
			for (Color nearGuess : nearGuessList) {
				if (nearAnswerList.remove(nearGuess)) {
					near++;
				}
			}
			
			PastResult pastResult = 
					PastResult.builder()
					.exact(exact)
					.guess(guess.stream().map(c -> String.valueOf(c.getValue())).collect(joining()))
					.near(near)
					.build();
			
			List<PastResult> pastResults = Lists.newArrayList();
			pastResults.addAll(game.getPastResults());
			pastResults.add(pastResult);
			
			return GameEntry
					.builder(game.getGameKey())
					.playerName(game.getPlayer())
					.solved(game.isSolved())
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
		return game.isActive() && TimeUnit.MILLISECONDS.toMinutes(currentTime - game.getStartTime()) < TTL;
	}

}
