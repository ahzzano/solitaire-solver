package solitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.Test;

import solitaire.utils.Card;
import solitaire.utils.Suit;
import solitaire.utils.Talon;
import solitaire.utils.Value;

public class TalonTest {
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
        Talon talon = new Talon();
        talon.withStock(stock);

        talon.drawThree();

        assertEquals(talon.stockSize(), 52 - 3);
        assertEquals(talon.wasteSize(), 3);
    }

    // Draw last 2 cards
    @Test void unevenDraw() {
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.DIAMONDS, Value.ACE));
        stock.add(new Card(Suit.DIAMONDS, Value.TWO));
        // stock.add(new Card(Suit.DIAMONDS, Value.THREE));

        Talon talon = new Talon();
        
        talon.drawThree();

        assertEquals(talon.stockSize(), 0);
        assertEquals(talon.wasteSize(), 2);
    }

    @Test void refreshUponEmpty() {
        Deque<Card> stock = initializeStock();

        Talon talon = new Talon();
        talon.withStock(stock);
        while (!stock.isEmpty()) {
            talon.drawThree();
        }

        talon.refresh();

        assertEquals(talon.stockSize(), 52);
    }

    @Test void unevenRefresh() {
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.CLUBS, Value.EIGHT));
        stock.add(new Card(Suit.CLUBS, Value.EIGHT));
        stock.add(new Card(Suit.CLUBS, Value.EIGHT));
        stock.add(new Card(Suit.CLUBS, Value.EIGHT));

        Talon talon = new Talon();
        talon.withStock(stock); 

        talon.drawThree();

        assertEquals(talon.stockSize(), 1);
        assertEquals(talon.wasteSize(), 3);

        talon.popTop();

        assertEquals(talon.wasteSize(), 2);

        talon.drawThree();

        assertEquals(talon.wasteSize(), 3);

        talon.refresh();

        assertEquals(talon.stockSize(), 3);
    }
}
