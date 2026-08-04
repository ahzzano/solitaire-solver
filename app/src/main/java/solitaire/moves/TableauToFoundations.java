package solitaire.moves;

import solitaire.utils.Card;
import solitaire.utils.Foundation;
import solitaire.utils.Manoeuvre;

public class TableauToFoundations implements Move {
    Manoeuvre[] tableau;
    Foundation[] foundations;

    public TableauToFoundations(Manoeuvre[] tableau, Foundation[] foundations) {
        this.tableau = tableau;
        this.foundations = foundations;
    }

    @Override
    public boolean play() {
        boolean move = false;
        for (Manoeuvre stack : this.tableau) {
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
