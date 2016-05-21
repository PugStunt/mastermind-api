package com.pugstunt.mastermind.transformers;

import java.util.function.IntFunction;

import com.pugstunt.mastermind.core.domain.enums.Color;

public class ColorTransformer implements IntFunction<Color> {

	@Override
	public Color apply(int value) {

		return Color.find(value);
	}

}