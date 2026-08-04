package solitaire.moves;

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
        if (this.talon.size() == 0) {
            this.talon.drawThree();;
        }

        Card top = this.talon.getTop();

        if(top == null) {
            return false;
            
        }

        for (Foundation foundation : this.foundations) {
            if (foundation.pushable(top)) {
                Card c = this.talon.popTop();
                System.out.println("Moved " + c.toDisplayString() + " from Talon to foundations");
                foundation.push(c);
                move = true;
                break;
            }
        }

        return move;
    }
    
}
