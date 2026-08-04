package solitaire.moves;

import java.util.Optional;

import solitaire.utils.Card;
import solitaire.utils.Manoeuvre;
import solitaire.utils.Talon;

public class TalonCardToTableau implements Move {
    Manoeuvre[] tableau;
    Talon talon;

    public TalonCardToTableau(Talon talon, Manoeuvre[] tableau) {
        this.tableau = tableau;
        this.talon = talon;
    }

    @Override
    public boolean play() {
        boolean move = false;
        if (this.talon.size() == 0) {
            this.talon.drawThree();
        }

        Optional<Card> topTemp = this.talon.getTop();
        if(topTemp.isEmpty()) {
            return false;
        }
        Card top = topTemp.get();
        int index = 0;
        for (Manoeuvre manoeuvre : this.tableau) {
            if (manoeuvre.empty()) {
                index++;
                continue;
            }
            if (top.isCompatibleBelow(manoeuvre.getRevealedBottomCard().get())) {
                Card c = this.talon.popTop();
                System.out.println("Moved " + top.toDisplayString() + " from Talon to Manoeuvre #" + (index + 1));

                manoeuvre.appendCard(c);
                move = true;
                break;
            }

            index++;
        }
        return move;
    }
}
