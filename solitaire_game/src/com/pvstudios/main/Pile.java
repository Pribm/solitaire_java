package com.pvstudios.main;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Stack;

public class Pile extends Stack<Card> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int cardOffset = 0;
	StackDirection stackDirection = StackDirection.STACKED;

	private int x, y;

	public Pile(int x, int y, StackDirection direction, int cardOffSet) {
		super();
		this.x = x;
		this.y = y;
		this.stackDirection = direction;
		this.cardOffset = cardOffSet;
	}

	public Pile(int x, int y) {
		super();
		this.x = x;
		this.y = y;
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

	public int getCardOffset() {
		return cardOffset;
	}

	public void setCardOffset(int cardOffset) {
		this.cardOffset = cardOffset;
	}

	public Pile(StackDirection direction, int cardOffSet) {
		this.stackDirection = direction;
		this.cardOffset = cardOffSet;
	}

	public Card getTopCard() {
		return this.peek();
	}

	public Card removeTopCard() {
		Card card = this.pop();
		return card;
	}

	public void addCardToBottom(Card card) {
		this.insertElementAt(card, 0);
	}

	public Stack<Card> getCards() {
		return this;
	}

	public int getTotalCards() {
		return this.size();
	}

	public boolean acceptPile(Pile addedPile) {
		if(addedPile.isEmpty()) return false;
		
		Card addedPileFirstCard = addedPile.firstElement();
		
		if(this.isEmpty() && addedPileFirstCard.getRanking() == Ranking.KING) {
			return true;
		}
		
		Card thisPileLastCard = this.lastElement();
		if(thisPileLastCard.isFaceDown()) return false;
		
		return (thisPileLastCard.getColor() != addedPileFirstCard.getColor()
				&& !addedPile.isEmpty()
				&& thisPileLastCard.getRanking().ordinal()-1 == addedPileFirstCard.getRanking().ordinal()
				);
	}

	public Card drawCard(Card card) {
		this.remove(card);
		return card;
	}

	public Rectangle getBounds() {

		Rectangle rect = new Rectangle();
		
		if(this.isEmpty()) {
			return new Rectangle(this.x, this.y, Card.width, Card.height);
		}
		
		switch (stackDirection) {
		case HORIZONTAL: {
			rect = new Rectangle(this.x, this.y, (Card.width - this.cardOffset) + (this.size() * this.cardOffset),
					Card.height);
			break;
		}
		case VERTICAL: {
			rect = new Rectangle(this.x, this.y, Card.width,
					(Card.height - this.cardOffset) + (this.size() * this.cardOffset));
			break;
		}
		case STACKED:
			rect = new Rectangle(this.x, this.y, Card.width, Card.height);
			break;
		}

		return rect;
	}
	
	public void addCard(Card card) {
		this.add(card);
	}

	public Card getCardAtPosition(int mouseX, int mouseY) {
		// Iterate over cards from top to bottom (reverse order for the stack)
		for (int i = this.size() - 1; i >= 0; i--) {
			Card card = this.get(i);

			Rectangle exposedBounds = null;

			// Adjust bounds based on the current stack direction
			switch (this.stackDirection) {
			case VERTICAL:
				// Expose only the visible portion of vertically stacked cards
				int exposedHeight = (i == this.size() - 1) ? Card.height : this.cardOffset; // Full height for top card,
				// else only exposed portion
				exposedBounds = new Rectangle(card.getX(), card.getY(), Card.width, exposedHeight);
				break;
			case HORIZONTAL:
				// Expose only the visible portion of horizontally stacked cards
				int exposedWidth = (i == this.size() - 1) ? Card.width : this.cardOffset; // Full width for top card,
				// else only exposed portion
				exposedBounds = new Rectangle(card.getX(), card.getY(), exposedWidth, Card.height);
				break;
			case STACKED:
				// If cards are fully stacked, only the top card is visible and clickable
				exposedBounds = new Rectangle(card.getX(), card.getY(), Card.width, Card.height);
				break;
			}

			if (exposedBounds != null && exposedBounds.contains(mouseX, mouseY)) {
				return card; // Return the topmost card under the mouse
			}
		}
		return null; // No card found at the given position
	}

	public void isDragged(MouseEvent e) {
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		this.x = mouseX - (Card.width / 2);
		this.y = mouseY - (Card.height / 2);
	}
	
	public void update() {
		for (int i = 0; i < this.size(); i++) {
			Card card = this.get(i);

			switch (this.stackDirection) {
			case VERTICAL:
				card.setX(this.x);
				card.setY(this.y + i * this.cardOffset); 
				break;

			case HORIZONTAL:
				card.setX(this.x + i * this.cardOffset);  
				card.setY(this.y); 
				break;

			case STACKED:
				card.setX(this.x);  
				card.setY(this.y); 
				break;
			}
		}
	}


	public void render(Graphics2D g) {
		if(this.isEmpty()) {
			g.drawImage(Game.spritesheet.getSprite(4, 12), this.getX(), this.getY(), null);
			return;
		}
		for (int i = 0; i < this.size(); i++) {
			Card card = this.get(i);
			card.render(g);
		}
		
		
		if(Game.DEBUG && !this.isEmpty()) {
			for (int i = this.size() - 1; i >= 0; i--) {
				Card card = this.get(i);

				Rectangle exposedBounds = null;

				switch (this.stackDirection) {
				case VERTICAL:
					int exposedHeight = (i == this.size() - 1) ? Card.height : this.cardOffset; 
					exposedBounds = new Rectangle(card.getX(), card.getY(), Card.width, exposedHeight);
					break;
				case HORIZONTAL:
					int exposedWidth = (i == this.size() - 1) ? Card.width : this.cardOffset; 
					exposedBounds = new Rectangle(card.getX(), card.getY(), exposedWidth, Card.height);
					break;
				case STACKED:
					exposedBounds = new Rectangle(card.getX(), card.getY(), Card.width, Card.height);
					break;
				}

				g.setColor(new java.awt.Color(120,0,0,180));
				g.fill(exposedBounds);
			}
		}
	}
}
