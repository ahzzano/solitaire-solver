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

    int numOfMoves = 0;
    int numOfCycles = 0;

    List<@NonNull Move> tableuMoves;
    List<@NonNull Move> talonMoves;
    
    public void displayDeck(ArrayList<Card> cards) {
        int maxRowLength = 7;
        int rowLength = 0;

        for (Card card : cards) {
            System.out.printf("%-6s ", card.toDisplayString());
            rowLength += 1;
            if(rowLength == maxRowLength) {
                rowLength = 0;
                System.out.println();
            }
        }
        System.out.println();
        System.out.println();
    }

    private void initializeGameWithDeck(ArrayList<Card> deck) {
        this.displayDeck(deck);

        this.initializeTableu(deck);
        this.initializeFoundations();
        this.talon = new Talon();
        this.talon.withStock(this.initializeStock(deck));

        this.tableuMoves = new ArrayList<>(Arrays.asList(
            new AceToFoundations(this.tableu, this.foundations),
            new CardsToFoundations(this.tableu, this.foundations),
            new KingToEmpty(this.tableu),
            new LateralMoves(this.tableu)
        ));

        this.talonMoves = new ArrayList<>(Arrays.asList(
            new TalonAceToFoundations(this.talon, this.foundations),
            new TalonCardToFoundation(this.talon, this.foundations),
            new TalonKingToTableu(this.talon, this.tableu),
            new TalonCardToTableu(this.talon, this.tableu)
        ));
    }

    public Game(ArrayList<Card> deck) {
        this.initializeGameWithDeck(deck);
    }

    public Game() {
        ArrayList<Card> deck = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank value : Rank.values()) {
                deck.add(new Card(suit, value));
            }
        }
        Collections.shuffle(deck);

        this.initializeGameWithDeck(deck);
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

    public void printStats() {
        System.out.println("Moves: " + this.numOfMoves);
        System.out.println("Deck Cycles: " + this.numOfCycles);
    }

    private boolean playMoves(List<@NonNull Move> moves) {
        boolean movesMade = true;
        boolean toRet = false;
        while(movesMade) {
            movesMade = false;
            for (Move move : moves) {
                while(move.play()) {
                    this.numOfMoves++; 
                    this.printStats();
                    this.displayState();
                    this.display.pause();
                    movesMade = true;
                    toRet = true;
                }
            }
        }
        return toRet;
    }

    private boolean playTableuMoves() {
        return this.playMoves(this.tableuMoves);
    }

    private boolean playTalonMoves() {
        return this.playMoves(this.talonMoves);
    }

    // TODO: Refactor
    public boolean playOneCycle() {
        this.playTableuMoves();

        boolean moveMade = false;
        boolean talonMove = false;
        while(!this.talon.isStockEmpty()) {
            System.out.println("Drawing cards from the talon");
            this.talon.drawThree();
            this.numOfMoves++;
            this.printStats();
            this.displayState();
            this.display.pause();

            talonMove = this.playTalonMoves();

            if (talonMove) {
                moveMade = true;
                this.playTableuMoves();
            }
        }

        this.numOfCycles++;

        return moveMade;
    }

    public boolean play() {
        boolean move = true;
        boolean win = false;

        while (move && !win) {
            move = this.playOneCycle();

            if (this.areFoundationsComplete()) {
                win = true;
                break;
            }

            this.talon.refresh();
        }

        return win;
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
