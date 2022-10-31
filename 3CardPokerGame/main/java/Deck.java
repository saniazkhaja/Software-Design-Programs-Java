import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card> {
    // Constructor to create a new deck of cards that are sorted in random order
    Deck() {
        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 15; j++) {
                Card newCard;
                if (i == 0) {
                    newCard = new Card('C', j);
                } else if (i == 1) {
                    newCard = new Card('D', j);
                } else if (i == 2) {
                    newCard = new Card('S', j);
                } else {
                    newCard = new Card('H', j);
                }
                this.add(newCard);
            }
        }
        Collections.shuffle(this);
    };

    //Clears the current deck and creates a new deck of cards that are sorted in random order
    public void newDeck() {
        this.clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 2; j < 15; j++) {
                Card newCard;
                if (i == 0) {
                    newCard = new Card('C', j);
                } else if (i == 1) {
                    newCard = new Card('D', j);
                } else if (i == 2) {
                    newCard = new Card('S', j);
                } else {
                    newCard = new Card('H', j);
                }
                this.add(newCard);
            }
        }
        Collections.shuffle(this);
    };
}
