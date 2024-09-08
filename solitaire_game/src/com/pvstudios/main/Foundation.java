package com.pvstudios.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Foundation extends Pile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Suit suit;
	private int x, y;

	public Foundation(int x, int y,Suit suit) {
		super(x, y);
		this.x = x;
		this.y = y;
		this.suit = suit;
	}

	public boolean acceptCard(Card card) {
		if(card.getSuit() == this.suit) {
			if(this.isEmpty() && card.getRanking().ordinal() == 0) return true;			
			if(this.getCards().size() > 0 && card.getRanking().ordinal()-1 == this.lastElement().getRanking().ordinal()) return true;
		}
	    return false;
	}

	public Suit getSuit() {
		return suit;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void addCard(Card card) {
        this.add(card);
        card.setX(this.x);
        card.setY(this.y); 
    }
	
	public Pile getPile() {
		return this;
	}

	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, Card.width, Card.height);
	}

	@Override
	public void render(Graphics2D g) {
		if(this.getCards().size() == 0) {			
			g.drawImage(Game.spritesheet.getSprite(4, 12), this.getX(), this.getY(), null);
		}else {
			this.getTopCard().render(g);
		}
	}
}
