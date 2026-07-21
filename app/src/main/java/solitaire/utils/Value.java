package solitaire.utils;

public enum Value {
    ACE(1, "A"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "J"),
    QUEEN(12, "Q"),
    KING(13, "K");

    private final int value;
    private final String displayString;

    Value(int value, String displayString) {
        this.value = value;
        this.displayString = displayString;
    }

    public String displayString() {
        return this.displayString;
    }

    public int number() {
        return this.value;
    }
}
