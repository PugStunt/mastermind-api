package com.pugstunt.mastermind.api;

import java.util.List;

import com.pugstunt.mastermind.core.domain.enums.Color;

public class TestUtils {
	
	public static String convertListColorsIntoGuess(List<Color> colors) {
		
		return colors.stream()
			.map(Color::getValue)
			.map(String::valueOf)
			.reduce((a, b) -> a.concat(b))
			.get();
	}

}
