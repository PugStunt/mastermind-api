package com.pugstunt.mastermind.transformers;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.pugstunt.mastermind.core.domain.GameCreated;
import com.pugstunt.mastermind.core.domain.enums.Color;
import com.pugstunt.mastermind.core.entity.GameEntry;

public class NewGameTransformer implements Function<GameEntry, GameCreated> {

	@Override
	public GameCreated apply(GameEntry game) {
		
		GameCreated response = new GameCreated();
		
		final List<Color> answer = game.getAnswer();
		response.setColors(
				Arrays.stream(Color.values())
					.map(Color::getValue)
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
