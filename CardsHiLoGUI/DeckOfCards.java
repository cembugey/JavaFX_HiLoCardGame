import java.util.Random;

//                CEM BUGEY 3014176  && ELVIS LESI 3011081  


public class DeckOfCards {
	
	// The next card on the deck
	private int nextCard = 0;
	
	private int[] suits;   // Array to store suit numbers
	private int[] ranks;   // Array to store rank numbers
	
	
	private Card[] deck;   // Array to store the cards
	
	public DeckOfCards() {
		
		// instantiate suits and ranks
		suits = new int[4];
		ranks = new int[13];
		
		// instantiate the deck
		deck = new Card[52];
		
		// Populate suits
		suits[0] = 0;  // Hearts
		suits[1] = 1;  // Diamonds
		suits[2] = 2;  // Clubs
		suits[3] = 3;  // Spades
		
		// i goes from 0 to 12. Ranks go from 1 to 13. Ace is 1.
		for(int i = 0; i <= 12; i++) {
			
			// Populate ranks from 1 to 13
			ranks[i] = i + 1;
		}
		
		// Populate the deck with cards
		int cardCount = 0;
		
		// There are 4 suits. Use s (suit) to count from 0 through 3
		for(int s = 0; s <= 3; s++) {
			
			// There are 12 ranks. Use r (ranks) to count from 0 through 12
			// Do this for each suit
			for(int r = 0; r <= 12; r++) {
				deck[cardCount] = new Card(ranks[r], suits[s]);
				cardCount++;
			}
		}
		
	}
	
	// A method to shuffle the deck
	public void shuffle() {
	
		// randomly swap each card with another
		Random rand = new Random();
		for(int i=0; i<=51; i++) {
			int randomNum = rand.nextInt(52);
			Card temp = deck[i];
			deck[i] = deck[randomNum];
			deck[randomNum] = temp;
		}
		// Reset the card index to the first card
		nextCard = 0;
	}
	
	// A method to pick the top card from the deck
		public Card dealTopCard() {
				
			Card card;
				
			// Check if deck is exhausted
			if(nextCard <= 51) {
				card = deck[nextCard];
				nextCard++;
			}
			else {
				// -1 is a flag to indicate an invalid card
				card = new Card(-1, -1);
			}
			return card;
		}
	
	// A method that returns true if there is no card left on the deck
	public boolean isEmpty() {
		boolean emptyFlag = false;
			
		if(nextCard > 51) {
			emptyFlag = true;
		}
		return emptyFlag;
	}
}
