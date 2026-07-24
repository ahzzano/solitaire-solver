package solitaire.utils;

import java.util.Deque;
import java.util.Scanner;

import solitaire.Game;

public class BoardDisplay {
    Game board;
    Scanner scanner;
    
    public BoardDisplay(Game board) {
        this.board = board;
    }

    public BoardDisplay() {

    }

    public static BoardDisplay withBoard(Game board) {
        BoardDisplay display = new BoardDisplay(board);
        board.withDisplay(display);
        return display;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayState() {
        Manoeuvre[] tableu = this.board.getTableu();
        Waste waste = this.board.getWaste();
        Deque<Card> stock = this.board.getStock();
        Foundation[] foundations = this.board.getFoundations();


        Card topWaste = waste.getTop();
        System.out.println("");

        if (topWaste != null) {
            System.out.print("S(" + stock.size() + ")");
            System.out.print("--" + topWaste.toDisplayString());
        } else {
            System.out.print(" [ empty ] ");
        }

        for (int i = 0; i < 16; i++) {
            System.out.print(" ");
        }

        for (Foundation foundation : foundations) {
            if (foundation.empty()) {
                System.out.print("----  ");
                continue;
            }

            System.out.print(foundation.getTop().toDisplayString() + "  ");
            if (foundation.getTop().value() != Value.TEN) {
                System.out.print(" ");
            }
        }

        System.out.println();
        System.out.println();

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

        for (int i = 0; i < maxSizeStack; i++) {
            for (Manoeuvre stack : tableu) {
                if (i >= stack.size()) {
                    if (i < 1) {
                        System.out.print("----   ");
                    }
                    else {
                        System.out.print("       ");
                    }
                    continue;
                }

                if (i < stack.revealedStart()) {
                    System.out.print("-(-)   ");
                    continue;
                }

                System.out.print(stack.getCard(i).toDisplayString());
                System.out.print("  ");
                if (stack.getCard(i).value() != Value.TEN) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println("");

        if(this.scanner != null) {
            this.scanner.nextLine();
        }
    }
}
