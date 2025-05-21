package game.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	
	final int VALUE_RANGE_MIN = 2;
	final int VALUE_RANGE_MAX = 14;
	
	private List<Card> cards;
	
	public Deck() throws Exception {
		this.cards = new ArrayList<>();
		
		for (CardType type : CardType.values()) {
			for (int i = VALUE_RANGE_MIN; i <= VALUE_RANGE_MAX; i++) {
				this.cards.add(new Card(type, i));
			}
		}
		
		Collections.shuffle(this.cards);
	}
	
	public Card drawCard() {
		return this.cards.removeFirst();
	}
}
