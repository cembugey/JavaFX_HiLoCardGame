
public class Card {

	// Rank and suit of the card
	private int suit = 0;
	private int rank = 0;
	
	
	// Default constructor
	public Card() {
		
	}//Card()
	
	// 2-argument constructor
	public Card(int rank, int suit){
		this.rank = rank;
		this.suit = suit;
		
	}//Card()
	
    // A method that returns true if the rank of a given card is lower than that of our card
	public boolean isLower(Card c){
		boolean rankIsLess = false;
		
		if(rank < c.getRank()){
			rankIsLess = true;
		}//if
		
		return rankIsLess;
		
	}// isLower()
	
	// A method that returns true if the rank of a given card is higher than that of our card
	public boolean isHigher(Card c){
		boolean rankIsGreater = false;
	
		if(rank > c.getRank()){
			rankIsGreater = true;
		}//if
		
		return rankIsGreater;
		
	}// isHigher()
	
	// A method that returns true if the rank of a given card is equal to that of our card
	public boolean isEqual(Card c){
		boolean rankIsEqualTo = false;
		
		if(rank == c.getRank()){
			rankIsEqualTo = true;
		}//if
		else{
			rankIsEqualTo = false;
		}//else
			
		return rankIsEqualTo;
		
	}//isEqual()
	
	@Override  // A method to get String representation of the card
	public String toString(){
		
		String cardSuit = "";
		String cardRank = "";
		String cardString;
		
		int cs = getSuit();
		int cr = getRank();
		
		
		switch (cs){
		case 0:
			cardSuit = "hearts";
			break;
		case 1:
			cardSuit = "diamonds";
			break;
		case 2:
			cardSuit = "clubs";
			break;
		case 3:
			cardSuit = "spades";
			break;
		default:
			cardSuit = "n/a";
			break;
			
		}//switch suit
		
		
		switch(cr){
		case 1:
			cardRank = "ace";
			break;
		case 2:
			cardRank = "2";
			break;
		case 3:
			cardRank = "3";
			break;
		case 4:
			cardRank = "4";
			break;
		case 5:
			cardRank = "5";
			break;
		case 6:
			cardRank = "6";
			break;
		case 7:
			cardRank = "7";
			break;
		case 8:
			cardRank = "8";
			break;
		case 9:
			cardRank = "9";
			break;
		case 10:
			cardRank = "10";
			break;
		case 11:
			cardRank = "jack";
			break;
		case 12:
			cardRank = "queen";
			break;
		case 13:
			cardRank = "king";
			break;
		default:
			cardRank = "n/a";
			break;
		}//switch rank
		
		cardString = cardRank + "_of_" + cardSuit;
				
		return cardString;
	}//toString()
	
	// Getter method for the rank attribute
	public int getRank(){
		return rank;
	}//getRank()
	
	// Getter method for the suit attribute
	public int getSuit(){
		return suit;		
	}//getSuit()
	
}//class
