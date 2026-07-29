package solitaire.moves;

import solitaire.utils.Card;
import solitaire.utils.Foundation;
import solitaire.utils.Manoeuvre;

public class AceToFoundations implements Move {
    Manoeuvre[] tableu;
    Foundation[] foundations;
    public AceToFoundations(Manoeuvre[] tableu, Foundation[] foundations) {
        this.tableu = tableu;
        this.foundations = foundations;
    }

    @Override
    public boolean play() {
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
}
