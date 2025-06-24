package game.utils.calculator;

import java.util.ArrayList;

import game.deck.Card;
import game.deck.CardType;
import game.deck.CardValue;
import game.utils.CardCombination;
import game.utils.HandRanking;

public class RoyalStraightFlushCalculator {
	public static CardCombination checkForRoyalStraightFlush(Card cards[]) throws Exception {
		CardType flushType = FlushCalculator.getCardTypeForFlush(cards);
		if (flushType == null) {
			return null;
		}
		
		ArrayList<CardValue> flushCardValues = FlushCalculator.getAllCardValuesOfFlushType(cards, flushType);
		flushCardValues.sort( (a,b) -> { return -1 * a.compareTo(b); } );
		
		CardValue[] foundStraight = StraightCalculator.getStraightStreak(flushCardValues);
		if (foundStraight == null) {
			return null;
		}
		
		HandRanking ranking = HandRanking.STRAIGHT_FLUSH;
		if (foundStraight[0] == CardValue.ACE) {
			ranking = HandRanking.ROYAL_FLUSH;
		}
		
		return new CardCombination(ranking, foundStraight);
	}
}
