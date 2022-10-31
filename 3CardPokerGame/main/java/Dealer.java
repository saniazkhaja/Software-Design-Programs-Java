import java.util.ArrayList;
public class Dealer {
    Deck theDeck;
    ArrayList<Card> dealersHand;

    //Dealer constructor
    Dealer(){
        this.theDeck = new Deck();
    };

    //Remove 3 cards from the deck and deals them out
    public ArrayList<Card> dealHand() {
        ArrayList<Card> threeCards = new ArrayList<>();
        if (theDeck.size() < 35) {
            theDeck.newDeck();
        }
        threeCards.add(theDeck.get(0));
        threeCards.add(theDeck.get(1));
        threeCards.add(theDeck.get(2));
        theDeck.remove(threeCards.get(0));
        theDeck.remove(threeCards.get(1));
        theDeck.remove(threeCards.get(2));

        return threeCards;
    };
}
