package solitaire.utils;

import java.util.ArrayDeque;
import java.util.Deque;

public class Waste {
    Deque<Card> cards;

    public Waste() {
        this.cards = new ArrayDeque<Card>();
    }

    public int size() {
        return this.cards.size();
    }

    public Card getTop() {
        return this.cards.peek();
    }

    public Card popTop() {
        return this.cards.pop();
    }

    public void drawThreeFrom(Deque<Card> stock) {
        for (int i = 0; i < 3; i++) {
            if (stock.isEmpty()) {
                break;
            }
            this.cards.push(stock.pop());
        }
    }

    public void refresh(Deque<Card> stock) {
        while (!this.cards.isEmpty()) {
            stock.push(this.cards.pop());
        }
    }

    public void display() {
        for (Card card : this.cards) {
            System.out.println(card.toDisplayString());
        }
    }
}
