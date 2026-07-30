package solitaire.moves;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

import solitaire.utils.Card;
import solitaire.utils.Manoeuvre;
import solitaire.utils.Rank;
import solitaire.utils.Suit;

/**
 * MoveKey
 * Used instead of a Card object as a key for the HashMap. This was done
 * as using a Card as a key in a HashMap would make comparisons by reference.
 * It's better if the comparisons are by value instead of by reference. MoveKey
 * does that well
 * @param rank
 * @param suit
 */
record MoveKey(Rank rank, Suit suit) {
    public static MoveKey fromCard(Card card) {
        return new MoveKey(card.value(), card.suit());
    }
}

public class LateralMoves implements Move{

    private Manoeuvre[] tableu;

    public LateralMoves(Manoeuvre[] tableu) {
        this.tableu = tableu;
    }

    private HashMap<MoveKey, Deque<Manoeuvre>> generateDestinationMap() {
        HashMap<MoveKey, Deque<Manoeuvre>> destMap = new HashMap<>();

        for(Manoeuvre manoeuvre : this.tableu) {
            Card bottomCard = manoeuvre.getRevealedBottom();
            Rank targetRank = Rank.fromValue(bottomCard.value().number() - 1);

            if(bottomCard.suit() == Suit.CLUBS || bottomCard.suit() == Suit.SPADES) {
                MoveKey moveKeyA = new MoveKey(targetRank, Suit.DIAMONDS);
                MoveKey moveKeyB = new MoveKey(targetRank, Suit.HEARTS);

                if(!destinationColumn.containsKey(moveKeyA)) {
                    destinationColumn.put(moveKeyA, new LinkedList<>());
                }
            }
        }

        return destMap;

    }

    @Override
    public boolean play() {
        // TODO: Optimize this move. 
        // Currently uses a double for loop that iterates the tableu twice
        // Try to make it use only one for loop

        boolean move = false;

        HashMap<MoveKey, Deque<Manoeuvre>> destinationColumn = generateDestinationMap();


        // CardStack A - to move
        // CardStack B - to receive
        // int indexA = 1;
        // for (Manoeuvre manoeuvreToMove : this.tableu) {
        //     if (manoeuvreToMove.empty()) {
        //         continue;
        //     }
        //     if (manoeuvreToMove.revealedStart() == 0 && manoeuvreToMove.getRevealedTop().value() == Rank.KING) {
        //         continue;
        //     }
        //     int indexB = 1;
        //     for (Manoeuvre manoeuvreToReceive : this.tableu) {
        //         if (manoeuvreToMove == manoeuvreToReceive) {
        //             continue;
        //         }
                
        //         if (manoeuvreToReceive.empty()) {
        //             continue;
        //         }

        //         if(manoeuvreToMove.getRevealedTop().isCompatibleBelow(manoeuvreToReceive.getRevealedBottom())) {
        //             var temp = manoeuvreToMove.splitStack(manoeuvreToMove.revealedStart());

        //             if(temp.isEmpty()) {
        //                 continue;
        //             }

        //             Manoeuvre cs = temp.get();

        //             manoeuvreToReceive.mergeStacks(cs);
        //             move = true;
        //             System.out.println("Moved cards from Manoeuvre #" + (indexA+1) + " to Manoeuvre #" + (indexB+1));
        //             break;
        //         }
        //         indexB++;
        //     }
        //     indexA++;
        // }

        return move;
    }
    
}
