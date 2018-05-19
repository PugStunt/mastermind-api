package com.pugstunt.mastermind.transformers;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.pugstunt.mastermind.core.domain.SolvedGame;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;

public class SolvedGameTransformer implements Function<GameEntry, SolvedGame> {

	@Override
	public SolvedGame apply(GameEntry game) {
		
		SolvedGame response = new SolvedGame();
		
		response.setUser(game.getPlayerName());
		response.setGameKey(game.getGameKey());
		response.setNumGuesses(game.getGuessNumber());

		response.setPastResults(
				game.getPastResults().stream()
					.map(new PastResultTransformer())
					.collect(toList())
				);
		
		response.setStartTime(new Date(game.getStartTime()));
		response.setSolved(game.isSolved());
		response.setColors(Arrays.stream(Color.values())
					.map(Color::getValue)
					.collect(toList()));
		
		response.setCodeLength(game.getAnswer().size());
		
		final long currentTime = new Date().getTime();
		final long startTime = game.getStartTime();
		final long timeTaken = currentTime - startTime;
		
		response.setTimeTaken((float) TimeUnit.MILLISECONDS.toSeconds(timeTaken));
		response.setResult("You win!");
		response.setFurtherInstructions("Solve the challenge to see this!");
		
		return response;
	}

}
