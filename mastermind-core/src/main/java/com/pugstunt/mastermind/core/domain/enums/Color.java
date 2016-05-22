package com.pugstunt.mastermind.core.domain.enums;

import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public enum Color {

	RED('R', new java.awt.Color( 231, 76, 60)),
	GREEN('G', new java.awt.Color( 46, 204, 113)),
	BLUE('B', new java.awt.Color( 52, 152, 219)),
	YELLOW('Y', new java.awt.Color( 241, 196, 15)),
	ORANGE('O', new java.awt.Color( 230, 126, 34)),
	PURPLE('P', new java.awt.Color( 155, 89, 182)),
	CYAN('C', new java.awt.Color( 26, 188, 156)),
	MAGENTA('M', new java.awt.Color( 222, 107, 174));

	private char value;

	private java.awt.Color rgb;

	private Color(char value, java.awt.Color rgb) {
		this.value = value;
		this.rgb = rgb;
	}

	public char getValue() {
		return value;
	}

	public java.awt.Color getRgb() {
		return rgb;
	}

	public static List<Color> from(String guess) {

		if (Objects.isNull(guess)) {
			throw new IllegalArgumentException("Invalid guess");
		}

		if (values().length != guess.length()) {
			throw new IllegalArgumentException(
					"Invalid size for guess. Expected: " + values().length + "; Actual: " + guess.length());
		}

		List<Color> colors = guess.toUpperCase().chars().mapToObj(new Transformer()).collect(Collectors.toList());
		return colors;
	}

	private static class Transformer implements IntFunction<Color> {

		@Override
		public Color apply(int value) {

			return Color.find(value);
		}
	}

	private static Color find(int c) {

		for (Color color : values()) {
			if (color.getValue() == c) {
				return color;
			}
		}

		throw new IllegalArgumentException("Unknown color: '" + (char) c + "'");
	}

}
