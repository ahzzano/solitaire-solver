package solitaire.utils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class Talon {
    private Deque<Card> stock;
    private Deque<Card> waste;

    public void withStock(Deque<Card> stock) {
        this.stock = stock;
    }

    public void withWaste(Deque<Card> waste) {
        this.waste = waste;
    }

    public Talon() {
        this.stock = new ArrayDeque<>();
        this.waste = new ArrayDeque<>();
    }

    public boolean isStockEmpty() {
        return this.stock.isEmpty();
    }

    public Optional<Card> getTop() {
        return Optional.ofNullable(this.waste.peek());
    }

    // @Deprecated
    // TODO: Make this use Optional<Card> 
    public Card popTop() {
        return this.waste.pop();
    }

    public void drawThree() {
        for (int i = 0; i < 3; i++) {
            if (stock.isEmpty()) {
                break;
            }
            this.waste.push(stock.pop());
        }
    }

    public void refresh() {
        while (!this.waste.isEmpty()) {
            stock.push(this.waste.pop());
        }
    }

    public int size() {
        return this.waste.size();
    }

    public int stockSize() {
        return this.stock.size();
    }

    public int wasteSize() {
        return this.waste.size();
    }
}
