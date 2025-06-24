package game.utils.calculator;

import java.util.ArrayList;
import java.util.Set;

import game.deck.CardValue;
import game.utils.CardCombination;
import game.utils.HandRanking;
import game.utils.Utils;
import main.Constants;

public class StraightCalculator {

	public static CardCombination checkForStraight(Set<CardValue> values) throws Exception {
		ArrayList<CardValue> sortedValues = Utils.getSortedArrayList(values);	
		CardValue[] foundStraight = getStraightStreak(sortedValues);
		
		if (foundStraight == null) {
			return null;
		}
		
		return new CardCombination(HandRanking.STRAIGHT, foundStraight);
	}
	
	public static CardValue[] getStraightStreak(ArrayList<CardValue> sortedValues) {
		ArrayList<CardValue> currentStreak = new ArrayList<CardValue>();
		CardValue lastValue = sortedValues.get(0);
		CardValue currentValue = null;
		
		currentStreak.add(lastValue);		
		for (int i = 1; i < sortedValues.size(); i++) {
			currentValue = sortedValues.get(i);
			int difference = lastValue.getNumericValue() - currentValue.getNumericValue();
			
			if (difference == 1) {
				currentStreak.add(currentValue);
				lastValue = currentValue;
				
				if (currentStreak.size() == Constants.NUMBER_COMMUNITY_CARDS) {
					return (CardValue[]) currentStreak.toArray(new CardValue[Constants.NUMBER_COMMUNITY_CARDS]);
				}
			} else {
				currentStreak = new ArrayList<CardValue>();
				lastValue = sortedValues.get(i);
				currentStreak.add(lastValue);
			}
		}
		
		// Special case: Ace counting as One
		if (currentStreak.size() == 4 && currentStreak.get(3) == CardValue.TWO && sortedValues.get(0) == CardValue.ACE) {
			currentStreak.add(CardValue.ACE);
			return (CardValue[]) currentStreak.toArray(new CardValue[Constants.NUMBER_COMMUNITY_CARDS]);
		}
		
		return null;
	}
}
