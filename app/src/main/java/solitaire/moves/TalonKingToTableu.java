package solitaire.moves;

import solitaire.utils.Card;
import solitaire.utils.Manoeuvre;
import solitaire.utils.Talon;
import solitaire.utils.Rank;

public class TalonKingToTableu implements Move{
    Talon talon;
    Manoeuvre[] tableu;

    public TalonKingToTableu(Talon talon, Manoeuvre[] tableu) {
        this.talon = talon;
        this.tableu = tableu;
    }

    @Override
    public boolean play() {
        boolean move = false;
        if (this.talon.size() == 0) {
            this.talon.drawThree();;
        }

        Card top = this.talon.getTop();
        if (top.value() != Rank.KING) {
            return false;
        }

        int index=0;
        for (Manoeuvre manoeuvre : this.tableu) {
            if (manoeuvre.empty()) {
                Card c = this.talon.popTop();
                manoeuvre.pushCard(c);
                System.out.println("Moved " + c.toDisplayString() + " to " + (index+1));
                move = true;
                break;
            }
            index += 1;
        }

        return move;
    }
    
}
