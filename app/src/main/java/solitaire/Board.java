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
        this.waste = new Waste();
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

    public boolean cardsToFoundation() {
        boolean move = false;

        for (Manoeuvre stack : this.tableu) {
            if (stack.empty()) {
                continue;
            }

            boolean pushable = true;
            while (!stack.empty() && pushable) {
                for (Foundation foundation : foundations) {
                    Card end = stack.getRevealedBottom();
                    if (!foundation.pushable(end)) {
                        pushable = false;
                        continue;
                    }

                    foundation.push(stack.popCard());
                    move = true;
                    pushable = true;
                    break;
                }
            }
        }

        return move;
    }

    public boolean aceToFoundations() {
        boolean move = false;
        for (Manoeuvre stack : this.tableu) {
            if (stack.empty()) {
                continue;
            }
            Card end = stack.getRevealedBottom();
            if (end.value() != Value.ACE) {
                continue;
            }

            for (Foundation foundation : this.foundations) {
                if (!foundation.empty()) {
                    continue;
                }

                Card c = stack.popCard();
                foundation.push(c);
                move = true;
                break;
            }
        }

        return move;
    }

    public boolean kingToEmpty() {
        ArrayList<Integer> emptyStackIndexes = new ArrayList<>();
        boolean move = false;

        for (int i = 0; i < this.tableu.length; i++) {
            if (tableu[i].empty()) {
                emptyStackIndexes.add(i);
            }
        }

        if(emptyStackIndexes.isEmpty()) {
            return move;
        }

        int nextMarkedStack = 0;

        for (Manoeuvre stack : this.tableu) {
            if (stack.empty()) {
                continue;
            }

            if (nextMarkedStack >= emptyStackIndexes.size()) {
                break;
            }

            if (stack.revealedStart() > 0 && stack.getRevealedTop().value() == Value.KING) {
                Manoeuvre kingStack = stack.splitStack(stack.revealedStart()).get();
                this.tableu[emptyStackIndexes.get(nextMarkedStack)].mergeStacks(kingStack);
                nextMarkedStack += 1;
                move = true;
            }
        }

        return move;
    }

    public boolean lateralMoves() {
        boolean move = false;

        // CardStack A - to move
        // CardStack B - to receive
        for (Manoeuvre manoeuvreToMove : this.tableu) {
            if (manoeuvreToMove.empty()) {
                continue;
            }
            if (manoeuvreToMove.revealedStart() == 0 && manoeuvreToMove.getRevealedTop().value() == Value.KING) {
                continue;
            }
            for (Manoeuvre manoeuvreToReceive : this.tableu) {
                if (manoeuvreToMove == manoeuvreToReceive) {
                    continue;
                }
                
                if (manoeuvreToReceive.empty()) {
                    continue;
                }

                if(manoeuvreToMove.getRevealedTop().isCompatibleBelow(manoeuvreToReceive.getRevealedBottom())) {
                    var temp = manoeuvreToMove.splitStack(manoeuvreToMove.revealedStart());

                    if(temp.isEmpty()) {
                        continue;
                    }

                    Manoeuvre cs = temp.get();

                    manoeuvreToReceive.mergeStacks(cs);
                    move = true;
                    break;
                }
            }
        }

        return move;
    }

    public boolean wasteCardToTableu() {
        boolean move = false;
        if (this.waste.size() == 0) {
            this.waste.drawThreeFrom(this.stock);
        }

        Card top = this.waste.getTop();
        for (Manoeuvre manoeuvre : this.tableu) {
            if (manoeuvre.empty()) {
                continue;
            }
            if (top.isCompatibleBelow(manoeuvre.getRevealedBottom())) {
                Card c = this.waste.popTop();

                manoeuvre.appendCard(c);
                move = true;
                break;
            }
        }
        return move;
    }

    public boolean wasteCardToFoundation() {
        boolean move = false;
        if (this.waste.size() == 0) {
            this.waste.drawThreeFrom(this.stock);
        }

        Card top = this.waste.getTop();

        for (Foundation foundation : this.foundations) {
            if (foundation.pushable(top)) {
                Card c = this.waste.popTop();
                foundation.push(c);
                move = true;
                break;
            }
        }

        return move;
    }

    public boolean wasteAceToFoundation() {
        boolean move = false;
        if (this.waste.size() == 0) {
            this.waste.drawThreeFrom(this.stock);
        }

        Card top = this.waste.getTop();
        if (top.value() != Value.ACE) {
            return false;
        }

        for (Foundation foundation : this.foundations) {
            if (!foundation.empty()) {
                continue;
            }

            Card c = this.waste.popTop();
            foundation.push(c);
            move = true;
            break;
        }

        return move;
    }

    public boolean wasteKingToTableu() {
        boolean move = false;
        if (this.waste.size() == 0) {
            this.waste.drawThreeFrom(this.stock);
        }

        Card top = this.waste.getTop();
        if (top.value() != Value.KING) {
            return false;
        }

        for (Manoeuvre cardStack : this.tableu) {
            if (cardStack.empty()) {
                cardStack.pushCard(this.waste.popTop());
                move = true;
                break;
            }
        }

        return move;
    }

    private void tableuMoves() {
        boolean tableuMoves = true;

        while(tableuMoves) {
            tableuMoves = false;
            tableuMoves = tableuMoves || this.aceToFoundations();
            tableuMoves = tableuMoves || this.cardsToFoundation();
            tableuMoves = tableuMoves || this.kingToEmpty();
            tableuMoves = tableuMoves || this.lateralMoves();
        }

    }

    public boolean playOneCycle() {
        this.tableuMoves();

        boolean moveMade = false;
        while(!this.stock.isEmpty()) {
            this.waste.drawThreeFrom(this.stock);
            boolean wasteMoves = this.wasteAceToFoundation();
            wasteMoves = wasteMoves || this.wasteCardToFoundation();
            wasteMoves = wasteMoves || this.wasteKingToTableu();
            wasteMoves = wasteMoves || this.wasteCardToTableu();

            if (wasteMoves) {
                moveMade = true;
                this.tableuMoves();
            }
        }

        this.waste.refresh(this.stock);
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
