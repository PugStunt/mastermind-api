package com.pugstunt.mastermind.core.domain.enums;

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

	public static Color find(int c) {

		for (Color color : values()) {
			if (color.getValue() == c) {
				return color;
			}
		}

		throw new IllegalArgumentException("Color not found for key " + c);
	}
}
