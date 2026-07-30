package solitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import solitaire.utils.Card;
import solitaire.utils.Manoeuvre;
import solitaire.utils.Suit;
import solitaire.utils.Rank;

public class CardTest {
    @Test void cardMovableComparison() {
        Card c1 = new Card(Suit.DIAMONDS, Rank.FIVE);
        Card c2 = new Card(Suit.SPADES, Rank.FOUR);
        Card c3 = new Card(Suit.HEARTS, Rank.THREE);

        assertTrue(c2.isCompatibleBelow(c1));
        assertFalse(c3.isCompatibleBelow(c1));

    }

    @Test void comparisonsWorks() {
        Card c1 = new Card(Suit.DIAMONDS, Rank.ACE);
        Card c2 = new Card(Suit.HEARTS, Rank.ACE);
        Card c3 = new Card(Suit.CLUBS, Rank.ACE);
        Card c4 = new Card(Suit.DIAMONDS, Rank.TWO);

        assertTrue(c1.sameColor(c2));
        assertTrue(c1.sameValue(c3));
        assertTrue(c1.sameSuit(c4));
    }

    @Test void cardStackEnd() {
        LinkedList<Card> cards = new LinkedList<Card>(List.of(
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.NINE),
            new Card(Suit.CLUBS, Rank.EIGHT),
            new Card(Suit.DIAMONDS, Rank.SEVEN)
        ));

        Manoeuvre cs = new Manoeuvre(cards, 2);

        assertEquals(cs.getRevealedBottom().value(), Rank.SEVEN);

    }

    @Test void cardStackPopping() {
        LinkedList<Card> cards = new LinkedList<Card>(List.of(
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.NINE),
            new Card(Suit.CLUBS, Rank.EIGHT),
            new Card(Suit.DIAMONDS, Rank.SEVEN)
        ));

        Manoeuvre cs = new Manoeuvre(cards, 2);
        Card c = cs.popCard();

        assertEquals(c.value(), Rank.SEVEN);
        assertEquals(c.suit(), Suit.DIAMONDS);
    }

    @Test void cardStackRevealsProperly() {
        LinkedList<Card> cards = new LinkedList<Card>(List.of(
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.NINE),
            new Card(Suit.CLUBS, Rank.EIGHT),
            new Card(Suit.DIAMONDS, Rank.SEVEN)
        ));

        Manoeuvre cs = new Manoeuvre(cards, 3);
        cs.popCard();

        assertEquals(cs.getRevealedBottom().value(), Rank.EIGHT);
        assertEquals(cs.revealedStart(), 2);
    }

    @Test void cardStackSplitsStacks() {
        LinkedList<Card> cards = new LinkedList<Card>(List.of(
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.NINE),
            new Card(Suit.CLUBS, Rank.EIGHT),
            new Card(Suit.DIAMONDS, Rank.SEVEN)
        ));

        Manoeuvre cs = new Manoeuvre(cards, 2);

        // Avoid splitting non-revealed cards
        var newCSTemp = cs.splitStack(0);
        assertTrue(newCSTemp.isEmpty());

        newCSTemp = cs.splitStack(2);
        assertTrue(newCSTemp.isPresent());

        Manoeuvre newCS = newCSTemp.get();
        assertTrue(newCS.getCard(0).suit() == Suit.CLUBS);
        assertTrue(newCS.getCard(1).suit() == Suit.DIAMONDS);
    }

    @Test void cardStackBasicMerge() {
        LinkedList<Card> cards = new LinkedList<Card>(List.of(
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.NINE),
            new Card(Suit.CLUBS, Rank.EIGHT),
            new Card(Suit.DIAMONDS, Rank.SEVEN)
        ));

        Manoeuvre cs = new Manoeuvre(cards, 2);

        var newCSTemp = cs.splitStack(2);
        Manoeuvre newCS = newCSTemp.get();

        cs.mergeStacks(newCS);
        assertSame(cs.getCard(3).suit(), Suit.DIAMONDS);
        assertSame(cs.getCard(2).suit(), Suit.CLUBS);
    }

    @Test void cardStackInvalidMerge() {
        LinkedList<Card> cards1 = new LinkedList<Card>(List.of(
            new Card(Suit.HEARTS, Rank.JACK),
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.NINE)
        ));

        LinkedList<Card> cards2 = new LinkedList<Card>(List.of(
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.SPADES, Rank.NINE)
        ));

        Manoeuvre cs1 = new Manoeuvre(cards1, 0);
        Manoeuvre cs2 = new Manoeuvre(cards2, 0);

        boolean worked = cs1.mergeStacks(cs2);
        assertFalse(worked);
    }
}
