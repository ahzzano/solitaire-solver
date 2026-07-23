package solitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import solitaire.utils.Card;
import solitaire.utils.Manoeuvre;
import solitaire.utils.Foundation;
import solitaire.utils.Suit;
import solitaire.utils.Value;
import solitaire.utils.Waste;

public class MovesTest {
    @Test void wasteCardToTableu() {
        Board board = new Board();
        Manoeuvre[] tableu = new Manoeuvre[4];

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(new Card(Suit.DIAMONDS, Value.KING))), 0);
        tableu[1] = new Manoeuvre(new LinkedList<Card>(
            List.of(
                new Card(Suit.DIAMONDS, Value.KING),
                new Card(Suit.HEARTS, Value.TEN))
            ),
            1);
        tableu[2] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.TWO),
            new Card(Suit.SPADES, Value.THREE),
            new Card(Suit.HEARTS, Value.FIVE)
        )),2);

        tableu[3] = new Manoeuvre(new LinkedList<Card>(List.of(
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

        board.withTableu(tableu);
        board.withStock(stock);
        board.withWaste(waste);

        board.wasteCardToTableu();
        board.wasteCardToTableu();
        board.wasteCardToTableu();

        assertEquals(board.tableu[0].getRevealedBottom().value(), Value.QUEEN);
        assertEquals(board.tableu[1].getRevealedBottom().value(), Value.NINE);
        assertEquals(board.tableu[2].getRevealedBottom().value(), Value.FOUR);

        assertEquals(board.tableu[3].getRevealedBottom().value(), Value.FOUR);
        assertEquals(board.tableu[3].getRevealedBottom().suit(), Suit.SPADES);
    }


    @Test void wasteCardToFoundation() {
        Board board = new Board();
        Foundation[] foundations = new Foundation[4];
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        foundations[0].push(new Card(Suit.CLUBS, Value.ACE));

        Waste waste = new Waste();
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.SPADES, Value.ACE));
        stock.add(new Card(Suit.CLUBS, Value.TWO));

        board.withFoundations(foundations);
        board.withWaste(waste);
        board.withStock(stock);

        board.wasteCardToFoundation();
        assertEquals(board.foundations[0].getTop().value(), Value.TWO);
        assertEquals(board.waste.size(), 1);
    }

    @Test void wasteAceToFoundation() {
        Board board = new Board();
        Foundation[] foundations = new Foundation[4];
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        Waste waste = new Waste();
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.CLUBS, Value.ACE));
        stock.add(new Card(Suit.HEARTS, Value.KING));

        board.withFoundations(foundations);
        board.withWaste(waste);
        board.withStock(stock);

        board.wasteAceToFoundation();

        assertTrue(board.foundations[0].empty());

        board.waste.popTop();

        board.wasteAceToFoundation();

        assertFalse(board.foundations[0].empty());
    }

    @Test void wasteKingToTableu() {
        Board board = new Board();
        Manoeuvre[] tableu = new Manoeuvre[4];
        tableu[0] = new Manoeuvre(new LinkedList<Card>(), 0);

        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.THREE),
            new Card(Suit.SPADES, Value.JACK)
        )), 1);

        tableu[2] = new Manoeuvre(new LinkedList<Card>(), 0);

        tableu[3] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.JACK)
        )), 0);

        Waste waste = new Waste();
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.CLUBS, Value.KING));
        stock.add(new Card(Suit.HEARTS, Value.KING));

        board.withTableu(tableu);
        board.withWaste(waste);
        board.withStock(stock);

        board.wasteKingToTableu();

        assertFalse(board.tableu[0].empty());
        assertEquals(board.tableu[0].getRevealedBottom().value(), Value.KING);
        assertEquals(board.tableu[0].getRevealedBottom().suit(), Suit.HEARTS);

        board.wasteKingToTableu();

        assertFalse(board.tableu[2].empty());
        assertEquals(board.tableu[2].getRevealedBottom().value(), Value.KING);
        assertEquals(board.tableu[2].getRevealedBottom().suit(), Suit.CLUBS);

        assertEquals(board.tableu[1].size(), 2);
        assertEquals(board.tableu[3].size(), 1);
    }

    @Test void lateralMoves() {
        Board board = new Board();
        Manoeuvre[] tableu = new Manoeuvre[4];

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.QUEEN)
        )), 0);

        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.THREE),
            new Card(Suit.SPADES, Value.JACK)
        )), 1);

        tableu[2] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.KING)
        )), 0);

        tableu[3] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.KING)
        )), 0);

        board.withTableu(tableu);
        board.lateralMoves();

        assertTrue(board.tableu[0].empty());
    }

    @Test void aceToFoundations() {
        Board board = new Board();
        LinkedList<Card> stack1 = new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.EIGHT),
            new Card(Suit.CLUBS, Value.NINE),
            new Card(Suit.CLUBS, Value.JACK),
            new Card(Suit.CLUBS, Value.ACE)
        ));

        LinkedList<Card> stack2 = new LinkedList<Card>(List.of(
            new Card(Suit.HEARTS, Value.ACE)
        ));

        Manoeuvre[] tableu = new Manoeuvre[3];
        Foundation[] foundations = new Foundation[4];

        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        tableu[0] = new Manoeuvre(stack1, 3);
        tableu[1] = new Manoeuvre(new LinkedList<>(), 0);
        tableu[2] = new Manoeuvre(stack2, 0);
        
        board.withTableu(tableu);
        board.withFoundations(foundations);
        board.aceToFoundations();

        assertEquals(board.tableu[0].getRevealedBottom().value(), Value.JACK);
        assertEquals(board.foundations[0].getTop().value(), Value.ACE);

        assertTrue(board.tableu[2].empty());
        assertEquals(board.foundations[1].getTop().suit(), Suit.HEARTS);
    }

    @Test void cardsToFoundation() {
        Board board = new Board();
        Manoeuvre[] tableu = new Manoeuvre[2];
        Foundation[] foundations = new Foundation[4];

        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        foundations[0].push(new Card(Suit.DIAMONDS, Value.ACE));
        foundations[1].push(new Card(Suit.HEARTS, Value.ACE));
        foundations[2].push(new Card(Suit.CLUBS, Value.ACE));
        foundations[3].push(new Card(Suit.SPADES, Value.ACE));

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.TWO),
            new Card(Suit.HEARTS, Value.TWO),
            new Card(Suit.SPADES, Value.TWO),
            new Card(Suit.CLUBS, Value.TWO)
        )), 3);

        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.FOUR),
            new Card(Suit.HEARTS, Value.THREE)
        )), 1);

        board.withTableu(tableu);
        board.withFoundations(foundations);
        board.cardsToFoundation();

        assertTrue(board.tableu[0].empty());
        assertEquals(board.tableu[1].getRevealedBottom().suit(), Suit.SPADES);
        assertEquals(board.tableu[1].getRevealedBottom().value(), Value.FOUR);

        assertEquals(board.foundations[1].getTop().value(), Value.THREE);
    }
    
    @Test void kingToEmpty() {
        Board board = new Board();
        Manoeuvre[] tableu = new Manoeuvre[4];

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.HEARTS, Value.KING)
        )), 0);
        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of()), 0);
        tableu[2] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.ACE),
            new Card(Suit.DIAMONDS, Value.KING),
            new Card(Suit.SPADES, Value.QUEEN)
        )), 1);
        tableu[3] = new Manoeuvre(new LinkedList<Card>(List.of()), 0);

        board.withTableu(tableu);
        boolean worked = board.kingToEmpty();

        assertTrue(worked);

        assertEquals(board.tableu[0].getRevealedTop().suit(), Suit.HEARTS);
        assertEquals(board.tableu[1].getRevealedTop().suit(), Suit.DIAMONDS);
        assertEquals(board.tableu[2].getRevealedBottom().value(), Value.ACE);

        assertTrue(board.tableu[3].empty());
    }

    @Test void kingToNonEmpty() {
        // Do nothing
        Board board = new Board();
        Manoeuvre[] tableu = new Manoeuvre[2];

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.HEARTS, Value.KING)
        )), 0);
        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.DIAMONDS, Value.ACE),
            new Card(Suit.DIAMONDS, Value.KING),
            new Card(Suit.SPADES, Value.QUEEN)
        )), 1);

        board.withTableu(tableu);
        boolean worked = board.kingToEmpty();

        assertFalse(worked);
    }

    @Test void exampleFirstMoveWithoutWaste() {
        Board board = new Board();
        Manoeuvre[] tableu = new Manoeuvre[7];
        Foundation[] foundations = new Foundation[4];

        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(
            new Card(Suit.SPADES, Value.FIVE)
        )), 0);

        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of( 
            new Card(Suit.DIAMONDS, Value.TEN),
            new Card(Suit.SPADES, Value.SIX)
        )), 1);

        tableu[2] = new Manoeuvre(new LinkedList<Card>(List.of( 
            new Card(Suit.HEARTS, Value.SIX),
            new Card(Suit.HEARTS, Value.QUEEN),
            new Card(Suit.DIAMONDS, Value.QUEEN)
        )), 2);

        tableu[3] = new Manoeuvre(new LinkedList<Card>(List.of( 
            new Card(Suit.DIAMONDS, Value.FOUR),
            new Card(Suit.HEARTS, Value.FIVE),
            new Card(Suit.DIAMONDS, Value.EIGHT),
            new Card(Suit.SPADES, Value.ACE)
        )), 3);

        tableu[4] = new Manoeuvre(new LinkedList<Card>(List.of( 
            new Card(Suit.DIAMONDS, Value.FIVE),
            new Card(Suit.SPADES, Value.FOUR),
            new Card(Suit.HEARTS, Value.ACE),
            new Card(Suit.DIAMONDS, Value.TWO),
            new Card(Suit.CLUBS, Value.TWO)
        )), 4);

        tableu[5] = new Manoeuvre(new LinkedList<Card>(List.of( 
            new Card(Suit.DIAMONDS, Value.THREE),
            new Card(Suit.DIAMONDS, Value.KING),
            new Card(Suit.CLUBS, Value.THREE),
            new Card(Suit.CLUBS, Value.SEVEN),
            new Card(Suit.CLUBS, Value.TEN),
            new Card(Suit.SPADES, Value.JACK)
        )), 5);

        tableu[6] = new Manoeuvre(new LinkedList<Card>(List.of( 
            new Card(Suit.DIAMONDS, Value.THREE),
            new Card(Suit.DIAMONDS, Value.KING),
            new Card(Suit.CLUBS, Value.THREE),
            new Card(Suit.CLUBS, Value.SEVEN),
            new Card(Suit.CLUBS, Value.TEN),
            new Card(Suit.SPADES, Value.JACK),
            new Card(Suit.DIAMONDS, Value.SEVEN)
        )), 6);

        board.withTableu(tableu);
        board.withFoundations(foundations);

        while(board.cardsToFoundation() || board.aceToFoundations() || board.kingToEmpty() || board.lateralMoves()) {}

        // Expected Final State 
        assertEquals(board.foundations[0].empty(), false);
        
        assertFalse(board.tableu[0].empty());
        assertFalse(board.tableu[2].empty());
        assertFalse(board.tableu[3].empty());
        assertFalse(board.tableu[4].empty());
        assertFalse(board.tableu[5].empty());
        assertFalse(board.tableu[6].empty());

        assertTrue(board.tableu[1].empty());
    }

    @Test void exampleGame() {
        Board board = new Board();

        assertTrue(board.stock.isEmpty() == false);
        
        Foundation[] foundations = new Foundation[4];
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }
        board.withFoundations(foundations);

        boolean moveMadeThisCycle = board.playOneCycle();

        assertFalse(board.foundations[0].empty());
        assertFalse(board.foundations[1].empty());
        assertFalse(board.foundations[2].empty());
        assertFalse(board.foundations[3].empty());

        for (Manoeuvre stack : board.tableu) {
            for (int i = stack.revealedStart() + 1; i < stack.size(); i++) {
                Card a = stack.getCard(i);
                Card b = stack.getCard(i - 1);

                assertFalse(a.sameColor(b));
            }
        }

        assertTrue(moveMadeThisCycle);
    }

}
