package solitaire;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import solitaire.utils.Card;
import solitaire.utils.Rank;
import solitaire.utils.Suit;

public class GameTest {
    @Test
    void winningGame() {
        ArrayList<Card> deck = new ArrayList<Card>(List.of(
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.NINE),
            new Card(Suit.HEARTS, Rank.QUEEN),
            new Card(Suit.CLUBS, Rank.ACE),
            new Card(Suit.DIAMONDS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.EIGHT),
            new Card(Suit.SPADES, Rank.QUEEN),

            new Card(Suit.HEARTS, Rank.TWO),
            new Card(Suit.DIAMONDS, Rank.JACK),
            new Card(Suit.CLUBS, Rank.EIGHT),
            new Card(Suit.DIAMONDS, Rank.KING),
            new Card(Suit.SPADES, Rank.TWO),
            new Card(Suit.HEARTS, Rank.FOUR),
            new Card(Suit.HEARTS, Rank.EIGHT),

            new Card(Suit.HEARTS, Rank.THREE),
            new Card(Suit.CLUBS, Rank.NINE),
            new Card(Suit.CLUBS, Rank.TWO),
            new Card(Suit.CLUBS, Rank.FOUR),
            new Card(Suit.SPADES, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.THREE),
            new Card(Suit.HEARTS, Rank.JACK),

            new Card(Suit.CLUBS, Rank.SIX),
            new Card(Suit.DIAMONDS, Rank.FIVE),
            new Card(Suit.CLUBS, Rank.KING),
            new Card(Suit.CLUBS, Rank.THREE),
            new Card(Suit.SPADES, Rank.TEN),
            new Card(Suit.SPADES, Rank.THREE),
            new Card(Suit.SPADES, Rank.KING),

            new Card(Suit.HEARTS, Rank.FIVE),
            new Card(Suit.DIAMONDS, Rank.ACE),
            new Card(Suit.DIAMONDS, Rank.TWO),
            new Card(Suit.DIAMONDS, Rank.SEVEN),
            new Card(Suit.HEARTS, Rank.KING),
            new Card(Suit.HEARTS, Rank.NINE),
            new Card(Suit.SPADES, Rank.JACK),

            new Card(Suit.HEARTS, Rank.SIX),
            new Card(Suit.DIAMONDS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.QUEEN),
            new Card(Suit.CLUBS, Rank.FIVE),
            new Card(Suit.SPADES, Rank.EIGHT),
            new Card(Suit.CLUBS, Rank.JACK),
            new Card(Suit.SPADES, Rank.ACE),

            new Card(Suit.SPADES, Rank.FIVE),
            new Card(Suit.CLUBS, Rank.QUEEN),
            new Card(Suit.DIAMONDS, Rank.SIX),
            new Card(Suit.HEARTS, Rank.SEVEN),
            new Card(Suit.SPADES, Rank.SEVEN),
            new Card(Suit.CLUBS, Rank.SEVEN),
            new Card(Suit.HEARTS, Rank.ACE),

            new Card(Suit.SPADES, Rank.SIX),
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.SPADES, Rank.NINE)
        ));

        Game game = new Game(deck);

        boolean win = game.play();
        assertTrue(win);
    }
    
}
