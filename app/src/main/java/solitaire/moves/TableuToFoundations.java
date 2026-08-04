package solitaire.moves;

import solitaire.utils.Card;
import solitaire.utils.Foundation;
import solitaire.utils.Manoeuvre;

public class TableuToFoundations implements Move {
    Manoeuvre[] tableu;
    Foundation[] foundations;

    public TableuToFoundations(Manoeuvre[] tableu, Foundation[] foundations) {
        this.tableu = tableu;
        this.foundations = foundations;
    }

    @Override
    public boolean play() {
        boolean move = false;
        for (Manoeuvre stack : this.tableu) {
            if(move) {
                break;
            }
            if (stack.empty()) {
                continue;
            }

            for (Foundation foundation : foundations) {
                Card end = stack.getRevealedBottomCard().get();
                if (!foundation.pushable(end)) {
                    continue;
                }

                foundation.push(stack.popCard());
                System.out.println("Moved " + foundation.getTop().toDisplayString() + " to Foundations");
                move = true;
                break;
            }
        }
        return move;
    }
}
