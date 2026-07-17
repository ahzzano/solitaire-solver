package solitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import solitaire.utils.Card;
import solitaire.utils.CardStack;
import solitaire.utils.Foundation;
import solitaire.utils.Suit;
import solitaire.utils.Value;

public class MovesTest {
    @Test void lateralMoves() {
        CardStack[] tableu = new CardStack[4];

        tableu[0] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.QUEEN)
        )), 0);

        tableu[1] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.THREE),
            new Card(Suit.SPADES, Value.JACK)
        )), 1);

        tableu[2] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.KING)
        )), 0);

        tableu[3] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.KING)
        )), 0);

        App app = new App();
        app.lateralMoves(tableu);

        assertTrue(tableu[0].empty());
    }

    @Test void aceToFoundations() {
        LinkedList<Card> stack1 = new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.EIGHT),
            new Card(Suit.CLUBS, Value.NINE),
            new Card(Suit.CLUBS, Value.JACK),
            new Card(Suit.CLUBS, Value.ACE)
        ));

        LinkedList<Card> stack2 = new LinkedList<Card>(List.of(
            new Card(Suit.HEARTS, Value.ACE)
        ));

        CardStack[] tableu = new CardStack[3];
        Foundation[] foundations = new Foundation[4];

        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        tableu[0] = new CardStack(stack1, 3);
        tableu[1] = new CardStack(new LinkedList<>(), 0);
        tableu[2] = new CardStack(stack2, 0);
        
        App app = new App();
        app.aceToFoundations(tableu, foundations);

        assertEquals(tableu[0].getRevealedBottom().value(), Value.JACK);
        assertEquals(foundations[0].getTop().value(), Value.ACE);

        assertTrue(tableu[2].empty());
        assertEquals(foundations[1].getTop().suit(), Suit.HEARTS);
    }

    @Test void cardsToFoundation() {
        CardStack[] tableu = new CardStack[2];
        Foundation[] foundations = new Foundation[4];

        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        foundations[0].push(new Card(Suit.DIAMONDS, Value.ACE));
        foundations[1].push(new Card(Suit.HEARTS, Value.ACE));
        foundations[2].push(new Card(Suit.CLUBS, Value.ACE));
        foundations[3].push(new Card(Suit.SPADES, Value.ACE));

        tableu[0] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.TWO),
            new Card(Suit.HEARTS, Value.TWO),
            new Card(Suit.SPADES, Value.TWO),
            new Card(Suit.CLUBS, Value.TWO)
        )), 3);

        tableu[1] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.FOUR),
            new Card(Suit.HEARTS, Value.THREE)
        )), 1);

        App app = new App();
        app.cardsToFoundation(tableu, foundations);

        assertTrue(tableu[0].empty());
        assertEquals(tableu[1].getRevealedBottom().suit(), Suit.SPADES);
        assertEquals(tableu[1].getRevealedBottom().value(), Value.FOUR);

        assertEquals(foundations[1].getTop().value(), Value.THREE);
    }
    
    @Test void kingToEmpty() {
        CardStack[] tableu = new CardStack[4];

        tableu[0] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.HEARTS, Value.KING)
        )), 0);
        tableu[1] = new CardStack(new LinkedList<Card>(List.of()), 0);
        tableu[2] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.ACE),
            new Card(Suit.DIAMONDS, Value.KING),
            new Card(Suit.SPADES, Value.QUEEN)
        )), 1);
        tableu[3] = new CardStack(new LinkedList<Card>(List.of()), 0);

        App app = new App();
        boolean worked = app.kingToEmpty(tableu);

        assertTrue(worked);

        assertEquals(tableu[0].getRevealedTop().suit(), Suit.HEARTS);
        assertEquals(tableu[1].getRevealedTop().suit(), Suit.DIAMONDS);
        assertEquals(tableu[2].getRevealedBottom().value(), Value.ACE);

        assertTrue(tableu[3].empty());
    }

    @Test void exampleFirstMoveWithoutWaste() {
        CardStack[] tableu = new CardStack[7];
        Foundation[] foundations = new Foundation[4];

        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        tableu[0] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.FIVE)
        )), 0);

        tableu[1] = new CardStack(new LinkedList<Card>(List.of( 
            new Card(Suit.DIAMONDS, Value.TEN),
            new Card(Suit.SPADES, Value.SIX)
        )), 1);

        tableu[2] = new CardStack(new LinkedList<Card>(List.of( 
            new Card(Suit.HEARTS, Value.SIX),
            new Card(Suit.HEARTS, Value.QUEEN),
            new Card(Suit.DIAMONDS, Value.QUEEN)
        )), 2);

        tableu[3] = new CardStack(new LinkedList<Card>(List.of( 
            new Card(Suit.DIAMONDS, Value.FOUR),
            new Card(Suit.HEARTS, Value.FIVE),
            new Card(Suit.DIAMONDS, Value.EIGHT),
            new Card(Suit.SPADES, Value.ACE)
        )), 3);

        tableu[4] = new CardStack(new LinkedList<Card>(List.of( 
            new Card(Suit.DIAMONDS, Value.FIVE),
            new Card(Suit.SPADES, Value.FOUR),
            new Card(Suit.HEARTS, Value.ACE),
            new Card(Suit.DIAMONDS, Value.TWO),
            new Card(Suit.CLUBS, Value.TWO)
        )), 4);

        tableu[5] = new CardStack(new LinkedList<Card>(List.of( 
            new Card(Suit.DIAMONDS, Value.THREE),
            new Card(Suit.DIAMONDS, Value.KING),
            new Card(Suit.CLUBS, Value.THREE),
            new Card(Suit.CLUBS, Value.SEVEN),
            new Card(Suit.CLUBS, Value.TEN),
            new Card(Suit.SPADES, Value.JACK)
        )), 5);

        tableu[6] = new CardStack(new LinkedList<Card>(List.of( 
            new Card(Suit.DIAMONDS, Value.THREE),
            new Card(Suit.DIAMONDS, Value.KING),
            new Card(Suit.CLUBS, Value.THREE),
            new Card(Suit.CLUBS, Value.SEVEN),
            new Card(Suit.CLUBS, Value.TEN),
            new Card(Suit.SPADES, Value.JACK),
            new Card(Suit.DIAMONDS, Value.SEVEN)
        )), 6);

        App app = new App();

        app.aceToFoundations(tableu, foundations);
        app.cardsToFoundation(tableu, foundations);
        app.kingToEmpty(tableu);

        boolean keepLateralMoves = true;
        while (keepLateralMoves) {
            keepLateralMoves = app.lateralMoves(tableu);
        }

        // Expected Final State 
        assertEquals(foundations[0].empty(), false);
        
        assertFalse(tableu[0].empty());
        assertFalse(tableu[2].empty());
        assertFalse(tableu[3].empty());
        assertFalse(tableu[4].empty());
        assertFalse(tableu[5].empty());
        assertFalse(tableu[6].empty());

        assertTrue(tableu[1].empty());
    }
}
