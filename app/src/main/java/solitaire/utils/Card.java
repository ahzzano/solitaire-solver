package solitaire.utils;

public class Card {
    final Suit suit;
    final Rank rank;

    public String toDisplayString() {
        return this.rank.displayString() + "(" + this.suit.displayString() + ")";
    }

    public Rank rank() {
        return this.rank;
    }

    public Suit suit() {
        return this.suit;
    }

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public boolean sameSuit(Card other) {
        return this.suit == other.suit;
    }

    public boolean sameValue(Card other) {
        return this.rank == other.rank;
    }

    public boolean isCompatibleBelow(Card other) {
        return !this.sameColor(other) && this.rank().number() == other.rank().number() - 1;
    }

    public boolean sameColor(Card other) {
        if (this.suit == Suit.CLUBS || this.suit == Suit.SPADES) {
            return other.suit == Suit.CLUBS || other.suit == Suit.SPADES;
        } 

        return other.suit == Suit.DIAMONDS || other.suit == Suit.HEARTS;
    }
}
