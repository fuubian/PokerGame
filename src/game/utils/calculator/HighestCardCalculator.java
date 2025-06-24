package game.utils.calculator;

import java.util.HashMap;

import game.deck.CardValue;
import game.utils.CardCombination;
import game.utils.HandRanking;

public class HighestCardCalculator {
	public static CardCombination checkForHighestCard(HashMap<CardValue, Integer> valueCounter) throws Exception {
		CardValue topValues[] = PairCalculator.getTopValuesPairTripleQuads(valueCounter, 0);
		return new CardCombination(HandRanking.HIGH_CARD, topValues);
	}
}
