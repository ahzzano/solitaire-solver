package solitaire.moves;

import solitaire.utils.Card;
import solitaire.utils.Manoeuvre;
import solitaire.utils.Talon;

public class TalonCardToTableu implements Move {
    Manoeuvre[] tableu;
    Talon talon;

    public TalonCardToTableu(Talon talon, Manoeuvre[] tableu) {
        this.tableu = tableu;
        this.talon = talon;
    }

    @Override
    public boolean play() {
        boolean move = false;
        if (this.talon.size() == 0) {
            this.talon.drawThree();
        }

        Card top = this.talon.getTop();
        int index = 0;
        for (Manoeuvre manoeuvre : this.tableu) {
            if (manoeuvre.empty()) {
                continue;
            }
            if (top.isCompatibleBelow(manoeuvre.getRevealedBottom())) {
                Card c = this.talon.popTop();
                System.out.println("Moved " + top.toDisplayString() + " to Manoeuvre #" + (index+1));

                manoeuvre.appendCard(c);
                move = true;
                break;
            }

            index++;
        }
        return move;
    }
}
