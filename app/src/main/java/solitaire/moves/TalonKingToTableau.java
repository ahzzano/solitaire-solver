package solitaire.moves;

import solitaire.utils.Card;
import solitaire.utils.Manoeuvre;
import solitaire.utils.Talon;
import solitaire.utils.Rank;

public class TalonKingToTableau implements Move{
    Talon talon;
    Manoeuvre[] tableau;

    public TalonKingToTableau(Talon talon, Manoeuvre[] tableu) {
        this.talon = talon;
        this.tableau = tableu;
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

        if (top.rank() != Rank.KING) {
            return false;
        }

        int index=0;
        for (Manoeuvre manoeuvre : this.tableau) {
            if (manoeuvre.empty()) {
                Card c = this.talon.popTop();
                manoeuvre.pushCard(c);
                System.out.println("Moved " + c.toDisplayString() + " from Talon to " + (index+1));
                move = true;
                break;
            }
            index += 1;
        }

        return move;
    }
    
}
