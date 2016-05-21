package com.pugstunt.mastermind.transformers;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.pugstunt.mastermind.core.domain.GameResponse;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;

public class GuessTransformer implements Function<GameEntry, GameResponse>{

	@Override
	public GameResponse apply(GameEntry game) {
		
		GameResponse response = new GameResponse();
		
		response.setUser(game.getPlayer());
		response.setGameKey(game.getGameKey());
		response.setNumGuesses(game.getGuesses());

		response.setPastResults(
				game.getPastResults().stream()
					.map(new PastResultTransformer())
					.collect(toList())
				);
		
		response.setStartTime(new Date(game.getStartTime()));
		response.setSolved(game.isSolved());
		response.setColors(Arrays.asList(Color.values())
					.stream()
					.map(color -> color.getValue())
					.collect(toList()));
		
		response.setCodeLength(game.getAnswer().size());
		
		if (!game.isActive()) {
			response.setResult("This game has expired (5 minutes window). Please start a new game.");
		} else if (game.isSolved()) {
			
			final long currentTime = new Date().getTime();
			final long startTime = game.getStartTime();
			final long timeTaken = currentTime - startTime;
			
			response.setTimeTaken(new Float(TimeUnit.MILLISECONDS.toSeconds(timeTaken)));
			response.setResult("You win!");
			response.setFurtherInstructions("Solve the challenge to see this!");
		}
		
		return response;
	}

}
