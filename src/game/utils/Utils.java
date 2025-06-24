package game.utils;

import java.util.ArrayList;
import java.util.Set;

import game.deck.CardValue;

public class Utils {
	public static ArrayList<CardValue> getSortedArrayList(Set<CardValue> cardValues) {
		ArrayList<CardValue> sortedValues = new ArrayList<>(cardValues);
		sortedValues.sort( (a,b) -> { return -1 * a.compareTo(b); } );
		
		return sortedValues;
	}
}
