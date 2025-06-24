package game.utils.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import game.deck.CardValue;
import game.utils.CardCombination;
import game.utils.HandRanking;
import game.utils.Utils;
import main.Constants;

public class PairCalculator {
	public static CardCombination checkForPairTripleQuads(HashMap<CardValue, Integer> valueCounter, int checkedCount) throws Exception {
		if (checkedCount != 2 && checkedCount != 3 && checkedCount != 4) {
			throw new Exception("An unexpected value was used to examine whether a pair, tripple or quad occur.\nExpected value: 2, 3 or 4\n Received value: " + checkedCount);
		}
		
		if (!valueCounter.containsValue(checkedCount)) {
			return null;
		}
		
		CardValue[] topValues = getTopValuesPairTripleQuads(valueCounter, checkedCount);
		HandRanking ranking = getHandRankingPairTripleQuads(checkedCount);
		return new CardCombination(ranking, topValues);
	}

	public static CardCombination checkForTwoPair(HashMap<CardValue, Integer> valueCounter) throws Exception {
		HashSet<CardValue> foundPairs = getAllPairs(valueCounter);
		if (foundPairs.size() < 2) {
			return null;
		}
		
		ArrayList<CardValue> sortedPairs = Utils.getSortedArrayList(foundPairs);
		
		// Remove top two pairs from valueCounter
		valueCounter.remove(sortedPairs.get(0));
		if (sortedPairs.size() > 1) {
			valueCounter.remove(sortedPairs.get(1));
		}
		
		ArrayList<CardValue> sortedValues = Utils.getSortedArrayList(valueCounter.keySet());
		
		CardValue[] topValues = getTopValuesTwoPair(sortedPairs, sortedValues);
		return new CardCombination(HandRanking.TWO_PAIR, topValues);
	}
	
	public static CardValue[] getTopValuesPairTripleQuads(HashMap<CardValue, Integer> valueCounter, int checkedCount) {
		CardValue topValues[] = new CardValue[Constants.NUMBER_COMMUNITY_CARDS];
		CardValue matchingValue = null;
		
		for (CardValue cardValue : valueCounter.keySet()) {
			if (valueCounter.get(cardValue) == checkedCount) {
				if (matchingValue == null || cardValue.compareTo(matchingValue) > 0) {
					matchingValue = cardValue;
				}
			}
		}
		
		Arrays.fill(topValues, 0, checkedCount, matchingValue);
		valueCounter.remove(matchingValue);
		
		// Sorting converted HashSet to find highest card
		ArrayList<CardValue> sortedValues = Utils.getSortedArrayList(valueCounter.keySet());
		
		// Add highest cards to topValues
		for (int i = checkedCount; i < Constants.NUMBER_COMMUNITY_CARDS; i++) {
			topValues[i] = sortedValues.get(i-checkedCount);
		}
		return topValues;
	}
	
	private static CardValue[] getTopValuesTwoPair(ArrayList<CardValue> sortedPairs,
			ArrayList<CardValue> sortedValues) {
		CardValue topValues[] = new CardValue[Constants.NUMBER_COMMUNITY_CARDS];
		Arrays.fill(topValues, 0, 2, sortedPairs.get(0));
		Arrays.fill(topValues, 2, 4, sortedPairs.get(1));
	
		sortedValues.remove(sortedPairs.get(0));
		sortedValues.remove(sortedPairs.get(1));
		topValues[4] = sortedValues.get(0);
		
		return topValues;
	}
	
	private static HandRanking getHandRankingPairTripleQuads(int checkedCount) {
		HandRanking ranking = null;
		if (checkedCount == 4) {
			ranking = HandRanking.QUADS;
		} else if (checkedCount == 3) {
			ranking = HandRanking.TRIPLE;
		} else if (checkedCount == 2) {
			ranking = HandRanking.ONE_PAIR;
		}
		return ranking;
	}
	
	private static HashSet<CardValue> getAllPairs(HashMap<CardValue, Integer> valueCounter) {
		HashSet<CardValue> foundPairs = new HashSet<>();
		
		// Identify amount of pairs and card value
		for (CardValue key : valueCounter.keySet()) {
			if (valueCounter.get(key) == 2) {
				foundPairs.add(key);
			}
		}
		return foundPairs;
	}
}
