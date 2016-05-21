package com.pugstunt.mastermind.core.domain.enums;

public enum Color {

	RED('R', new int[] { 231, 76, 60 }),
	GREEN('G', new int[] { 46, 204, 113 }),
	BLUE('B', new int[] { 52, 152, 219 }),
	YELLOW('Y', new int[] { 241, 196, 15 }),
	ORANGE('O', new int[] { 230, 126, 34 }),
	PURPLE('P', new int[] { 155, 89, 182 }),
	CYAN('C', new int[] { 26, 188, 156 }),
	MAGENTA('M', new int[] { 222, 107, 174 });

	private char value;
	private int[] rgb;

	private Color(char value, int[] rgb) {
		this.value = value;
		this.rgb = rgb;
	}

	public char getValue() {
		return value;
	}

	public int[] getRgb() {
		return rgb;
	}

	public static Color find(int c) {

		for (Color color : values()) {
			if (color.getValue() == c) {
				return color;
			}
		}

		throw new IllegalArgumentException("Color not found for key " + c);
	}
}
