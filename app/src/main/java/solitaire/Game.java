package solitaire;

import java.util.*;

import org.jspecify.annotations.NonNull;

import solitaire.moves.AceToFoundations;
import solitaire.moves.CardsToFoundations;
import solitaire.moves.KingToEmpty;
import solitaire.moves.LateralMoves;
import solitaire.moves.Move;
import solitaire.moves.TalonAceToFoundations;
import solitaire.moves.TalonCardToFoundation;
import solitaire.moves.TalonCardToTableu;
import solitaire.moves.TalonKingToTableu;
import solitaire.utils.*;

public class Game {
    Manoeuvre[] tableu;
    Talon talon;
    Foundation[] foundations;
    BoardDisplay display;

    List<@NonNull Move> tableuMoves;
    List<@NonNull Move> talonMoves;
    
    public Game() {
        ArrayList<Card> deck = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                deck.add(new Card(suit, value));
            }
        }

        this.initializeTableu(deck);
        this.initializeFoundations();
        this.talon = new Talon();
        this.talon.withStock(this.initializeStock(deck));

        this.tableuMoves = new ArrayList<>(Arrays.asList(
            new AceToFoundations(tableu, foundations),
            new CardsToFoundations(tableu, foundations),
            new KingToEmpty(tableu),
            new LateralMoves(tableu)
        ));

        this.talonMoves = new ArrayList<>(Arrays.asList(
            new TalonAceToFoundations(talon, foundations),
            new TalonCardToFoundation(talon, foundations),
            new TalonKingToTableu(talon, tableu),
            new TalonCardToTableu(talon, tableu)
        ));
    }

    public void withDisplay(BoardDisplay display) {
        this.display = display;
    }

    public Manoeuvre[] getTableu() {
        return this.tableu;
    }

    public Talon getTalon() {
        return this.talon;
    }

    public Foundation[] getFoundations() {
        return this.foundations;
    }

    public void withTableu(Manoeuvre[] tableu) {
        this.tableu = tableu;
    }

    public void withFoundations(Foundation[] foundations) {
        this.foundations = foundations;
    }

    private void initializeTableu(ArrayList<Card> deck) {
        this.tableu = new Manoeuvre[7];
        for (int i = 0; i < 7; i++) {
            this.tableu[i] = new Manoeuvre(new LinkedList<Card>(), i);
            for (int j = 0; j < i+1; j++) {
                Card c = deck.remove(0);
                this.tableu[i].appendCard(c);
            }
        }
    }

    private void initializeFoundations() {
        this.foundations = new Foundation[4];
        for (int i = 0; i < 4; i++) {
            this.foundations[i] = new Foundation();
        }
    }

    private Deque<Card> initializeStock(ArrayList<Card> deck) {
        Deque<Card> stock = new ArrayDeque<>();

        while (!deck.isEmpty()) {
            Card c = deck.remove(0);
            stock.add(c);
        }

        return stock;
    }

    private void displayState() {
        if(this.display != null) {
            this.display.displayState();
        }
    }

    private void tableuMoves() {
        boolean movesMade = true;
        while(movesMade) {
            movesMade = false;
            for (Move move : this.tableuMoves) {
                boolean played = move.play();
                if(played) {
                    this.displayState();
                    movesMade = true;
                }
            }
        }
    }

    public boolean playOneCycle() {
        this.tableuMoves();

        boolean moveMade = false;
        boolean wasteMoves = false;
        while(!this.talon.isStockEmpty()) {
            System.out.println("Drawing cards from the talon");
            this.talon.drawThree();;
            this.displayState();

            for (Move move : this.talonMoves) {
                if(move.play()) {
                    wasteMoves = true;
                    this.displayState();
                }
            }

            if (wasteMoves) {
                moveMade = true;
                this.tableuMoves();
            }
        }

        this.talon.refresh();
        return moveMade;
    }

    public boolean areFoundationsComplete() {
        boolean complete = true;
        for (Foundation foundation : this.foundations) {
            if (foundation.size() < 13) {
                complete = false;
                break;
            }
        }

        return complete;
    }
}
