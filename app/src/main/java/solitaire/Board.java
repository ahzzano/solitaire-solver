package solitaire;

import java.util.*;

import solitaire.utils.*;

public class Board {
    Manoeuvre[] tableu;
    Waste waste;
    Deque<Card> stock;
    Foundation[] foundations;

    public Board() {
        ArrayList<Card> deck = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                deck.add(new Card(suit, value));
            }
        }

        this.initializeTableu(deck);
        this.initializeStock(deck);
        this.initializeFoundations();
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

    private void initializeStock(ArrayList<Card> deck) {
        this.stock = new ArrayDeque<>();

        while (!deck.isEmpty()) {
            Card c = deck.remove(0);
            stock.add(c);
        }
    }

    public void displayState(Scanner scanner) {
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
        scanner.nextLine();
    }

    
}
