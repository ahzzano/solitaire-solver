package solitaire.moves;

import java.util.Optional;

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
        this.talon.drawIfTalonEmpty();

        Optional<Card> topTemp = this.talon.getTop();
        if(topTemp.isEmpty()) {
            return false;
        }
        Card top = topTemp.get();

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
