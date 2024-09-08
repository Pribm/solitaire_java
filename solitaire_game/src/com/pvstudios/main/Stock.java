package com.pvstudios.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Stock extends Pile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Pile drawedCards;
	
	public Stock(int x, int y) {
		super(x, y, StackDirection.STACKED, 0);
		this.drawedCards = new Pile(Card.width + x + 15, y, StackDirection.HORIZONTAL, 20);
	}
	
	
	public void draw() {
		
		if(!drawedCards.isEmpty()) {
			for (Card card : drawedCards) {
				card.setFaceDown(true);
			}
			this.addAll(0, drawedCards);
			drawedCards.clear();
		}
		
		int cardsToBeDrawed = 3;
		
		for(int i = 0 ; i < cardsToBeDrawed; i++) {
			if(!this.isEmpty() && this.size() > cardsToBeDrawed) {
				Card upperCard = this.pop();
				upperCard.setFaceDown(false);
				drawedCards.addCard(upperCard);
			}
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), Card.width, Card.height);
	}

	public void render(Graphics2D g) {
		this.getTopCard().setFinalPosition(this.getX(), this.getY());
		this.getTopCard().render(g);
		
		drawedCards.render(g);
		
		g.setColor(Color.decode("#FF0000"));
		g.draw(getBounds());
	}
	
	public Pile getDrawedCards() {
		return this.drawedCards;
	}
}
