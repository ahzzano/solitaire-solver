package solitaire.moves;

import solitaire.utils.Manoeuvre;
import solitaire.utils.Rank;

public class KingToEmpty implements Move {
    Manoeuvre[] tableau;

    public KingToEmpty(Manoeuvre[] tableau) {
        this.tableau = tableau;
    }

    @Override
    public boolean play() {
        int emptyStackIndex = -1;
        boolean move = false;

        for(int i=0; i<this.tableau.length; i++) {
            if(tableau[i].empty()) {
                emptyStackIndex = i;
                break;
            }
        }

        if(emptyStackIndex == -1) {
            return false;
        }

        for (Manoeuvre stack : this.tableau) {
            if (stack.empty()) {
                continue;
            }

            if (stack.revealedStart() > 0 && stack.getRevealedTop().rank() == Rank.KING) {
                Manoeuvre kingStack = stack.splitStack(stack.revealedStart()).get();
                System.out.println("Moved " + kingStack.getRevealedTop().toDisplayString() + " to Manoeuvre #" + (emptyStackIndex+1));
                this.tableau[emptyStackIndex].mergeStacks(kingStack);
                move = true;
                break;
            }
        }

        return move;
    }
    
}
