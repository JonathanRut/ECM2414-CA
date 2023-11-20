import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Player {
    private LinkedList<Card> hand;
    static private int numberOfPlayers = 1;
    private final int playerNumber;
    private final ConcurrentLinkedQueue<Card> drawDeck;
    private final ConcurrentLinkedQueue<Card> discardDeck;
    private LinkedList<String> history;
    private int won = Integer.MAX_VALUE;
    private int turn = 0;

    public Player(ConcurrentLinkedQueue<Card> drawDeck, ConcurrentLinkedQueue<Card> discardDeck){
        this.discardDeck = discardDeck;
        this.drawDeck = drawDeck;
        playerNumber = numberOfPlayers;
        numberOfPlayers++;
    }

    private void writeHistory(){}

    private int draw(){
        Card card = drawDeck.remove();
        hand.add(card);
        return card.getNumber();
    }

    private int discard(){
        ArrayList<Integer> possibleDiscards = new ArrayList<Integer>();
        for(int i = 0; i < hand.size(); i++){
            if (hand.get(i).getNumber() != playerNumber){
                possibleDiscards.add(i);
            }
        }
        Random rnd = new Random();
        int removeIndex = possibleDiscards.get(rnd.nextInt(0,possibleDiscards.size()));
        Card discardedCard = hand.remove(removeIndex);
        discardDeck.add(discardedCard);
        return discardedCard.getNumber();
    }

    private synchronized void makeMove(){
        turn++;
        int drawnNumber = draw();
        int discardNumber = discard();
        history.add("player " + playerNumber + " draws a " + drawnNumber + " from deck " + playerNumber);
        history.add("player " + playerNumber + "discards a " + discardNumber + " from deck " + (playerNumber + 1 == numberOfPlayers ? 0:playerNumber));
        history.add("player " + playerNumber + " current hand is " + hand.toString().replaceAll("[,]|[]]|[\\[]",""));
        hasWon();
    }

    public int handSize() {
        return hand.size();
    }

    public void receiveCard(Card card) {
        hand.add(card);
    }

    private void hasWon(){
        int matchingCards = 0;
        for(Card card : hand){
            if(card.getNumber() == hand.peekFirst().getNumber()){
                matchingCards += 1;
            }
        }
        if (matchingCards == 4){
            // TODO
        }
    }
}
