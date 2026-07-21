package solitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import solitaire.utils.Card;
import solitaire.utils.CardStack;
import solitaire.utils.Foundation;
import solitaire.utils.Suit;
import solitaire.utils.Value;
import solitaire.utils.Waste;

public class MovesTest {
    @Test void wasteCardToTableu() {
        CardStack[] tableu = new CardStack[4];

        tableu[0] = new CardStack(new LinkedList<Card>(List.of(new Card(Suit.DIAMONDS, Value.KING))), 0);
        tableu[1] = new CardStack(new LinkedList<Card>(
            List.of(
                new Card(Suit.DIAMONDS, Value.KING),
                new Card(Suit.HEARTS, Value.TEN))
            ),
            1);
        tableu[2] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.TWO),
            new Card(Suit.SPADES, Value.THREE),
            new Card(Suit.HEARTS, Value.FIVE)
        )),2);

        tableu[3] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.TWO),
            new Card(Suit.SPADES, Value.THREE),
            new Card(Suit.HEARTS, Value.FIVE),
            new Card(Suit.SPADES, Value.FOUR)
        )), 3);

        Deque<Card> stock = new ArrayDeque<>();
        stock.add(new Card(Suit.CLUBS, Value.FOUR));
        stock.add(new Card(Suit.SPADES, Value.NINE));
        stock.add(new Card(Suit.CLUBS, Value.QUEEN));
        Waste waste = new Waste();

        App app = new App();
        app.wasteCardToTableu(waste, stock, tableu);
        app.wasteCardToTableu(waste, stock, tableu);
        app.wasteCardToTableu(waste, stock, tableu);

        assertEquals(tableu[0].getRevealedBottom().value(), Value.QUEEN);
        assertEquals(tableu[1].getRevealedBottom().value(), Value.NINE);
        assertEquals(tableu[2].getRevealedBottom().value(), Value.FOUR);

        assertEquals(tableu[3].getRevealedBottom().value(), Value.FOUR);
        assertEquals(tableu[3].getRevealedBottom().suit(), Suit.SPADES);
    }

    @Test void wasteCardToFoundation() {
        Foundation[] foundations = new Foundation[4];
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        foundations[0].push(new Card(Suit.CLUBS, Value.ACE));

        Waste waste = new Waste();
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.SPADES, Value.ACE));
        stock.add(new Card(Suit.CLUBS, Value.TWO));

        App app = new App();

        app.wasteCardToFoundation(waste, stock, foundations);
        assertEquals(foundations[0].getTop().value(), Value.TWO);
        assertEquals(waste.size(), 1);

    }

    @Test void wasteAceToFoundation() {
        Foundation[] foundations = new Foundation[4];
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        Waste waste = new Waste();
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.CLUBS, Value.ACE));
        stock.add(new Card(Suit.HEARTS, Value.KING));

        App app = new App();

        app.wasteAceToFoundation(waste, stock, foundations);

        assertTrue(foundations[0].empty());

        waste.popTop();

        app.wasteAceToFoundation(waste, stock, foundations);

        assertFalse(foundations[0].empty());
    }

    @Test void wasteKingToTableu() {
        CardStack[] tableu = new CardStack[4];
        tableu[0] = new CardStack(new LinkedList<Card>(), 0);

        tableu[1] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.THREE),
            new Card(Suit.SPADES, Value.JACK)
        )), 1);

        tableu[2] = new CardStack(new LinkedList<Card>(), 0);

        tableu[3] = new CardStack(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.JACK)
        )), 0);

        Waste waste = new Waste();
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.CLUBS, Value.KING));
        stock.add(new Card(Suit.HEARTS, Value.KING));

        App app = new App();

        app.wasteKingToTableu(waste, stock, tableu);

        assertFalse(tableu[0].empty());
        assertEquals(tableu[0].getRevealedBottom().value(), Value.KING);
        assertEquals(tableu[0].getRevealedBottom().suit(), Suit.HEARTS);

        app.wasteKingToTableu(waste, stock, tableu);

        assertFalse(tableu[2].empty());
        assertEquals(tableu[2].getRevealedBottom().value(), Value.KING);
        assertEquals(tableu[2].getRevealedBottom().suit(), Suit.CLUBS);

        assertEquals(tableu[1].size(), 2);
        assertEquals(tableu[3].size(), 1);
    }

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

        while(app.buildTableu(tableu, foundations)) {}

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

    @Test void exampleGame() {
        ArrayList<Card> deck = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                deck.add(new Card(suit, value));
            }
        }

        App app = new App();

        CardStack[] tableu = app.initializeTableu(deck);

        Deque<Card> stock = app.initializeStock(deck);
        Waste waste = new Waste();

        assertTrue(deck.isEmpty());
        
        // Initialize the foundations
        Foundation[] foundations = new Foundation[4];
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        boolean moveMadeThisCycle = app.playOneCycle(tableu, foundations, waste, stock);

        assertFalse(foundations[0].empty());
        assertFalse(foundations[1].empty());
        assertFalse(foundations[2].empty());
        assertFalse(foundations[3].empty());

        for (CardStack stack : tableu) {
            for (int i = stack.revealedStart() + 1; i < stack.size(); i++) {
                Card a = stack.getCard(i);
                Card b = stack.getCard(i - 1);

                assertFalse(a.sameColor(b));
            }
        }

        assertTrue(moveMadeThisCycle);


    }
}
