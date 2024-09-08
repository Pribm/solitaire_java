package com.pvstudios.main;

import java.util.Collections;

public class Deck extends Pile {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Deck() {
		super(0, 0);
		initializeDeck();
		this.shuffle();
	}
	
	public void shuffle() {
		Collections.shuffle(this.getCards());
	}

	public Card draw() {
		if (this.isEmpty()) {
			return null;
		}
		return this.remove(this.size() - 1);
	}

	public boolean isEmpty() {
		return this.isEmpty();
	}

	private void initializeDeck() {
		for (int i = 0; i < Suit.values().length; i++) {
			for (int j = 0; j < Ranking.values().length; j++) {
				Ranking rank = Ranking.values()[j];
		        Suit suit = Suit.values()[i];
		        Card card = new Card(0, 0, suit, rank, Game.spritesheet.getSprite(i, j));
		        this.addCard(card);
			}
		}
	}
}
