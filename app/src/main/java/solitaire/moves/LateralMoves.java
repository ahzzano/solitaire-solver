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

    public static MoveKey[] expectedCards(Card card) {
        Rank targetRank = Rank.fromValue(card.value().number() - 1);

        if(card.suit() == Suit.DIAMONDS || card.suit() == Suit.HEARTS) {
            return new MoveKey[] {
                new MoveKey(targetRank, Suit.CLUBS),
                new MoveKey(targetRank, Suit.SPADES)
            };
        }

        return new MoveKey[] {
            new MoveKey(targetRank, Suit.DIAMONDS),
            new MoveKey(targetRank, Suit.HEARTS)
        };

    }
}

public class LateralMoves implements Move{

    private Manoeuvre[] tableu;

    public LateralMoves(Manoeuvre[] tableu) {
        this.tableu = tableu;
    }

    private void addToDestMap(HashMap<MoveKey, Deque<Manoeuvre>> destMap, MoveKey[] moveKeys, Deque<Manoeuvre> queue) {
        for (MoveKey moveKey : moveKeys) {
            
            if(!destMap.containsKey(moveKey)) {
                destMap.put(moveKey, queue);
            } 
        }
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

            queue.add(manoeuvre);

            MoveKey[] moveKeys = MoveKey.expectedCards(bottomCard);
            this.addToDestMap(destMap, moveKeys, queue);
        }

        return destMap;
    }

    // TODO: Refactor me
    // This function is way too long. Needs to be chopped up further
    // Also, add proper display thx
    @Override
    public boolean play() {
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
                    destMap.remove(targetMove);
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
