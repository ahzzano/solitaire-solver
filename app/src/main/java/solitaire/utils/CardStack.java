package solitaire.utils;

import java.util.LinkedList;
import java.util.Optional;

public class CardStack {
    LinkedList<Card> cards;

    // Topmost revealed card
    private int revealedStart;

    // REMOVE revealedEnd, no need for the thing as its always at the end of the list

    public CardStack(LinkedList<Card> cards, int revealedStart) {
        this.cards = cards;
        this.revealedStart = revealedStart;
    }

    public void pushCard(Card c) {
        if (this.cards.isEmpty()) {
            this.cards.push(c);
        }
    }

    public void appendCard(Card c) {
        this.cards.addLast(c);
    }

    public Optional<CardStack> splitStack(int index) {
        if (index < this.revealedStart) {
            return Optional.empty();
        }
        LinkedList<Card> splitCards = new LinkedList<>(this.cards.subList(index, this.cards.size()));
        this.cards.subList(index, this.cards.size()).clear();

        CardStack splitStack = new CardStack(splitCards, 0);

        if (index == this.revealedStart) {
            revealCard();
        }

        return Optional.of(splitStack);
    }

    public Card popCard() {
        if (this.revealedStart == this.cards.size() - 1) {
            this.revealCard();
        }
        return this.cards.pollLast();
    }

    public boolean mergeStacks(CardStack other) {
        if (this.cards.isEmpty()) {
            this.cards.addAll(other.cards);
            return true;
        }

        if (other.revealedStart() != 0) {
            return false;
        }

        if (other.getCard(other.revealedStart()).sameColor(this.getRevealedBottom())) {
            return false;
        }

        this.cards.addAll(other.cards);
        return true;
    }

    public void revealCard() {
        if(revealedStart > 0) {
            this.revealedStart -= 1;
        }
    }

    public int revealedStart() {
        return this.revealedStart;
    }

    public Card getCard(int index) {
        return this.cards.get(index);
    }

    public Card getRevealedTop() {
        return this.cards.get(this.revealedStart);
    }
    
    public Card getRevealedBottom() {
        if (this.cards.isEmpty()) {
            return null;
        }
        return this.cards.get(this.cards.size() - 1);
    }

    public int size() {
        return this.cards.size();
    }

    public boolean empty() {
        return this.cards.isEmpty();
    }
}
