package solitaire.moves;

import java.util.Optional;

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
        this.talon.drawIfTalonEmpty();

        Optional<Card> topTemp = this.talon.getTop();
        if(topTemp.isEmpty()) {
            return false;
        }
        Card top = topTemp.get();
        if (top.rank() != Rank.ACE) {
            return false;
        }

        for (Foundation foundation : this.foundations) {
            if (!foundation.empty()) {
                continue;
            }

            Card c = this.talon.popTop();
            System.out.println("Moved " + c.toDisplayString() + " from Talon to foundations");
            foundation.push(c);
            move = true;
            break;
        }

        return move;
    }
    
}
