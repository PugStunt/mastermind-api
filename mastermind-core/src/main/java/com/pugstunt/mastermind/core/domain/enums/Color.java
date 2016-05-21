package com.pugstunt.mastermind.core.domain.enums;

public enum Color {

	RED('R'),
	GREEN('G'),
	BLUE('B'),
	YELLOW('Y'),
	ORANGE('O'),
	PURPLE('P'),
	CYAN('C'),
	MAGENTA('M');
	
	private Character value;
	
	private Color(Character value) {
		this.value = value;
	}
	
	public Character getValue() {
		return value;
	}
}
