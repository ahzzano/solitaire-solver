package solitaire.utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class Foundation {
    private Deque<Card> cards;
    private Card base;

    public Foundation() {
        this.cards = new ArrayDeque<Card>();
    }

    public boolean pushable(Card c) {
        if(this.cards.isEmpty() && c.value() == Value.ACE) {
            return true;
        }

        if (this.base == null) {
            return false;
        }

        boolean validMove = this.base.suit() == c.suit() &&  c.value().number() - 1 == this.cards.getFirst().value().number();
        return validMove;
    }

    public boolean push(Card c) {
        if(this.base == null && c.value() == Value.ACE) {
            this.base = c;
            cards.push(c);
            return true;
        }

        if(this.base == null) {
            return false;
        }

        if (this.base.suit() == c.suit()) {
            if (c.value().number() - 1 == this.cards.getFirst().value().number()) {
                cards.push(c);
                return true;
            }
            return true;
        }


        return false;
    }

    public Optional<Card> pop() {
        if (!this.cards.isEmpty()) {
            return Optional.of(this.cards.pop());
        }

        return Optional.empty();
    }

    public boolean empty() {
        return this.cards.isEmpty();
    }

    public int size() {
        return this.cards.size();
    }

    public Card getTop() {
        return this.cards.peek();
    }
    
    public void display() {
        for (Card card : cards) {
            System.out.println(card.toDisplayString());
        }
    }
}
