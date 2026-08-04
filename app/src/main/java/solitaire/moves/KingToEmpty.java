package solitaire.moves;

import java.util.ArrayList;

import solitaire.utils.Manoeuvre;
import solitaire.utils.Rank;

public class KingToEmpty implements Move {
    Manoeuvre[] tableau;

    public KingToEmpty(Manoeuvre[] tableau) {
        this.tableau = tableau;
    }

    @Override
    public boolean play() {
        ArrayList<Integer> emptyStackIndexes = new ArrayList<>();
        boolean move = false;

        for (int i = 0; i < this.tableau.length; i++) {
            if (tableau[i].empty()) {
                emptyStackIndexes.add(i);
            }
        }

        if(emptyStackIndexes.isEmpty()) {
            return move;
        }

        int nextMarkedStack = 0;

        int index = 0;
        for (Manoeuvre stack : this.tableau) {
            if (stack.empty()) {
                continue;
            }

            if (nextMarkedStack >= emptyStackIndexes.size()) {
                break;
            }

            if (stack.revealedStart() > 0 && stack.getRevealedTop().rank() == Rank.KING) {
                Manoeuvre kingStack = stack.splitStack(stack.revealedStart()).get();
                System.out.println("Moved " + kingStack.getRevealedTop().toDisplayString() + " to Manoeuvre #" + (index+1));
                this.tableau[emptyStackIndexes.get(nextMarkedStack)].mergeStacks(kingStack);
                nextMarkedStack += 1;
                move = true;
                break;
            }
            index++;
        }

        return move;
    }
    
}
