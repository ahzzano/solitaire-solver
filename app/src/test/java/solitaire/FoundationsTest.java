package solitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import solitaire.utils.Card;
import solitaire.utils.Foundation;
import solitaire.utils.Suit;
import solitaire.utils.Rank;

public class FoundationsTest {
    @Test void foundationValidPushes() {
        Foundation foundation = new Foundation();
        Card c = new Card(Suit.CLUBS, Rank.ACE);
        foundation.push(c);

        Card invalidColor = new Card(Suit.DIAMONDS, Rank.TWO);
        Card invalidNumber = new Card(Suit.CLUBS, Rank.THREE);
        Card validTop = new Card(Suit.CLUBS, Rank.TWO);

        boolean pushed = foundation.push(invalidColor);
        assertFalse(pushed);
        assertEquals(foundation.getTop().suit(), Suit.CLUBS);

        foundation.push(invalidNumber);
        assertEquals(foundation.getTop().rank(), Rank.ACE);

        foundation.push(validTop);
        assertEquals(foundation.getTop().rank(), Rank.TWO);
    }

    @Test void foundationPushToEmpty() {
        Foundation foundation = new Foundation();
        Card c = new Card(Suit.CLUBS, Rank.ACE);

        assertEquals(foundation.empty(), true);

        foundation.push(c);
        assertEquals(foundation.empty(), false);

        Card top = foundation.getTop();

        assertEquals(top.suit(), Suit.CLUBS);
    }
    
}
