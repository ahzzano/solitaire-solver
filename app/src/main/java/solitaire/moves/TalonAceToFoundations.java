package solitaire.moves;

import solitaire.utils.Card;
import solitaire.utils.Foundation;
import solitaire.utils.Talon;
import solitaire.utils.Rank;

public class TalonAceToFoundations implements Move {

    private Foundation[] foundations;
    private Talon talon;

    public TalonAceToFoundations(Talon talon, Foundation[] foundations) {
        this.talon = talon;
        this.foundations = foundations;
    }

    @Override
    public boolean play() {
        boolean move = false;
        if (this.talon.size() == 0) {
            this.talon.drawThree();
        }

        Card top = this.talon.getTop();
        if (top.value() != Rank.ACE) {
            return false;
        }

        for (Foundation foundation : this.foundations) {
            if (!foundation.empty()) {
                continue;
            }

            Card c = this.talon.popTop();
            System.out.println("Moved " + c.toDisplayString() + " to foundations");
            foundation.push(c);
            move = true;
            break;
        }

        return move;
    }
    
}
