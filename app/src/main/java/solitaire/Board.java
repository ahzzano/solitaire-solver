package solitaire;

import java.util.*;

import solitaire.utils.*;

public class Board {
    Manoeuvre[] tableu;
    Waste waste;
    Deque<Card> stock;
    Foundation[] foundations;
    BoardDisplay display;
    
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

    public void withDisplay(BoardDisplay display) {
        this.display = display;
    }

    public Manoeuvre[] getTableu() {
        return this.tableu;
    }

    public Waste getWaste() {
        return this.waste;
    }

    public Deque<Card> getStock() {
        return this.stock;
    }

    public Foundation[] getFoundations() {
        return this.foundations;
    }

    public void withTableu(Manoeuvre[] tableu) {
        this.tableu = tableu;
    }

    public void withStock(Deque<Card> stock) {
        this.stock = stock;
    }

    public void withWaste(Waste waste) {
        this.waste = waste;
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

    private void initializeStock(ArrayList<Card> deck) {
        this.stock = new ArrayDeque<>();

        while (!deck.isEmpty()) {
            Card c = deck.remove(0);
            stock.add(c);
        }
    }

    private void displayState() {
        if(this.display != null) {
            this.display.displayState();
        }
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
                    System.out.println("Moved " + foundation.getTop().toDisplayString() + " to Foundations");
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
                System.out.println("Moved " + c.toDisplayString() + " to Foundations");
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

        int index = 0;
        for (Manoeuvre stack : this.tableu) {
            if (stack.empty()) {
                continue;
            }

            if (nextMarkedStack >= emptyStackIndexes.size()) {
                break;
            }

            if (stack.revealedStart() > 0 && stack.getRevealedTop().value() == Value.KING) {
                Manoeuvre kingStack = stack.splitStack(stack.revealedStart()).get();
                System.out.println("Moved " + kingStack.getRevealedTop().toDisplayString() + " to Manoeuvre #" + (index+1));
                this.tableu[emptyStackIndexes.get(nextMarkedStack)].mergeStacks(kingStack);
                nextMarkedStack += 1;
                move = true;
            }
            index++;
        }

        return move;
    }

    public boolean lateralMoves() {
        boolean move = false;

        // CardStack A - to move
        // CardStack B - to receive
        int indexA = 1;
        for (Manoeuvre manoeuvreToMove : this.tableu) {
            if (manoeuvreToMove.empty()) {
                continue;
            }
            if (manoeuvreToMove.revealedStart() == 0 && manoeuvreToMove.getRevealedTop().value() == Value.KING) {
                continue;
            }
            int indexB = 1;
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
                    System.out.println("Moved cards from Manoeuvre #" + (indexA) + " to Manoeuvre #" + (indexB));
                    break;
                }
                indexB++;
            }
            indexA++;
        }

        return move;
    }

    public boolean wasteCardToTableu() {
        boolean move = false;
        if (this.waste.size() == 0) {
            this.waste.drawThreeFrom(this.stock);
        }

        Card top = this.waste.getTop();
        int index = 0;
        for (Manoeuvre manoeuvre : this.tableu) {
            if (manoeuvre.empty()) {
                continue;
            }
            if (top.isCompatibleBelow(manoeuvre.getRevealedBottom())) {
                Card c = this.waste.popTop();
                System.out.println("Moved " + top.toDisplayString() + " to Manoeuvre #" + (index+1));

                manoeuvre.appendCard(c);
                move = true;
                break;
            }

            index++;
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
                System.out.println("Moved " + c.toDisplayString() + " to foundations");
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
            System.out.println("Moved " + c.toDisplayString() + " to foundations");
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

        int index=0;
        for (Manoeuvre manoeuvre : this.tableu) {
            if (manoeuvre.empty()) {
                Card c = this.waste.popTop();
                manoeuvre.pushCard(c);
                System.out.println("Moved " + c.toDisplayString() + " to " + (index+1));
                move = true;
                break;
            }
            index += 1;
        }

        return move;
    }

    private void tableuMoves() {
        boolean tableuMoves = true;

        while(tableuMoves) {
            tableuMoves = false;
            if (this.aceToFoundations()) {
                this.displayState();
                tableuMoves = true;
            }
            if (this.cardsToFoundation()) {
                this.displayState();
                tableuMoves = true;
            }
            if (this.kingToEmpty()) {
                this.displayState();
                tableuMoves = true;
            }
            if (this.lateralMoves()) {
                this.displayState();
                tableuMoves = true;
            }
        }

    }

    public boolean playOneCycle() {
        this.tableuMoves();

        boolean moveMade = false;
        boolean wasteMoves = false;
        while(!this.stock.isEmpty()) {
            System.out.println("Drawing cards from stock");
            this.waste.drawThreeFrom(this.stock);
            this.displayState();

            if (this.wasteAceToFoundation()) {
                wasteMoves = true;
                this.displayState();
            }
            if (this.wasteCardToFoundation()) {
                wasteMoves = true;
                this.displayState();
            }
            if (this.wasteKingToTableu()) {
                wasteMoves = true;
                this.displayState();
            }
            if (this.wasteCardToTableu()) {
                wasteMoves = true;
                this.displayState();
            }

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
