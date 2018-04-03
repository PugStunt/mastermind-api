package com.pugstunt.mastermind.transformers;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.function.Function;

import com.pugstunt.mastermind.core.domain.WrongGuess;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;

public class WrongGuessTransformer  implements Function<GameEntry, WrongGuess> {


	@Override
	public WrongGuess apply(GameEntry game) {
		
		WrongGuess response = new WrongGuess();
		
		response.setGameKey(game.getGameKey());
		response.setNumGuesses(game.getGuesses());

		response.setPastResults(
				game.getPastResults().stream()
					.map(new PastResultTransformer())
					.collect(toList())
				);
		
		response.setSolved(game.isSolved());
		response.setColors(Arrays.stream(Color.values())
					.map(Color::getValue)
					.collect(toList()));
		
		response.setCodeLength(game.getAnswer().size());
		
		return response;
	}
}
