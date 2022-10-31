import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class MyTest {
	//Test Card constructor
	@Test
	void testCard() {
		Card myCard = new Card('C', 5);
		assertEquals('C', myCard.suit, "Card suit not correct");
		assertEquals(5, myCard.value, "Card value not correct");
	}

	//Test default constructor of Deck
	@Test
	void testDeckDefault() {
		Deck myDeck = new Deck();
		assertEquals(52, myDeck.size(), "Deck is not the correct size.");
	}

	//Test default deck for cards
	@Test
	void TestDeckDefaultCards() {
		Deck myDeck = new Deck();
		int countC = 0, countS = 0, countH = 0, countD = 0;
		for (int i = 0; i < myDeck.size(); i++) {
			if (myDeck.get(i).suit == 'C') {
				countC += myDeck.get(i).value;
			} else if (myDeck.get(i).suit == 'S') {
				countS += myDeck.get(i).value;
			} else if (myDeck.get(i).suit == 'H') {
				countH += myDeck.get(i).value;
			} else if (myDeck.get(i).suit == 'D') {
				countD += myDeck.get(i).value;
			}
		}
		assertEquals(2+3+4+5+6+7+8+9+10+11+12+13+14, countC, "C cards not correct for default deck");
		assertEquals(2+3+4+5+6+7+8+9+10+11+12+13+14, countD, "D cards not correct for default deck");
		assertEquals(2+3+4+5+6+7+8+9+10+11+12+13+14, countH, "H cards not correct for default deck");
		assertEquals(2+3+4+5+6+7+8+9+10+11+12+13+14, countS, "S cards not correct for default deck");
	}

	//Test cards are randomized for new decks
	@Test
	void testDeckDefaultRandom() {
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));
		Deck myDeck = new Deck();
		for (int i = 0; i < myDeck.size(); i++) {
			System.out.println(myDeck.get(i).suit + " " + myDeck.get(i).value + "\n");
		}

		ByteArrayOutputStream outputStreamCaptor2 = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor2));
		Deck myDeck2 = new Deck();
		for (int i = 0; i < myDeck2.size(); i++) {
			System.out.println(myDeck2.get(i).suit + " " + myDeck2.get(i).value + "\n");
		}

		assertNotEquals(outputStreamCaptor, outputStreamCaptor2, "Decks are the same");
	}

	//Test clearing out and making a new deck
	@Test
	void testNewDeck() {
		ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor));

		Deck myDeck = new Deck();
		for (int i = 0; i < myDeck.size(); i++) {
			System.out.println(myDeck.get(i).suit + " " + myDeck.get(i).value + "\n");
		}

		ByteArrayOutputStream outputStreamCaptor2 = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outputStreamCaptor2));
		myDeck.newDeck();
		for (int i = 0; i < myDeck.size(); i++) {
			System.out.println(myDeck.get(i).suit + " " + myDeck.get(i).value + "\n");
		}
		assertNotEquals(outputStreamCaptor, outputStreamCaptor2, "Deck did not change");
	}

	//Test making a new deck if the card count is less than or equal to 34
	@Test
	void testMakeNewDeck() {
		Dealer theDealer = new Dealer();
		int count = 0;
		while(theDealer.theDeck.size() > 34) {
			count++;
			theDealer.dealersHand = theDealer.dealHand();
			assertEquals(52-3*count, theDealer.theDeck.size(), "Deck size not correct after dealing cards");
			theDealer.dealersHand.clear();
		}

		theDealer.theDeck.newDeck();
		assertEquals(52, theDealer.theDeck.size(), "New deck size not correct");
	}

	//Test new deck for cards
	@Test
	void TestNewDeckCards() {
		Deck myDeck = new Deck();
		myDeck.newDeck();
		int countC = 0, countS = 0, countH = 0, countD = 0;
		for (int i = 0; i < myDeck.size(); i++) {
			if (myDeck.get(i).suit == 'C') {
				countC += myDeck.get(i).value;
			} else if (myDeck.get(i).suit == 'S') {
				countS += myDeck.get(i).value;
			} else if (myDeck.get(i).suit == 'H') {
				countH += myDeck.get(i).value;
			} else if (myDeck.get(i).suit == 'D') {
				countD += myDeck.get(i).value;
			}
		}
		assertEquals(2+3+4+5+6+7+8+9+10+11+12+13+14, countC, "C cards not correct for default deck");
		assertEquals(2+3+4+5+6+7+8+9+10+11+12+13+14, countD, "D cards not correct for default deck");
		assertEquals(2+3+4+5+6+7+8+9+10+11+12+13+14, countH, "H cards not correct for default deck");
		assertEquals(2+3+4+5+6+7+8+9+10+11+12+13+14, countS, "S cards not correct for default deck");
	}

	//Test the dealer constructor and make sure a hand was dealt to the dealer
	@Test
	void testDealerConstructor() {
		Dealer theDealer = new Dealer();
		assertEquals(52, theDealer.theDeck.size(), "Dealer deck size not correct");
		int count = 0;
		while(theDealer.theDeck.size() > 34) {
			assertEquals(52-3*count, theDealer.theDeck.size(), "Dealer deck size not correct");
			count++;
			theDealer.dealersHand = theDealer.dealHand();
			theDealer.dealersHand.clear();
		}
	}

	//Test the dealer constructor and make sure card count is correct after dealing out cards each time
	@Test
	void testDealerConstructorCardCount() {
		Dealer theDealer = new Dealer();
		theDealer.dealersHand = theDealer.dealHand();
		assertEquals(52-3, theDealer.theDeck.size(), "Dealer deck size not correct");
		assertEquals(3, theDealer.dealersHand.size(), "Dealer cards size not correct");
	}

	//Test the dealer dealing the cards to players
	@Test
	void testDealingCards() {
		Dealer theDealer = new Dealer();
		Player player1 = new Player();
		player1.hand = theDealer.dealHand();
		assertEquals(3, player1.hand.size(), "Player's number of cards not correct");
		assertEquals(52-3, theDealer.theDeck.size(), "Deck size incorrect after dealing out cards");
	}

	//Test dealing cards to multiple players
	@Test
	void testDealingMultiplePlayers() {
		Dealer theDealer = new Dealer();
		Player player1 = new Player();
		Player player2 = new Player();
		theDealer.dealersHand = theDealer.dealHand();
		player1.hand = theDealer.dealHand();
		player2.hand = theDealer.dealHand();
		assertEquals(3, player1.hand.size(), "Player 1's number of cards not correct");
		assertEquals(3, player2.hand.size(), "Player 2's number of cards not correct");
		assertEquals(52-3*3, theDealer.theDeck.size(), "Deck size incorrect after dealing out cards");

		theDealer.dealersHand.clear();
		assertEquals(0, theDealer.dealersHand.size(), "Dealer's number of cards not correct");
		theDealer.dealersHand = theDealer.dealHand();
		assertEquals(3, theDealer.dealersHand.size(), "Dealer's number of cards not correct");
		player1.hand.clear();
		assertEquals(0, player1.hand.size(), "Player 1's number of cards not correct");
		player1.hand = theDealer.dealHand();
		player2.hand.clear();
		assertEquals(0, player2.hand.size(), "Player 2's number of cards not correct");
		player2.hand = theDealer.dealHand();
		assertEquals(3, player1.hand.size(), "Player 1's number of cards not correct");
		assertEquals(3, player2.hand.size(), "Player 2's number of cards not correct");
		assertEquals(52-3*3*2, theDealer.theDeck.size(), "Deck size incorrect after dealing out cards");
	}

	//Test evalHand - high card
	@Test
	void testHighCard() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('C', 2);
		hand.add(card1);
		Card card2 = new Card('D', 5);
		hand.add(card2);
		Card card3 = new Card('S', 10);
		hand.add(card3);
		assertEquals(0,ThreeCardLogic.evalHand(hand), "High card eval not correct");
	}

	//Test evalHand - straight flush
	@Test
	void testStraightFlush() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('C', 6);
		hand.add(card1);
		Card card2 = new Card('C', 8);
		hand.add(card2);
		Card card3 = new Card('C', 7);
		hand.add(card3);
		assertEquals(1,ThreeCardLogic.evalHand(hand), "Straight flush eval not correct");
	}

	//Test evalHand - three of a kind
	@Test
	void testThreeOfAKind() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('C', 13);
		hand.add(card1);
		Card card2 = new Card('D', 13);
		hand.add(card2);
		Card card3 = new Card('H', 13);
		hand.add(card3);
		assertEquals(2,ThreeCardLogic.evalHand(hand), "Three of a kind eval not correct");
	}

	//Test evalHand - straight
	@Test
	void testStraight() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('S', 9);
		hand.add(card1);
		Card card2 = new Card('D', 8);
		hand.add(card2);
		Card card3 = new Card('H', 10);
		hand.add(card3);
		assertEquals(3,ThreeCardLogic.evalHand(hand), "Straight eval not correct");
	}

	//Test evalHand - flush
	@Test
	void testFlush() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('S', 9);
		hand.add(card1);
		Card card2 = new Card('S', 12);
		hand.add(card2);
		Card card3 = new Card('S', 10);
		hand.add(card3);
		assertEquals(4,ThreeCardLogic.evalHand(hand), "Flush eval not correct");
	}

	//Test evalHand - pair
	@Test
	void testPair() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('S', 9);
		hand.add(card1);
		Card card2 = new Card('H', 9);
		hand.add(card2);
		Card card3 = new Card('S', 10);
		hand.add(card3);
		assertEquals(5,ThreeCardLogic.evalHand(hand), "Pair eval not correct");
	}

	//Test evalPPWinnings - does not have at least pair of 2s
	@Test
	void testEvalPPWinningsNotQual() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('S', 9);
		hand.add(card1);
		Card card2 = new Card('H', 2);
		hand.add(card2);
		Card card3 = new Card('S', 10);
		hand.add(card3);
		int bet = 100;
		assertEquals(0,ThreeCardLogic.evalPPWinnings(hand, bet), "Eval pair for no pairs not correct");
	}

	//Test evalPPWinnings - straight flush
	@Test
	void testEvalPPWinningsStraightFlush() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('H', 12);
		hand.add(card1);
		Card card2 = new Card('H', 11);
		hand.add(card2);
		Card card3 = new Card('H', 10);
		hand.add(card3);
		int bet = 50;
		assertEquals(50*40+50,ThreeCardLogic.evalPPWinnings(hand, bet), "Eval pair for straight flush not correct");
	}

	//Test evalPPWinnings - three of a kind
	@Test
	void testEvalPPWinningsThreeOfAKind() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('H', 9);
		hand.add(card1);
		Card card2 = new Card('C', 9);
		hand.add(card2);
		Card card3 = new Card('S', 9);
		hand.add(card3);
		int bet = 20;
		assertEquals(20*30+20,ThreeCardLogic.evalPPWinnings(hand, bet), "Eval pair for three of a kind not correct");
	}

	//Test evalPPWinnings - straight
	@Test
	void testEvalPPWinningsStraight() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('H', 8);
		hand.add(card1);
		Card card2 = new Card('C', 9);
		hand.add(card2);
		Card card3 = new Card('S', 7);
		hand.add(card3);
		int bet = 300;
		assertEquals(300*6+300,ThreeCardLogic.evalPPWinnings(hand, bet), "Eval pair for straight not correct");
	}

	//Test evalPPWinnings - flush
	@Test
	void testEvalPPWinningsFlush() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('C', 12);
		hand.add(card1);
		Card card2 = new Card('C', 13);
		hand.add(card2);
		Card card3 = new Card('C', 10);
		hand.add(card3);
		int bet = 10;
		assertEquals(10*3+10,ThreeCardLogic.evalPPWinnings(hand, bet), "Eval pair for flush not correct");
	}

	//Test evalPPWinnings - pair
	@Test
	void testEvalPPWinningsPair() {
		ArrayList<Card> hand = new ArrayList<>();
		Card card1 = new Card('H', 5);
		hand.add(card1);
		Card card2 = new Card('C', 8);
		hand.add(card2);
		Card card3 = new Card('C', 5);
		hand.add(card3);
		int bet = 35;
		assertEquals(35+35,ThreeCardLogic.evalPPWinnings(hand, bet), "Eval pair for pair not correct");
	}

	//Test Compare hand - straight flush tie
	@Test
	void testCompareHandStraightFlush() {
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card1 = new Card('H', 14);
		dealerHand.add(card1);
		Card card2 = new Card('H', 12);
		dealerHand.add(card2);
		Card card3 = new Card('H', 13);
		dealerHand.add(card3);

		ArrayList<Card> playerHand = new ArrayList<>();
		Card card4 = new Card('S', 13);
		playerHand.add(card4);
		Card card5 = new Card('S', 14);
		playerHand.add(card5);
		Card card6 = new Card('S', 12);
		playerHand.add(card6);
		assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight flush tie incorrect.");
	}

	//Test Compare hand - straight tie
	@Test
	void testCompareHandStraight() {
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card1 = new Card('H', 7);
		dealerHand.add(card1);
		Card card2 = new Card('S', 8);
		dealerHand.add(card2);
		Card card3 = new Card('C', 6);
		dealerHand.add(card3);

		ArrayList<Card> playerHand = new ArrayList<>();
		Card card4 = new Card('C', 8);
		playerHand.add(card4);
		Card card5 = new Card('C', 7);
		playerHand.add(card5);
		Card card6 = new Card('H', 6);
		playerHand.add(card6);
		assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight tie incorrect.");
	}

	//Test Compare hand - flush tie
	@Test
	void testCompareHandFlush() {
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card1 = new Card('H', 7);
		dealerHand.add(card1);
		Card card2 = new Card('S', 8);
		dealerHand.add(card2);
		Card card3 = new Card('C', 6);
		dealerHand.add(card3);

		ArrayList<Card> playerHand = new ArrayList<>();
		Card card4 = new Card('C', 8);
		playerHand.add(card4);
		Card card5 = new Card('C', 7);
		playerHand.add(card5);
		Card card6 = new Card('H', 6);
		playerHand.add(card6);
		assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight tie incorrect.");
	}

	//Test Compare hand - pair tie
	@Test
	void testCompareHandPair() {
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card1 = new Card('H', 7);
		dealerHand.add(card1);
		Card card2 = new Card('S', 7);
		dealerHand.add(card2);
		Card card3 = new Card('C', 6);
		dealerHand.add(card3);

		ArrayList<Card> playerHand = new ArrayList<>();
		Card card4 = new Card('C', 7);
		playerHand.add(card4);
		Card card5 = new Card('D', 7);
		playerHand.add(card5);
		Card card6 = new Card('H', 6);
		playerHand.add(card6);
		assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for pair tie incorrect.");
	}

	//Test Compare hand - straight flush dealer win
	@Test
	void testCompareHandDealerStraightFlush() {
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card1 = new Card('H', 14);
		dealerHand.add(card1);
		Card card2 = new Card('H', 12);
		dealerHand.add(card2);
		Card card3 = new Card('H', 13);
		dealerHand.add(card3);

		//Against player lower straight flush
		ArrayList<Card> playerHand = new ArrayList<>();
		Card card4 = new Card('S', 13);
		playerHand.add(card4);
		Card card5 = new Card('S', 11);
		playerHand.add(card5);
		Card card6 = new Card('S', 12);
		playerHand.add(card6);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight flush against straight flush win incorrect.");

		//Against player three of a kind
		playerHand.clear();
		Card card7 = new Card('D', 12);
		playerHand.add(card7);
		Card card8 = new Card('H', 12);
		playerHand.add(card8);
		Card card9 = new Card('C', 12);
		playerHand.add(card9);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight flush against three of a kind win incorrect.");

		//Against player straight
		playerHand.clear();
		Card card10 = new Card('D', 8);
		playerHand.add(card10);
		Card card11 = new Card('H', 10);
		playerHand.add(card11);
		Card card12 = new Card('C', 9);
		playerHand.add(card12);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight flush against straight win incorrect.");

		//Against player flush
		playerHand.clear();
		Card card13 = new Card('D', 3);
		playerHand.add(card13);
		Card card14 = new Card('D', 7);
		playerHand.add(card14);
		Card card15 = new Card('D', 9);
		playerHand.add(card15);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight flush against flush win incorrect.");

		//Against player pair
		playerHand.clear();
		Card card16 = new Card('D', 14);
		playerHand.add(card16);
		Card card17 = new Card('S', 8);
		playerHand.add(card17);
		Card card18 = new Card('C', 14);
		playerHand.add(card18);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight flush against pair win incorrect.");

		//Against player high card
		playerHand.clear();
		Card card19 = new Card('D', 14);
		playerHand.add(card19);
		Card card20 = new Card('S', 8);
		playerHand.add(card20);
		Card card21 = new Card('C', 10);
		playerHand.add(card21);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight flush against high card win incorrect.");
	}

	//Test Compare hand - three of a kind player win
	@Test
	void testCompareHandPlayerThreeOfAKind() {
		ArrayList<Card> playerHand = new ArrayList<>();
		Card card1 = new Card('S', 13);
		playerHand.add(card1);
		Card card2 = new Card('D', 13);
		playerHand.add(card2);
		Card card3 = new Card('C', 13);
		playerHand.add(card3);

		//Player win against high card
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card4 = new Card('S', 14);
		dealerHand.add(card4);
		Card card5 = new Card('D', 5);
		dealerHand.add(card5);
		Card card6 = new Card('C', 7);
		dealerHand.add(card6);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for 3 of a kind against high card win incorrect.");

		//Player win against a straight
		dealerHand.clear();
		Card card7 = new Card('S', 6);
		dealerHand.add(card7);
		Card card8 = new Card('D', 5);
		dealerHand.add(card8);
		Card card9 = new Card('C', 7);
		dealerHand.add(card9);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for 3 of a kind against straight win incorrect.");

		//Player win against a flush
		dealerHand.clear();
		Card card10 = new Card('S', 6);
		dealerHand.add(card10);
		Card card11 = new Card('S', 14);
		dealerHand.add(card11);
		Card card12 = new Card('S', 3);
		dealerHand.add(card12);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for 3 of a kind against flush win incorrect.");

		//Player win against a pair
		dealerHand.clear();
		Card card13 = new Card('S', 6);
		dealerHand.add(card13);
		Card card14 = new Card('D', 6);
		dealerHand.add(card14);
		Card card15 = new Card('C', 7);
		dealerHand.add(card15);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for 3 of a kind against pair win incorrect.");

		//Player win against lower three of a kind
		dealerHand.clear();
		Card card16 = new Card('S', 11);
		dealerHand.add(card16);
		Card card17 = new Card('D', 11);
		dealerHand.add(card17);
		Card card18 = new Card('C', 11);
		dealerHand.add(card18);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for 3 of a kind against lower three of a kind win incorrect.");
	}

	//Test Compare hand - straight dealer win
	@Test
	void testCompareHandDealerStraight() {
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card1 = new Card('C', 8);
		dealerHand.add(card1);
		Card card2 = new Card('H', 10);
		dealerHand.add(card2);
		Card card3 = new Card('S', 9);
		dealerHand.add(card3);

		//Against player lower straight
		ArrayList<Card> playerHand = new ArrayList<>();
		Card card4 = new Card('S', 8);
		playerHand.add(card4);
		Card card5 = new Card('C', 9);
		playerHand.add(card5);
		Card card6 = new Card('H', 7);
		playerHand.add(card6);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight against straight win incorrect.");

		//Against player flush
		playerHand.clear();
		Card card7 = new Card('S', 4);
		playerHand.add(card7);
		Card card8 = new Card('S', 5);
		playerHand.add(card8);
		Card card9 = new Card('S', 12);
		playerHand.add(card9);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight against flush win incorrect.");

		//Against player pair
		playerHand.clear();
		Card card10 = new Card('C', 12);
		playerHand.add(card10);
		Card card11 = new Card('S', 5);
		playerHand.add(card11);
		Card card12 = new Card('S', 12);
		playerHand.add(card12);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight against pair win incorrect.");

		//Against player high card
		playerHand.clear();
		Card card13 = new Card('C', 12);
		playerHand.add(card13);
		Card card14 = new Card('S', 5);
		playerHand.add(card14);
		Card card15 = new Card('D', 4);
		playerHand.add(card15);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for straight against pair win incorrect.");
	}

	//Test Compare hand - flush player win
	@Test
	void testCompareHandPlayerFlush() {
		ArrayList<Card> playerHand = new ArrayList<>();
		Card card4 = new Card('S', 8);
		playerHand.add(card4);
		Card card5 = new Card('S', 11);
		playerHand.add(card5);
		Card card6 = new Card('S', 7);
		playerHand.add(card6);

		//Against dealer lower flush, high card not same
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card1 = new Card('C', 8);
		dealerHand.add(card1);
		Card card2 = new Card('C', 10);
		dealerHand.add(card2);
		Card card3 = new Card('C', 4);
		dealerHand.add(card3);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for flush against lower flush win incorrect.");

		//Against dealer lower flush, high card same
		dealerHand.clear();
		Card card7 = new Card('C', 11);
		dealerHand.add(card7);
		Card card8 = new Card('C', 6);
		dealerHand.add(card8);
		Card card9 = new Card('C', 4);
		dealerHand.add(card9);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for flush against lower flush win incorrect.");

		//Against dealer lower flush, 2 high cards same
		dealerHand.clear();
		Card card10 = new Card('C', 11);
		dealerHand.add(card10);
		Card card11 = new Card('C', 5);
		dealerHand.add(card11);
		Card card12 = new Card('C', 8);
		dealerHand.add(card12);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for flush against lower flush win incorrect.");

		//Against dealer pair
		dealerHand.clear();
		Card card13 = new Card('C', 11);
		dealerHand.add(card13);
		Card card14 = new Card('D', 11);
		dealerHand.add(card14);
		Card card15 = new Card('C', 8);
		dealerHand.add(card15);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for flush against pair win incorrect.");

		//Against dealer high card
		dealerHand.clear();
		Card card16 = new Card('C', 12);
		dealerHand.add(card16);
		Card card17 = new Card('D', 10);
		dealerHand.add(card17);
		Card card18 = new Card('C', 8);
		dealerHand.add(card18);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for flush against high card win incorrect.");
	}

	//Test Compare hand - pair dealer win
	@Test
	void testCompareHandDealerPair() {
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card1 = new Card('C', 8);
		dealerHand.add(card1);
		Card card2 = new Card('D', 8);
		dealerHand.add(card2);
		Card card3 = new Card('C', 4);
		dealerHand.add(card3);

		//Against player lower pair
		ArrayList<Card> playerHand = new ArrayList<>();
		Card card4 = new Card('S', 8);
		playerHand.add(card4);
		Card card5 = new Card('D', 7);
		playerHand.add(card5);
		Card card6 = new Card('S', 7);
		playerHand.add(card6);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for pair against lower pair win incorrect.");

		//Against player same pair but lower 3rd card
		playerHand.clear();
		Card card7 = new Card('S', 8);
		playerHand.add(card7);
		Card card8 = new Card('D', 3);
		playerHand.add(card8);
		Card card9 = new Card('S', 8);
		playerHand.add(card9);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for pair against lower pair win incorrect.");

		//Against player high card
		playerHand.clear();
		Card card10 = new Card('S', 8);
		playerHand.add(card10);
		Card card11 = new Card('D', 3);
		playerHand.add(card11);
		Card card12 = new Card('S', 12);
		playerHand.add(card12);
		assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for pair against high card win incorrect.");
	}

	//Test Compare hand - high card player win
	@Test
	void testCompareHandPlayerHighCard() {
		ArrayList<Card> playerHand = new ArrayList<>();
		Card card4 = new Card('S', 8);
		playerHand.add(card4);
		Card card5 = new Card('D', 7);
		playerHand.add(card5);
		Card card6 = new Card('S', 14);
		playerHand.add(card6);

		//Against dealer lower high card
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card1 = new Card('C', 13);
		dealerHand.add(card1);
		Card card2 = new Card('D', 8);
		dealerHand.add(card2);
		Card card3 = new Card('C', 4);
		dealerHand.add(card3);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for high card against lower high card win incorrect.");

		//Against dealer lower high card, first card same
		dealerHand.clear();
		Card card7 = new Card('C', 14);
		dealerHand.add(card7);
		Card card8 = new Card('D', 7);
		dealerHand.add(card8);
		Card card9 = new Card('C', 4);
		dealerHand.add(card9);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for high card against lower high card win incorrect.");

		//Against dealer lower high card, first card same
		dealerHand.clear();
		Card card10 = new Card('C', 14);
		dealerHand.add(card10);
		Card card11 = new Card('D', 8);
		dealerHand.add(card11);
		Card card12 = new Card('C', 4);
		dealerHand.add(card12);
		assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for high card against lower high card win incorrect.");
	}

	//Dealer does not have Queen or higher
	@Test
	void testDealerQueenOrHigher() {
		ArrayList<Card> playerHand = new ArrayList<>();
		Card card4 = new Card('S', 8);
		playerHand.add(card4);
		Card card5 = new Card('D', 7);
		playerHand.add(card5);
		Card card6 = new Card('S', 5);
		playerHand.add(card6);

		//Against dealer lower high card
		ArrayList<Card> dealerHand = new ArrayList<>();
		Card card1 = new Card('C', 10);
		dealerHand.add(card1);
		Card card2 = new Card('D', 8);
		dealerHand.add(card2);
		Card card3 = new Card('C', 4);
		dealerHand.add(card3);
		assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), "Hands comparison for dealer no Queen or higher incorrect.");
	}
}
