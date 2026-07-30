package solitaire.utils;

import java.util.ArrayDeque;
import java.util.Deque;

@Deprecated
public class Waste {
    Deque<Card> cards;

    @Deprecated
    public Waste() {
        this.cards = new ArrayDeque<Card>();
    }

    @Deprecated
    public int size() {
        return this.cards.size();
    }

    @Deprecated
    public Card getTop() {
        return this.cards.peek();
    }

    @Deprecated
    public Card popTop() {
        return this.cards.pop();
    }

    @Deprecated
    public void drawThreeFrom(Deque<Card> stock) {
        for (int i = 0; i < 3; i++) {
            if (stock.isEmpty()) {
                break;
            }
            this.cards.push(stock.pop());
        }
    }

    @Deprecated
    public void refresh(Deque<Card> stock) {
        while (!this.cards.isEmpty()) {
            stock.push(this.cards.pop());
        }
    }

    @Deprecated
    public void display() {
        for (Card card : this.cards) {
            System.out.println(card.toDisplayString());
        }
    }
}
