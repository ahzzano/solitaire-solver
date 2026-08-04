package solitaire.moves;

import java.util.Optional;

import solitaire.utils.Card;
import solitaire.utils.Manoeuvre;
import solitaire.utils.Rank;

public class LateralMoves implements Move {

    private Manoeuvre[] tableau;

    public LateralMoves(Manoeuvre[] tableau) {
        this.tableau = tableau;
    }

    private Optional<Integer> findDestination(Card card) {
        int index = 0;
        for (Manoeuvre manoeuvre : this.tableau) {
            int i = index;
            index++;

            if(manoeuvre.empty()) {
                continue;
            }

            Card bottomCard = manoeuvre.getRevealedBottomCard().get();

            if(card.isCompatibleBelow(bottomCard)) {
                return Optional.of(i);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean play() {
        boolean move = false;

        int sourceIndex = 0;
        for(Manoeuvre manoeuvre : this.tableau) {
            sourceIndex++;
            if(manoeuvre.empty()) {
                continue;
            }

            Card manoeuvreTop = manoeuvre.getRevealedTop();

            if(manoeuvreTop.rank() == Rank.KING) {
                continue;
            }

            Optional<Integer> targetManoeuvre = this.findDestination(manoeuvreTop);
            if(targetManoeuvre.isEmpty()) {
                continue;
            }

            int target = targetManoeuvre.get();

            if(tableau[target].empty()) {
                continue;
            }

            // TODO: combine into one function
            var temp = manoeuvre.splitStack(manoeuvre.revealedStart());
            if (temp.isEmpty()) {
                continue;
            }

            System.out.printf("Moved Cards %s and below from Manoeuvre #%d to Manouvre #%d\n", manoeuvreTop.toDisplayString(), sourceIndex, target+1);
            tableau[target].mergeStacks(temp.get());
            move = true;
            break;
        }
        
        return move;
    }
}
