import java.util.ArrayList;
import java.util.Collections;

public class ThreeCardLogic {
    //Returns integer value representing hand
    //0 for high card, 1 for straight flush, 2 for three of a kind, 3 for a straight, 4 for flush, 5 for a pair
    public static int evalHand(ArrayList<Card> hand) {
        ArrayList<Integer> cardNums = new ArrayList<>();
        for (Card i:hand) {
            cardNums.add(i.value);
        }
        Collections.sort(cardNums);

        if (hand.get(0).suit == hand.get(1).suit && hand.get(1).suit == hand.get(2).suit) {
            if (cardNums.get(0) == cardNums.get(1) - 1 && cardNums.get(1) == cardNums.get(2) - 1) {
                return 1;
            }
            return 4;
        } else if (cardNums.get(0) == cardNums.get(1) && cardNums.get(1) == cardNums.get(2)) {
            return 2;
        } else if (cardNums.get(0) == cardNums.get(1) - 1 && cardNums.get(1) == cardNums.get(2) - 1) {
            return 3;
        } else if (cardNums.get(0) == cardNums.get(1) || cardNums.get(1) == cardNums.get(2) || cardNums.get(0) == cardNums.get(2)) {
            return 5;
        }
        return 0;
    }

    //Return the amount won for PairPlus bet
    public static int evalPPWinnings(ArrayList<Card> hand, int bet) {
        int val = evalHand(hand);
        if (val == 1) {
            return 40 * bet + bet;
        } else if (val == 2) {
            return 30 * bet + bet;
        } else if (val == 3) {
            return 6 * bet +bet;
        } else if (val == 4) {
            return 3 * bet + bet;
        } else if (val == 5) {
            return bet+bet;
        }
        return 0;
    }

    //Compares player and dealer cards and return integer based on who won
    //0 if neither hand won, 1 if the dealer hand won, 2 if the player hand won
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player){
        int dealerVal = evalHand(dealer), playerVal = evalHand(player);

        if (dealerVal == 0 && dealer.get(0).value < 12 && dealer.get(1).value < 12 && dealer.get(2).value < 12) { //If dealer only has high cards and no Queen
            return 0;
        } else if (dealerVal == 0 && playerVal != 0) { //If the dealer only has high card but player's cards are better
            return 2;
        } else if (playerVal == 0 && dealerVal != 0) { //If the player only has high card but dealer's cards are better
            return 1;
        } else if (playerVal < dealerVal) { //If player cards are better
            return 2;
        } else if (dealerVal < playerVal) { //If dealer cards are better
            return 1;
        } else if (playerVal == dealerVal) { //If player and dealer have the same hand
            ArrayList<Integer> dealerCardNums = new ArrayList<>();
            for (Card i:dealer) {
                dealerCardNums.add(i.value);
            }
            Collections.sort(dealerCardNums);

            ArrayList<Integer> playerCardNums = new ArrayList<>();
            for (Card i:player) {
                playerCardNums.add(i.value);
            }
            Collections.sort(playerCardNums);

            //Compare high values for straight flush, three of a kind, and straight
            if (playerVal == 1 || playerVal == 2 || playerVal == 3) {
                if (playerCardNums.get(2) > dealerCardNums.get(2)) {
                    return 2;
                } else if (playerCardNums.get(2) < dealerCardNums.get(2)) {
                    return 1;
                }
            } else if (playerVal == 0 || playerVal == 4) { //Compare high values for high card and flush
                if (playerCardNums.get(2) > dealerCardNums.get(2)) {
                    return 2;
                } else if (playerCardNums.get(2) < dealerCardNums.get(2)) {
                    return 1;
                } else if (playerCardNums.get(1) > dealerCardNums.get(1)) {
                    return 2;
                } else if (playerCardNums.get(1) < dealerCardNums.get(1)) {
                    return 1;
                } else if (playerCardNums.get(0) > dealerCardNums.get(0)) {
                    return 2;
                } else if (playerCardNums.get(0) < dealerCardNums.get(0)) {
                    return 1;
                }
            } else if (playerVal == 5) { //Compare high values for pair
                int playerPair = playerCardNums.get(1), dealerPair = dealerCardNums.get(1); //Get the number of the pairs
                if (playerPair > dealerPair) {
                    return 2;
                } else if (playerPair < dealerPair) {
                    return 1;
                } else if (playerPair == dealerPair) {
                    int playerCard, dealerCard; //Get the 3rd card that's not in the pair
                    if (playerPair == playerCardNums.get(0)) {
                        playerCard = playerCardNums.get(2);
                    } else {
                        playerCard = playerCardNums.get(0);
                    }

                    if (dealerPair == dealerCardNums.get(0)) {
                        dealerCard = dealerCardNums.get(2);
                    } else {
                        dealerCard = dealerCardNums.get(0);
                    }

                    if (playerCard > dealerCard) {
                        return 2;
                    } else if (playerCard < dealerCard) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
}
