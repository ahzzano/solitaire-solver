package solitaire;

import java.util.*;

import org.jspecify.annotations.NonNull;

import solitaire.moves.*;
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
            new AceToFoundations(this.tableu, this.foundations),
            // new CardsToFoundations(this.tableu, this.foundations),
            // new KingToEmpty(this.tableu),
            new LateralMoves(this.tableu)
        ));

        this.talonMoves = new ArrayList<>(Arrays.asList(
            // new TalonAceToFoundations(this.talon, this.foundations),
            // new TalonCardToFoundation(this.talon, this.foundations),
            // new TalonKingToTableu(this.talon, this.tableu),
            // new TalonCardToTableu(this.talon, this.tableu)
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

    // TODO: Refactor
    private void playTableuMoves() {
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

    private boolean playTalonMoves() {
        boolean talonMoves = false;
        for (Move move : this.talonMoves) {
            if(move.play()) {
                talonMoves = true;
                this.displayState();
            }
        }

        return talonMoves;
    }

    // TODO: Refactor
    public boolean playOneCycle() {
        this.playTableuMoves();

        boolean moveMade = false;
        boolean talonMove = false;
        while(!this.talon.isStockEmpty()) {
            System.out.println("Drawing cards from the talon");
            this.talon.drawThree();
            this.displayState();
            talonMove = this.playTalonMoves();

            if (talonMove) {
                moveMade = true;
                this.playTableuMoves();
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
