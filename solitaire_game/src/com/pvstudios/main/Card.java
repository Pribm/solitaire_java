package com.pvstudios.main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class Card {
	public static int width = 72, height = 96;
	private int x, y;

	
	private int initialX;
    private int initialY;
	
	private boolean faceDown;
	
	private Suit suit;
	private Ranking ranking;
	private Color color;
	
	protected BufferedImage sprite;
	
	private SpriteSheet spritesheet;
	public static BufferedImage faceDownImage;

	public Card(int x, int y, Suit suit, Ranking ranking, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.ranking = ranking;
		this.suit = suit;
		spritesheet = new SpriteSheet(0,0,71,95,6,13,0,0, "/assets/cards.png");
		faceDownImage = spritesheet.getSprite(4, 0);
		faceDown = true;
		
		if(this.suit.getName() == Suit.DIAMONDS.getName() || this.suit.getName() == Suit.HEARTS.getName()) {
			this.color = Color.RED;
		}else {
			this.color = Color.BLACK;
		}
	}
	
	
	public void isDragged(MouseEvent e) {
		if(!this.faceDown) {			
			int mouseX = e.getX();
			int mouseY = e.getY();
			setFinalPosition(mouseX - getWidth() / 2, mouseY - getHeight() / 2);
		}
    }
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		if(faceDown){			
			g.drawImage(faceDownImage,x, y, width, height, null);
			return;
		}
		g.drawImage(this.sprite, x, y, width, height, null);
	}

	public boolean isFaceDown() {
		return faceDown;
	}

	public void setFaceDown(boolean faceDown) {
		this.faceDown = faceDown;
	}

	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getInitialX() {
		return initialX;
	}

	public int getInitialY() {
		return initialY;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setInitialX(int initialX) {
		this.initialX = initialX;
	}

	public void setInitialY(int initialY) {
		this.initialY = initialY;
	}

	public void setInitialPosition(int x, int y) {
		this.initialX = x;
		this.initialY = y;
	}
	
	public void setFinalPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Ranking getRanking() {
		return ranking;
	}

	public Suit getSuit() {
		return suit;
	}

	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, Card.width, Card.height);
	}
	
	public void returnToOriginalPosition() {
		this.setFinalPosition(this.initialX, this.initialY);
	}

	public Color getColor() {
		return color;
	}
	
	public boolean canMove() {
		return !this.isFaceDown();
	}
}
