package com.pvstudios.main;


public class Tableau extends Pile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int index;

	public Tableau(int x, int y, int index) {
		super(x, y, StackDirection.VERTICAL, 20);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
