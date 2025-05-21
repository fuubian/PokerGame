package game.deck;

public class Card {
	
	private CardType type;
	private int value;
	
	public Card(CardType type, int value) throws Exception {
		if (value < 2 && value > 14)
			throw new Exception("Unexpected value received in Card. Value must be between 2 and 14. Value received: " + value);
			
		this.type = type;
		this.value = value;
	}
	
	public CardType getType() { return this.type; }
	
	public int getValue() {	return this.value; }
	
	@Override
    public String toString() {
		String valueString;
		switch (this.value) {
		case 11:
			valueString = "Jack";
			break;
		case 12:
			valueString = "Queen";
			break;
		case 13:
			valueString = "King";
			break;
		case 14:
			valueString = "Ace";
			break;
		default:
			valueString = "" + this.value;
		}
		
		
        return this.type + "-" + valueString;
    }
}
