package game.utils.calculator;

import java.util.ArrayList;
import java.util.HashMap;

import game.deck.Card;
import game.deck.CardType;
import game.deck.CardValue;
import game.utils.CardCombination;
import game.utils.HandRanking;
import main.Constants;

public class FlushCalculator {
	public static CardCombination checkForFlush(Card cards[]) throws Exception {
		CardType flushType = getCardTypeForFlush(cards);	
		if (flushType == null) {
			return null;
		}
		
		ArrayList<CardValue> flushCardValues = getAllCardValuesOfFlushType(cards, flushType);
		flushCardValues.sort( (a,b) -> { return -1 * a.compareTo(b); } );
		CardValue topValues[] = new CardValue[Constants.NUMBER_COMMUNITY_CARDS];
		for (int i = 0; i < 5; i++) {
			topValues[i] = flushCardValues.get(i);
		}
		
		return new CardCombination(HandRanking.FLUSH, topValues);
	}
	
	public static CardType getCardTypeForFlush(Card[] cards) {
		HashMap<CardType, Integer> typeCounter = new HashMap<>();
		CardType flushType = null;
		
		for (int i = 0; i < cards.length; i++) {
			CardType type = cards[i].getType();
			int newCount = 1;
			if (typeCounter.containsKey(type)) {
				newCount = typeCounter.get(type)+1;
			}
			typeCounter.put(type, newCount);
			
			if (newCount == 5) {
				flushType = type;
				break;
			}
		}
		
		return flushType;
	}
	
	public static ArrayList<CardValue> getAllCardValuesOfFlushType(Card[] cards, CardType flushType) {
		ArrayList<CardValue> flushCardValues = new ArrayList<>();
		for (Card card : cards) {
			if (card.getType() == flushType) {
				flushCardValues.add(card.getValue());
			}
		}
		return flushCardValues;
	}
}
