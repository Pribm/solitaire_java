package com.pvstudios.main;

public enum StackDirection {
	VERTICAL("vertical"),
	HORIZONTAL("horizontal"),
	STACKED("stacked");
	
	private String direction;
	
	private StackDirection(String direction) {
		this.direction = direction;
	}
	
	public String getDirection() {
		return this.direction;
	}
}
