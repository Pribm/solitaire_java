package com.pvstudios.main;

public enum Ranking {
    ACE("ACE"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("JACK"),
    QUEEN("QUEEN"),
    KING("KING");

    private String name;

    private Ranking(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public boolean isOneLowerThan(Ranking otherRanking) {
        return this.ordinal() == otherRanking.ordinal() - 1;
    }
}
