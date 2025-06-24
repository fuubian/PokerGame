package game.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	
	private List<Card> cards;
	
	public Deck() throws Exception {
		this.cards = new ArrayList<>();
		
		for (CardType type : CardType.values()) {
			for (CardValue value: CardValue.values()) {
				this.cards.add(new Card(type, value));
			}
		}
		
		Collections.shuffle(this.cards);
	}
	
	public Card drawCard() {
		return this.cards.removeFirst();
	}
}
