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

            for (Foundation foundation : this.foundations) {
                var endTemp = manoeuvre.getRevealedBottomCard();
                if(endTemp.isEmpty()) {
                    continue;
                }
                Card end = endTemp.get();
                if (!foundation.pushable(end)) {
                    continue;
                }

                foundation.push(manoeuvre.popCard());
                System.out.println("Moved " + foundation.getTop().toDisplayString() + " to Foundations");
                move = true;
                break;
            }

            if(move == true) {
                break;
            }
        }

        return move;
    }

}
