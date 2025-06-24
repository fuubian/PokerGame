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

public class FullHouseCalculator {
	
	public static CardCombination checkForFullHouse(HashMap<CardValue, Integer> valueCounter) throws Exception {
		HashSet<CardValue> tripletValues = new HashSet<>();
		HashSet<CardValue> pairValues = new HashSet<>();
		
		for (CardValue value : valueCounter.keySet()) {
			if (valueCounter.get(value) == 3) {
				tripletValues.add(value);
			} else if (valueCounter.get(value) == 2) {
				pairValues.add(value);
			}
		}
		
		if ((tripletValues.size() >= 1 && pairValues.size() >= 1) ||tripletValues.size() >= 2) {
			return getTopValuesFullHouse(tripletValues, pairValues);
		}
		
		return null;
	}
	
	private static CardCombination getTopValuesFullHouse(HashSet<CardValue> tripletValues,
			HashSet<CardValue> pairValues) throws Exception {
		ArrayList<CardValue> sortedTriplets = Utils.getSortedArrayList(tripletValues);
		ArrayList<CardValue> sortedPairs = Utils.getSortedArrayList(pairValues);
		
		CardValue topValues[] = new CardValue[Constants.NUMBER_COMMUNITY_CARDS];
		Arrays.fill(topValues, 0, 3, sortedTriplets.get(0));
		
		CardValue higherValue = getSecondHighestValueFullHouse(sortedTriplets, sortedPairs);
		Arrays.fill(topValues, 3, 5, higherValue);
		
		return new CardCombination(HandRanking.FULL_HOUSE, topValues);
	}
	
	private static CardValue getSecondHighestValueFullHouse(ArrayList<CardValue> sortedTriplets,
			ArrayList<CardValue> sortedPairs) {
		CardValue higherValue = null;
		if (sortedTriplets.size() >= 2) {
			higherValue = sortedTriplets.get(1);;
		}
		if (sortedPairs.size() >= 1) {
			CardValue topPair = sortedPairs.get(0);
		    if (higherValue == null || topPair.compareTo(higherValue) > 0) {
		        higherValue = topPair;
		    }
		}
		return higherValue;
	}
}
