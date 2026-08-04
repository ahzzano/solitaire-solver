package solitaire.utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class Foundation {
    private Deque<Card> cards;

    public Foundation() {
        this.cards = new ArrayDeque<Card>();
    }

    public boolean pushable(Card card) {
        if(this.cards.isEmpty()) {
            return card.rank() == Rank.ACE;
        }

        Card top = cards.peek();

        return top.suit() == card.suit() 
            && card.rank().number() == top.rank().number() + 1;
    }

    public boolean push(Card card) {
        if(!this.pushable(card)) {
            return false;
        }

        this.cards.push(card);
        return true;
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
