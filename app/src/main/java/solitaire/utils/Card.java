package solitaire.utils;

public class Card {
    final Suit suit;
    final Value value;

    public String toDisplayString() {
        return this.value.displayString() + "(" + this.suit.displayString() + ")";
    }

    public Value value() {
        return this.value;
    }

    public Suit suit() {
        return this.suit;
    }

    public Card(Suit s, Value v) {
        this.suit = s;
        this.value = v;
    }

    public boolean sameSuit(Card other) {
        return this.suit == other.suit;
    }

    public boolean sameValue(Card other) {
        return this.value == other.value;
    }

    public boolean isCompatibleBelow(Card other) {
        return !this.sameColor(other) && this.value().number() == other.value().number() - 1;
    }

    public boolean sameColor(Card other) {
        if (this.suit == Suit.CLUBS || this.suit == Suit.SPADES) {
            return other.suit == Suit.CLUBS || other.suit == Suit.SPADES;
        } 

        return other.suit == Suit.DIAMONDS || other.suit == Suit.HEARTS;
    }
}
