package com.pvstudios.main;

public enum Color {
	RED("Red"),
	BLACK("Black");
	
	private String color;
	
	private Color(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return this.color;
	}
}
