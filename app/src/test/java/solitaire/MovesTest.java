package solitaire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import solitaire.moves.TableuToFoundations;
import solitaire.moves.KingToEmpty;
import solitaire.moves.LateralMoves;
import solitaire.moves.Move;
import solitaire.moves.TalonAceToFoundations;
import solitaire.moves.TalonCardToFoundation;
import solitaire.moves.TalonCardToTableu;
import solitaire.moves.TalonKingToTableu;
import solitaire.utils.Card;
import solitaire.utils.Manoeuvre;
import solitaire.utils.Foundation;
import solitaire.utils.Suit;
import solitaire.utils.Talon;
import solitaire.utils.Rank;

public class MovesTest {
    @Test
    void wasteCardToTableu() {
        Manoeuvre[] tableu = new Manoeuvre[4];

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(new Card(Suit.DIAMONDS, Rank.KING))), 0);
        tableu[1] = new Manoeuvre(new LinkedList<Card>(
                List.of(
                        new Card(Suit.DIAMONDS, Rank.KING),
                        new Card(Suit.HEARTS, Rank.TEN))),
                1);
        tableu[2] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.SPADES, Rank.TWO),
                new Card(Suit.SPADES, Rank.THREE),
                new Card(Suit.HEARTS, Rank.FIVE))), 2);

        tableu[3] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.SPADES, Rank.TWO),
                new Card(Suit.SPADES, Rank.THREE),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.SPADES, Rank.FOUR))), 3);

        Deque<Card> stock = new ArrayDeque<>();
        stock.add(new Card(Suit.CLUBS, Rank.FOUR));
        stock.add(new Card(Suit.SPADES, Rank.NINE));
        stock.add(new Card(Suit.CLUBS, Rank.QUEEN));
        // Waste waste = new Waste();

        Talon talon = new Talon();
        talon.withStock(stock);

        Move move = new TalonCardToTableu(talon, tableu);

        move.play();
        move.play();
        move.play();

        assertEquals(tableu[0].getRevealedBottomCard().get().rank(), Rank.QUEEN);
        assertEquals(tableu[1].getRevealedBottomCard().get().rank(), Rank.NINE);
        assertEquals(tableu[2].getRevealedBottomCard().get().rank(), Rank.FOUR);

        assertEquals(tableu[3].getRevealedBottomCard().get().rank(), Rank.FOUR);
        assertEquals(tableu[3].getRevealedBottomCard().get().suit(), Suit.SPADES);
    }

    @Test
    void wasteCardToFoundation() {
        Foundation[] foundations = new Foundation[4];
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        foundations[0].push(new Card(Suit.CLUBS, Rank.ACE));

        Talon talon = new Talon();
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.SPADES, Rank.ACE));
        stock.add(new Card(Suit.CLUBS, Rank.TWO));

        talon.withStock(stock);

        Move move = new TalonCardToFoundation(talon, foundations);
        move.play();

        assertEquals(foundations[0].getTop().rank(), Rank.TWO);
        assertEquals(talon.size(), 1);
    }

    @Test
    void wasteAceToFoundation() {
        Game board = new Game();
        Foundation[] foundations = new Foundation[4];
        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        Deque<Card> waste = new ArrayDeque<>();
        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.CLUBS, Rank.ACE));
        stock.add(new Card(Suit.HEARTS, Rank.KING));
        Talon talon = new Talon();

        talon.withStock(stock);
        talon.withWaste(waste);

        Move move = new TalonAceToFoundations(talon, foundations);
        move.play();

        assertTrue(board.foundations[0].empty());

        talon.popTop();
        move.play();

        assertFalse(foundations[0].empty());
    }

    @Test
    void wasteKingToTableu() {
        Manoeuvre[] tableu = new Manoeuvre[4];
        tableu[0] = new Manoeuvre(new LinkedList<Card>(), 0);

        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.SPADES, Rank.JACK))), 1);

        tableu[2] = new Manoeuvre(new LinkedList<Card>(), 0);

        tableu[3] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.SPADES, Rank.JACK))), 0);

        Deque<Card> stock = new ArrayDeque<>();

        stock.add(new Card(Suit.CLUBS, Rank.KING));
        stock.add(new Card(Suit.HEARTS, Rank.KING));

        Talon talon = new Talon();
        talon.withStock(stock);

        Move move = new TalonKingToTableu(talon, tableu);
        move.play();

        assertFalse(tableu[0].empty());
        assertEquals(tableu[0].getRevealedBottomCard().get().rank(), Rank.KING);
        assertEquals(tableu[0].getRevealedBottomCard().get().suit(), Suit.HEARTS);

        move.play();

        assertFalse(tableu[2].empty());
        assertEquals(tableu[2].getRevealedBottomCard().get().rank(), Rank.KING);
        assertEquals(tableu[2].getRevealedBottomCard().get().suit(), Suit.CLUBS);

        assertEquals(tableu[1].size(), 2);
        assertEquals(tableu[3].size(), 1);
    }

    @Test
    void lateralMoves() {
        Manoeuvre[] tableu = new Manoeuvre[4];

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.QUEEN))), 0);

        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.SPADES, Rank.JACK))), 1);

        tableu[2] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.SPADES, Rank.KING))), 0);

        tableu[3] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.SPADES, Rank.KING))), 0);

        Move move = new LateralMoves(tableu);
        boolean made = move.play();

        assertTrue(made);
        assertTrue(tableu[0].empty());
    }

    @Test
    void aceToFoundations() {
        LinkedList<Card> stack1 = new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.EIGHT),
                new Card(Suit.CLUBS, Rank.NINE),
                new Card(Suit.CLUBS, Rank.JACK),
                new Card(Suit.CLUBS, Rank.ACE)));

        LinkedList<Card> stack2 = new LinkedList<Card>(List.of(
                new Card(Suit.HEARTS, Rank.ACE)));

        Manoeuvre[] tableu = new Manoeuvre[3];
        Foundation[] foundations = new Foundation[4];

        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        tableu[0] = new Manoeuvre(stack1, 3);
        tableu[1] = new Manoeuvre(new LinkedList<>(), 0);
        tableu[2] = new Manoeuvre(stack2, 0);

        Move atf = new TableuToFoundations(tableu, foundations);
        atf.play();
        atf.play();

        assertEquals(tableu[0].getRevealedBottomCard().get().rank(), Rank.JACK);
        assertEquals(foundations[0].getTop().rank(), Rank.ACE);

        assertTrue(tableu[2].empty());
        assertEquals(foundations[1].getTop().suit(), Suit.HEARTS);
    }

    @Test
    void kingToEmpty() {
        Manoeuvre[] tableu = new Manoeuvre[4];

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.HEARTS, Rank.KING))), 0);
        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of()), 0);
        tableu[2] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.KING),
                new Card(Suit.SPADES, Rank.QUEEN))), 1);
        tableu[3] = new Manoeuvre(new LinkedList<Card>(List.of()), 0);

        Move move = new KingToEmpty(tableu);

        boolean worked = move.play();

        assertTrue(worked);

        assertEquals(tableu[0].getRevealedTop().suit(), Suit.HEARTS);
        assertEquals(tableu[1].getRevealedTop().suit(), Suit.DIAMONDS);
        assertEquals(tableu[2].getRevealedBottomCard().get().rank(), Rank.ACE);

        assertTrue(tableu[3].empty());
    }

    @Test
    void kingToNonEmpty() {
        // Do nothing
        Manoeuvre[] tableu = new Manoeuvre[2];

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.HEARTS, Rank.KING))), 0);
        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.KING),
                new Card(Suit.SPADES, Rank.QUEEN))), 1);

        Move move = new KingToEmpty(tableu);

        boolean worked = move.play();

        assertFalse(worked);
    }

    @Test
    void exampleFirstMoveWithoutWaste() {
        Manoeuvre[] tableu = new Manoeuvre[7];
        Foundation[] foundations = new Foundation[4];

        for (int i = 0; i < 4; i++) {
            foundations[i] = new Foundation();
        }

        tableu[0] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.SPADES, Rank.FIVE))), 0);

        tableu[1] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.TEN),
                new Card(Suit.SPADES, Rank.SIX))), 1);

        tableu[2] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.HEARTS, Rank.SIX),
                new Card(Suit.HEARTS, Rank.QUEEN),
                new Card(Suit.DIAMONDS, Rank.QUEEN))), 2);

        tableu[3] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.FOUR),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.DIAMONDS, Rank.EIGHT),
                new Card(Suit.SPADES, Rank.ACE))), 3);

        tableu[4] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.FIVE),
                new Card(Suit.SPADES, Rank.FOUR),
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.TWO),
                new Card(Suit.CLUBS, Rank.TWO))), 4);

        tableu[5] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.DIAMONDS, Rank.KING),
                new Card(Suit.CLUBS, Rank.THREE),
                new Card(Suit.CLUBS, Rank.SEVEN),
                new Card(Suit.CLUBS, Rank.TEN),
                new Card(Suit.SPADES, Rank.JACK))), 5);

        tableu[6] = new Manoeuvre(new LinkedList<Card>(List.of(
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.DIAMONDS, Rank.KING),
                new Card(Suit.CLUBS, Rank.THREE),
                new Card(Suit.CLUBS, Rank.SEVEN),
                new Card(Suit.CLUBS, Rank.TEN),
                new Card(Suit.SPADES, Rank.JACK),
                new Card(Suit.DIAMONDS, Rank.SEVEN))), 6);

        Move atf = new TableuToFoundations(tableu, foundations);
        Move kte = new KingToEmpty(tableu);
        Move lat = new LateralMoves(tableu);

        while (atf.play() || kte.play() || lat.play()) {
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
