package solitaire.moves;

import solitaire.utils.Manoeuvre;
import solitaire.utils.Value;

public class LateralMoves implements Move{

    private Manoeuvre[] tableu;

    public LateralMoves(Manoeuvre[] tableu) {
        this.tableu = tableu;
    }

    @Override
    public boolean play() {
        // TODO: Optimize this move. 
        // Currently uses a double for loop that iterates the tableu twice
        // Try to make it use only one for loop

        boolean move = false;

        // CardStack A - to move
        // CardStack B - to receive
        int indexA = 1;
        for (Manoeuvre manoeuvreToMove : this.tableu) {
            if (manoeuvreToMove.empty()) {
                continue;
            }
            if (manoeuvreToMove.revealedStart() == 0 && manoeuvreToMove.getRevealedTop().value() == Value.KING) {
                continue;
            }
            int indexB = 1;
            for (Manoeuvre manoeuvreToReceive : this.tableu) {
                if (manoeuvreToMove == manoeuvreToReceive) {
                    continue;
                }
                
                if (manoeuvreToReceive.empty()) {
                    continue;
                }

                if(manoeuvreToMove.getRevealedTop().isCompatibleBelow(manoeuvreToReceive.getRevealedBottom())) {
                    var temp = manoeuvreToMove.splitStack(manoeuvreToMove.revealedStart());

                    if(temp.isEmpty()) {
                        continue;
                    }

                    Manoeuvre cs = temp.get();

                    manoeuvreToReceive.mergeStacks(cs);
                    move = true;
                    System.out.println("Moved cards from Manoeuvre #" + (indexA+1) + " to Manoeuvre #" + (indexB+1));
                    break;
                }
                indexB++;
            }
            indexA++;
        }

        return move;
    }
    
}
