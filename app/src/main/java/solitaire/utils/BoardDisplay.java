package solitaire.utils;

import java.util.Scanner;

import solitaire.Game;

public class BoardDisplay {
    Game board;
    Scanner scanner;
    
    public BoardDisplay(Game board) {
        this.board = board;
    }

    public static BoardDisplay withBoard(Game board) {
        BoardDisplay display = new BoardDisplay(board);
        board.withDisplay(display);
        return display;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public void pause() {
        if(this.scanner != null) {
            this.scanner.nextLine();
        }
    }

    // TODO: Refactor to use System.out.printf()
    private void displayTalonAndFoundations(Talon talon, Foundation[] foundations) {
        Card topWaste = talon.getTop();
        System.out.println("      Talon                    Foundations");

        String deckDisplay = "";

        if (topWaste != null) {
            deckDisplay = String.format("D(%d) -- [ %s ]", talon.stockSize(), topWaste.toDisplayString());
        } else {
            deckDisplay = String.format("[ empty ]");
        }

        String foundationsString = "";

        for (Foundation foundation : foundations) {
            if (foundation.empty()) {
                foundationsString += String.format("%6s", "----");
                continue;
            }

            foundationsString += String.format("%6s", foundation.getTop().toDisplayString());
        }

        System.out.printf("%-22s%18s", deckDisplay, foundationsString);

        System.out.println();
        System.out.println();

    }

    // TODO: Refactor to use System.out.printf()
    private void printEmptyManoeuvre() {
        System.out.print("----   ");
    }

    private void printCardDisplayString(Card card) {
        System.out.print(card.toDisplayString());
        System.out.print("  ");
        if (card.value() != Rank.TEN) {
            System.out.print(" ");
        }
    }

    // TODO: Refactor to use System.out.printf()
    private void printHiddenCard() {
        System.out.print("-(-)   ");

    }
    
    // TODO: Refactor to use System.out.printf()
    private void displayTableu(Manoeuvre[] tableu) {
        int maxSizeStack = 0;

        for (Manoeuvre stack : tableu) {
            if (stack.size() > maxSizeStack) {
                maxSizeStack = stack.size();
            }
        }

        for (int i = 0; i < tableu.length; i++) {
            System.out.print("Mv#" + (i+1) + "   ");
        }
        System.out.println();

        if(maxSizeStack <= 0) {
            for(int i=0; i<7; i++) {
                this.printEmptyManoeuvre();
            }
        }

        for (int i = 0; i < maxSizeStack; i++) {
            for (Manoeuvre stack : tableu) {
                if (i >= stack.size()) {
                    if (i < 1) {
                        this.printEmptyManoeuvre();
                    }
                    else {
                        System.out.print("       ");
                    }
                    continue;
                }

                if (i < stack.revealedStart()) {
                    this.printHiddenCard();
                    continue;
                }

                Card card = stack.getCard(i);
                this.printCardDisplayString(card);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public void displayState() {
        Manoeuvre[] tableu = this.board.getTableu();
        Talon talon = this.board.getTalon();
        Foundation[] foundations = this.board.getFoundations();

        System.out.println("=====================================");
        this.displayTalonAndFoundations(talon, foundations);
        this.displayTableu(tableu);
        System.out.println("=====================================");
    }
}
