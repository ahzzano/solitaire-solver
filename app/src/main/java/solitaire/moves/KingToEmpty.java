package solitaire.moves;

import java.util.ArrayList;

import solitaire.utils.Manoeuvre;
import solitaire.utils.Rank;

public class KingToEmpty implements Move {
    Manoeuvre[] tableu;

    public KingToEmpty(Manoeuvre[] tableu) {
        this.tableu = tableu;
    }

    @Override
    public boolean play() {
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

            if (stack.revealedStart() > 0 && stack.getRevealedTop().rank() == Rank.KING) {
                Manoeuvre kingStack = stack.splitStack(stack.revealedStart()).get();
                System.out.println("Moved " + kingStack.getRevealedTop().toDisplayString() + " to Manoeuvre #" + (index+1));
                this.tableu[emptyStackIndexes.get(nextMarkedStack)].mergeStacks(kingStack);
                nextMarkedStack += 1;
                move = true;
                break;
            }
            index++;
        }

        return move;
    }
    
}
