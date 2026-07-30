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
            // Use the same queue. That way, if a card gets placed into one
            // both will be dequeued
            LinkedList<Manoeuvre> queue = new LinkedList<>();

            if(manoeuvre.empty()) {
                continue;
            }

            Card bottomCard = manoeuvre.getRevealedBottom();
            Rank targetRank = Rank.fromValue(bottomCard.value().number() - 1);

            queue.add(manoeuvre);

            if(bottomCard.suit() == Suit.CLUBS || bottomCard.suit() == Suit.SPADES) {
                MoveKey moveKeyA = new MoveKey(targetRank, Suit.DIAMONDS);
                MoveKey moveKeyB = new MoveKey(targetRank, Suit.HEARTS);

                if(!destMap.containsKey(moveKeyA)) {
                    destMap.put(moveKeyA, queue);
                } 

                if(!destMap.containsKey(moveKeyB)) {
                    destMap.put(moveKeyB, queue);
                } 
            } 
            if(bottomCard.suit() == Suit.DIAMONDS || bottomCard.suit() == Suit.HEARTS) {
                MoveKey moveKeyA = new MoveKey(targetRank, Suit.CLUBS);
                MoveKey moveKeyB = new MoveKey(targetRank, Suit.SPADES);

                if(!destMap.containsKey(moveKeyA)) {
                    destMap.put(moveKeyA, queue);
                } 

                if(!destMap.containsKey(moveKeyB)) {
                    destMap.put(moveKeyB, queue);
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

        HashMap<MoveKey, Deque<Manoeuvre>> destMap = this.generateDestinationMap();
        for (Manoeuvre manoeuvre : this.tableu) {
            if(manoeuvre.empty()) {
                continue;
            }

            Card manoeuvreTop = manoeuvre.getRevealedTop();

            // Skip all King to Empty moves.
            // This should be handled by KingToEmpty() instead
            if(manoeuvreTop.value() == Rank.KING) {
                continue;
            }

            MoveKey targetMove = new MoveKey(manoeuvreTop.value(), manoeuvreTop.suit());
            if(destMap.containsKey(targetMove)) {
                Deque<Manoeuvre> nextManoeuvre = destMap.get(targetMove);

                if(nextManoeuvre.isEmpty()) {
                    continue;
                }

                boolean validNext = false;

                Manoeuvre dest;

                do {
                    dest = nextManoeuvre.poll();
                    if(dest == null) {
                        break;
                    }
                    Card manoeuvreBottom = dest.getRevealedBottom();
                    if(manoeuvreBottom == null) {
                        continue;
                    }
                    if(!manoeuvreTop.isCompatibleBelow(manoeuvreBottom)) {
                        continue;
                    }

                    validNext = true;
                } while(!validNext);

                if(dest == null) {
                    continue;
                }

                var temp = manoeuvre.splitStack(manoeuvre.revealedStart());

                if(temp.isEmpty()) {
                    continue;
                }

                System.out.println("Moved Cards from " + targetMove);
                dest.mergeStacks(temp.get());
                move = true;

            }
        }

        return move;
    }
    
}
