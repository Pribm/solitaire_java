package com.pvstudios.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Table extends MouseAdapter {
	public static final int NUM_TABLEAU_PILES = 7;

	private Deck deck;
	private Stock stock;

	private ArrayList<Tableau> tableauPiles;
	private ArrayList<Foundation> foundationPiles;

	Pile selectedTableau = null;
	private Pile auxiliaryPile;

	public Table() {
		super();
		newGame();
	}

	public void newGame() {
		deck = new Deck();
		this.auxiliaryPile = new Pile(0,0, StackDirection.VERTICAL, 20);
		this.initializeStock();
		this.initializeTableau();
		this.initializeFoundations();
	}

	public void initializeStock() {
		stock = new Stock(15,15);
		stock.addAll(deck);
	}


	public void initializeTableau() {
		tableauPiles = new ArrayList<Tableau>();

		int tableauCardMarginRight = 15;

		for (int i = 0; i < NUM_TABLEAU_PILES; i++) {

			int initialX = ((Card.width + tableauCardMarginRight) * i)
					+ (Game.frame.getWidth() - ((Card.width + tableauCardMarginRight) * Table.NUM_TABLEAU_PILES))
					- tableauCardMarginRight;

			int initialY =  (Card.height + 15) + 15;

			Tableau tableauPile = new Tableau(initialX, initialY, i);

			for (int j = 0; j <= i; j++) {
				Card card = this.stock.removeTopCard();
				tableauPile.addCard(card);

				//ALL FACE UP
				//tableauPile.getTopCard().setFaceDown(false);
			}

			tableauPile.getTopCard().setFaceDown(false);

			tableauPiles.add(tableauPile);
		}
	}

	public void initializeFoundations() {
		foundationPiles = new ArrayList<Foundation>();
		int i = 1;
		for (Suit suit : Suit.values()) {
			Foundation foundation = new Foundation(Game.WIDTH - ((72 + 15) * i) - 15, 15, suit);
			foundation.setPosition(Game.WIDTH - ((72 + 15) * i) - 15, 15);
			foundationPiles.add(foundation);

			i++;
		}
	}


	public void update() {
		for (Pile pile : tableauPiles) {
			pile.update();
		}

		this.stock.update();
		this.stock.getDrawedCards().update();

		this.auxiliaryPile.update();
	}

	public void render(Graphics2D g) {
		g.setColor(Color.decode("#006300"));
		g.fillRect(0, 0, Game.frame.getWidth(), Game.frame.getHeight());

		for (Tableau pile : tableauPiles) {
			pile.render(g);
		}

		for (Foundation pile : foundationPiles) {
			pile.render(g);
		}

		stock.render(g);

		if(!this.auxiliaryPile.isEmpty()) {
			auxiliaryPile.render(g);
		}
	}

	private void selectTablePile(MouseEvent e) {
		int fromCard = 0; 

		for (Tableau pile : tableauPiles) {
			Card selectedCard = pile.getCardAtPosition(e.getX(), e.getY());
			if (selectedCard != null && selectedCard.canMove()) {
				selectedTableau = pile;
				fromCard = pile.indexOf(selectedCard); 
				break;
			}
		}

		if (selectedTableau != null && fromCard >= 0) {
			List<Card> subPile = selectedTableau.subList(fromCard, selectedTableau.size());
			for (Card card : subPile) {
				this.auxiliaryPile.setX(e.getX());
				this.auxiliaryPile.setY(e.getY());
				this.auxiliaryPile.add(card);
			}
		}
	}

	public void selectStockCard(MouseEvent e) {
		if(!this.stock.getDrawedCards().isEmpty() && this.stock.getDrawedCards().lastElement().getBounds().contains(e.getPoint())) {
			selectedTableau = this.stock.getDrawedCards();
			this.auxiliaryPile.add(this.stock.getDrawedCards().lastElement());
			this.auxiliaryPile.setX(e.getX());
			this.auxiliaryPile.setY(e.getY());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		selectTablePile(e);
		selectStockCard(e);
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		if (!auxiliaryPile.isEmpty()) {
			auxiliaryPile.isDragged(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(stock.getBounds().contains(e.getPoint())) {
			stock.draw();
			return;
		}

		for (Pile pile : tableauPiles) {

			if (pile.lastElement().getBounds().contains(e.getPoint())) {
				if (pile.lastElement().isFaceDown()) {
					pile.lastElement().setFaceDown(false);
					return;
				}

				if(e.getClickCount() == 2) {		        	
					for (Foundation foundationPile : foundationPiles) {
						if(foundationPile.acceptCard(pile.lastElement())) {
							
							pile.lastElement().setX(foundationPile.getX());
							pile.lastElement().setY(foundationPile.getY());
							foundationPile.add(pile.lastElement());
							pile.remove(pile.lastElement());
							return;
						}
					};
				}
			}
		}
	}

	public void dropTableauOverAnother(MouseEvent e) {
		for (Tableau pile : tableauPiles) {
			if(pile.getBounds().intersects(auxiliaryPile.getBounds()) && pile.acceptPile(auxiliaryPile)) {
				auxiliaryPile.setX(pile.getX());
				auxiliaryPile.setY(pile.getY() + pile.getCardOffset());
				pile.addAll(auxiliaryPile);
				selectedTableau.removeAll(auxiliaryPile);
				break;
			}
		}

		selectedTableau = null;
		this.auxiliaryPile.clear();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dropTableauOverAnother(e);
	}
}
