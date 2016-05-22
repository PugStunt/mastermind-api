package com.pugstunt.mastermind.transformers;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.pugstunt.mastermind.core.domain.GameResponse;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;

public class NewGameTransformer implements Function<GameEntry, GameResponse> {

	@Override
	public GameResponse apply(GameEntry game) {
		
		GameResponse response = new GameResponse();
		
		final List<Color> answer = game.getAnswer();
		response.setColors(
				Arrays.asList(Color.values())
					.stream()
					.map(color -> color.getValue())
					.collect(toList())
				);
		
		response.setCodeLength(answer.size());
		response.setGameKey(game.getGameKey());
		response.setNumGuesses(game.getGuesses());
		
		response.setPastResults(
				game.getPastResults().stream()
					.map(new PastResultTransformer())
					.collect(toList())
				);
		
		response.setSolved(game.isSolved());
		
		return response;
	}

}
