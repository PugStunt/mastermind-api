package com.pugstunt.mastermind.core.domain.enums;

public enum Color {

	RED("R"),
	GREEN("G"),
	BLUE("B"),
	YELLOW("Y"),
	ORANGE("O"),
	PURPLE("P"),
	CYAN("C"),
	MAGENTA("M");
	
	private String value;
	
	private Color(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
