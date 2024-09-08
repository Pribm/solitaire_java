package com.pvstudios.main;

public enum Suit {
	SPADES("Spades"),
	HEARTS("Hearts"),
	CLUBS("Clubs"),
    DIAMONDS("Diamonds");
    

	private String name;

	Suit(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
