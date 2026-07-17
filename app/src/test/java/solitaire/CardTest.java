package solitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import solitaire.utils.Card;
import solitaire.utils.CardStack;
import solitaire.utils.Suit;
import solitaire.utils.Value;

public class CardTest {
    @Test void cardMovableComparison() {
        Card c1 = new Card(Suit.DIAMONDS, Value.FIVE);
        Card c2 = new Card(Suit.SPADES, Value.FOUR);
        Card c3 = new Card(Suit.HEARTS, Value.THREE);

        assertTrue(c2.isCompatibleBelow(c1));
        assertFalse(c3.isCompatibleBelow(c1));

    }

    @Test void comparisonsWorks() {
        Card c1 = new Card(Suit.DIAMONDS, Value.ACE);
        Card c2 = new Card(Suit.HEARTS, Value.ACE);
        Card c3 = new Card(Suit.CLUBS, Value.ACE);
        Card c4 = new Card(Suit.DIAMONDS, Value.TWO);

        assertTrue(c1.sameColor(c2));
        assertTrue(c1.sameValue(c3));
        assertTrue(c1.sameSuit(c4));
    }

    @Test void cardStackEnd() {
        LinkedList<Card> cards = new LinkedList<Card>(List.of(
            new Card(Suit.CLUBS, Value.TEN),
            new Card(Suit.DIAMONDS, Value.NINE),
            new Card(Suit.CLUBS, Value.EIGHT),
            new Card(Suit.DIAMONDS, Value.SEVEN)
        ));

        CardStack cs = new CardStack(cards, 2);

        assertEquals(cs.getRevealedBottom().value(), Value.SEVEN);

    }

    @Test void cardStackPopping() {
        LinkedList<Card> cards = new LinkedList<Card>(List.of(
            new Card(Suit.CLUBS, Value.TEN),
            new Card(Suit.DIAMONDS, Value.NINE),
            new Card(Suit.CLUBS, Value.EIGHT),
            new Card(Suit.DIAMONDS, Value.SEVEN)
        ));

        CardStack cs = new CardStack(cards, 2);
        Card c = cs.popCard();

        assertEquals(c.value(), Value.SEVEN);
        assertEquals(c.suit(), Suit.DIAMONDS);
    }

    @Test void cardStackRevealsProperly() {
        LinkedList<Card> cards = new LinkedList<Card>(List.of(
            new Card(Suit.CLUBS, Value.TEN),
            new Card(Suit.DIAMONDS, Value.NINE),
            new Card(Suit.CLUBS, Value.EIGHT),
            new Card(Suit.DIAMONDS, Value.SEVEN)
        ));

        CardStack cs = new CardStack(cards, 3);
        cs.popCard();

        assertEquals(cs.getRevealedBottom().value(), Value.EIGHT);
        assertEquals(cs.revealedStart(), 2);
    }

    @Test void cardStackSplitsStacks() {
        LinkedList<Card> cards = new LinkedList<Card>(List.of(
            new Card(Suit.CLUBS, Value.TEN),
            new Card(Suit.DIAMONDS, Value.NINE),
            new Card(Suit.CLUBS, Value.EIGHT),
            new Card(Suit.DIAMONDS, Value.SEVEN)
        ));

        CardStack cs = new CardStack(cards, 2);

        // Avoid splitting non-revealed cards
        var newCSTemp = cs.splitStack(0);
        assertTrue(newCSTemp.isEmpty());

        newCSTemp = cs.splitStack(2);
        assertTrue(newCSTemp.isPresent());

        CardStack newCS = newCSTemp.get();
        assertTrue(newCS.getCard(0).suit() == Suit.CLUBS);
        assertTrue(newCS.getCard(1).suit() == Suit.DIAMONDS);
    }

    @Test void cardStackBasicMerge() {
        LinkedList<Card> cards = new LinkedList<Card>(List.of(
            new Card(Suit.CLUBS, Value.TEN),
            new Card(Suit.DIAMONDS, Value.NINE),
            new Card(Suit.CLUBS, Value.EIGHT),
            new Card(Suit.DIAMONDS, Value.SEVEN)
        ));

        CardStack cs = new CardStack(cards, 2);

        var newCSTemp = cs.splitStack(2);
        CardStack newCS = newCSTemp.get();

        cs.mergeStacks(newCS);
        assertSame(cs.getCard(3).suit(), Suit.DIAMONDS);
        assertSame(cs.getCard(2).suit(), Suit.CLUBS);
    }

    @Test void cardStackInvalidMerge() {
        LinkedList<Card> cards1 = new LinkedList<Card>(List.of(
            new Card(Suit.HEARTS, Value.JACK),
            new Card(Suit.CLUBS, Value.TEN),
            new Card(Suit.DIAMONDS, Value.NINE)
        ));

        LinkedList<Card> cards2 = new LinkedList<Card>(List.of(
            new Card(Suit.HEARTS, Value.TEN),
            new Card(Suit.SPADES, Value.NINE)
        ));

        CardStack cs1 = new CardStack(cards1, 0);
        CardStack cs2 = new CardStack(cards2, 0);

        boolean worked = cs1.mergeStacks(cs2);
        assertFalse(worked);
    }
}
