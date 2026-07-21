package solitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

import solitaire.utils.Card;
import solitaire.utils.Suit;
import solitaire.utils.Value;
import solitaire.utils.Waste;

public class WasteTest {
    Deque<Card> initializeStock() {
        Deque<Card> stock = new java.util.ArrayDeque<Card>();

        for(Suit suit : Suit.values()) {
            for(Value value : Value.values()) {
                stock.push(new Card(suit, value));
            }
        }

        return stock;
    }

    @Test void drawThreeCards() {
        Deque<Card> stock = initializeStock();
        Waste waste = new Waste();

        waste.drawThreeFrom(stock);

        assertEquals(stock.size(), 52 - 3);
        assertEquals(waste.size(), 3);
    }

    // Draw last 2 cards
    @Test void unevenDraw() {
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.DIAMONDS, Value.ACE));
        stock.add(new Card(Suit.DIAMONDS, Value.TWO));
        // stock.add(new Card(Suit.DIAMONDS, Value.THREE));

        Waste waste = new Waste();
        
        waste.drawThreeFrom(stock);

        assertEquals(stock.size(), 0);
        assertEquals(waste.size(), 2);
    }

    @Test void refreshUponEmpty() {
        Deque<Card> stock = initializeStock();

        Waste waste = new Waste();
        while (!stock.isEmpty()) {
            waste.drawThreeFrom(stock);
        }

        waste.refresh(stock);

        assertEquals(stock.size(), 52);
    }

    @Test void unevenRefresh() {
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.CLUBS, Value.EIGHT));
        stock.add(new Card(Suit.CLUBS, Value.EIGHT));
        stock.add(new Card(Suit.CLUBS, Value.EIGHT));
        stock.add(new Card(Suit.CLUBS, Value.EIGHT));

        Waste waste = new Waste();

        waste.drawThreeFrom(stock);

        assertEquals(stock.size(), 1);
        assertEquals(waste.size(), 3);

        waste.popTop();

        assertEquals(waste.size(), 2);

        waste.drawThreeFrom(stock);

        assertEquals(waste.size(), 3);

        waste.refresh(stock);

        assertEquals(stock.size(), 3);
    }
}
