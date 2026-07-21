package solitaire.utils;

public enum Suit {
    DIAMONDS("D"),
    SPADES("S"),
    CLUBS("C"),
    HEARTS("H");

    private final String displayString;

    Suit(String displayString) {
        this.displayString = displayString;
    }

    public String displayString() {
        return this.displayString;
    }
}
