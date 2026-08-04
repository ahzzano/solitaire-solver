package solitaire.moves;

import java.util.Optional;

import solitaire.utils.Card;
import solitaire.utils.Foundation;
import solitaire.utils.Talon;

public class TalonCardToFoundation implements Move{
    private Talon talon;
    private Foundation[] foundations;

    public TalonCardToFoundation(Talon talon, Foundation[] foundations) {
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

        for (Foundation foundation : this.foundations) {
            if (foundation.pushable(top)) {
                Card c = this.talon.popTop().get();
                System.out.println("Moved " + c.toDisplayString() + " from Talon to foundations");
                foundation.push(c);
                move = true;
                break;
            }
        }

        return move;
    }
    
}
