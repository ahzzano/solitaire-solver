package solitaire.moves;

import solitaire.utils.Card;
import solitaire.utils.Foundation;
import solitaire.utils.Manoeuvre;

public class CardsToFoundations implements Move {
    Manoeuvre[] tableu;
    Foundation[] foundations;

    public CardsToFoundations(Manoeuvre[] tableu, Foundation[] foundations) {
        this.tableu = tableu;
        this.foundations = foundations;
    }

    @Override
    public boolean play() {
        boolean move = false;

        for (Manoeuvre manoeuvre : this.tableu) {
            if (manoeuvre.empty()) {
                continue;
            }

            boolean pushable = true;
            while (!manoeuvre.empty() && pushable) {
                for (Foundation foundation : this.foundations) {
                    Card end = manoeuvre.getRevealedBottomCard().get();
                    if (!foundation.pushable(end)) {
                        pushable = false;
                        continue;
                    }

                    foundation.push(manoeuvre.popCard());
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
