package solitaire.moves;

import java.util.Optional;

import solitaire.utils.Card;
import solitaire.utils.Manoeuvre;
import solitaire.utils.Rank;

public class LateralMoves implements Move {

    private Manoeuvre[] tableu;

    public LateralMoves(Manoeuvre[] tableu) {
        this.tableu = tableu;
    }

    private Optional<Integer> findDestination(Card card) {
        int index = 0;
        for (Manoeuvre manoeuvre : this.tableu) {
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
        for(Manoeuvre manoeuvre : this.tableu) {
            if(manoeuvre.empty()) {
                sourceIndex++;
                continue;
            }

            Card manoeuvreTop = manoeuvre.getRevealedTop();

            if(manoeuvreTop.value() == Rank.KING) {
                sourceIndex++;
                continue;
            }

            Optional<Integer> targetManoeuvre = this.findDestination(manoeuvreTop);
            if(targetManoeuvre.isEmpty()) {
                sourceIndex++;
                continue;
            }

            int target = targetManoeuvre.get();

            if(tableu[target].empty()) {
                sourceIndex++;
                continue;
            }

            var temp = manoeuvre.splitStack(manoeuvre.revealedStart());
            if (temp.isEmpty()) {
                sourceIndex++;
                continue;
            }

            System.out.printf("Moved Cards from Manoeuvre #%d to Manouvre #%d\n", sourceIndex+1, target+1);
            tableu[target].mergeStacks(temp.get());
            move = true;
            break;
        }
        
        return move;
    }
}
