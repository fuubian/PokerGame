package game.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import game.deck.Card;
import game.deck.CardType;
import main.Constants;

/*
 * Ranks:
 * High Card (0)
 * One Pair (1)
 * Two Pair (2)
 * Triple (3)
 * Straight (4)
 * Flush (5)
 * Full House (6)
 * Quads (7)
 * Straight Flush (8)
 * Royal Flush (9)
 */

public class CardCombinationCalculator {
	
	public static void calculateCombinationValue(Card handCards[], Card communityCards[]) throws Exception {
		if (handCards.length != Constants.NUMBER_CARDS_ON_HAND || communityCards.length != Constants.NUMBER_COMMUNITY_CARDS) {
			throw new Exception("Unexpected number of cards received.\nReceived hand cards: " + handCards.length + 
					"\nReceived community cards: " + communityCards.length);
		}
		
		
	}
	
	/*
	 * This method checks whether a pair, two pairs, a triplet, a full house, or a quad occurs and returns the respective hand combination.
	 * If none of these combinations occur, null is returned.
	 */
	private CardCombination checkForPairingsOverall(Card cards[]) {
		HashMap<Integer, Integer> valueCounter = new HashMap<>();
		int numberOfPairs = 0;
		
		// Store occurrences in HashMap
		for (int i = 0; i < cards.length; i++) {
			int cardValue = cards[i].getValue();
			
			if (valueCounter.containsKey(cardValue)) {
				valueCounter.put(cardValue, valueCounter.get(cardValue)+1);
			}
		}
		
		// Check for Full House
		// TODO: Optimize that 2 triplets may be part of the cards
		if (valueCounter.containsValue(3) && valueCounter.containsValue(2)) {
			return this.checkForFullHouse(valueCounter);
		}
		
		// Check for Four Of A Kind and Three Of A Kind
		for (int i = 4; i >= 3; i--) {
			if (valueCounter.containsValue(i)) {
				try {
					return this.checkForTripleQuads(valueCounter, i);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		// Check for pairs
		if (valueCounter.containsValue(2)) {
			return this.checkForPairs(valueCounter);
		}
		
		return null;
	}
	
	private CardCombination checkForPairs(HashMap<Integer, Integer> valueCounter) {
		return null;
	}
	
	private CardCombination checkForTripleQuads(HashMap<Integer, Integer> valueCounter, int checkedCount) throws Exception {
		if (checkedCount != 3 && checkedCount != 4) {
			throw new Exception("An unexpected value was used to examine whether a tripple or a quad occur.\nExpected value: 3 or 4\n Received value: " + checkedCount);
		}
		
		if (valueCounter.containsValue(checkedCount)) {
			int topValues[] = new int[Constants.NUMBER_COMMUNITY_CARDS];
			
			for (int cardValue : valueCounter.keySet()) {
				if (valueCounter.get(cardValue) == checkedCount) {
					for (int i = 0; i < checkedCount; i++) {
						topValues[i] = cardValue;
					}
					
					valueCounter.remove(cardValue);
					break;
				}
			}
			
			// Sorting converted HashSet to find highest card
			ArrayList<Integer> sortedValues = new ArrayList<Integer>(valueCounter.keySet());
			sortedValues.sort( (a,b) -> { return -1 * a.compareTo(b); } );
			
			// Add highest cards to topValues
			for (int i = 0; i+3 <= checkedCount; i++) {
				topValues[checkedCount+i] = sortedValues.get(i);
			}
			
			HandRanking ranking = (checkedCount == 4) ? HandRanking.QUADS : HandRanking.TRIPLE;
			return new CardCombination(ranking, topValues);
		}
		
		return null;
	}
	
	private CardCombination checkForFullHouse(HashMap<Integer, Integer> valueCounter) {
		if (valueCounter.containsValue(3) && valueCounter.containsValue(2)) {
			if 
		}
		
		return null;
	}
	
	private boolean checkForStraight(Card cards[]) {
		// Create array of card values
		int cardValues[] = new int[cards.length];
		for (int i = 0; i < cards.length; i++) {
			cardValues[i] = cards[i].getValue();
		}
		
		// Sort array
		Arrays.sort(cardValues);
		
		// Check for 5-card long sequence
		int sequenceCounter = 1;
		for (int i = 1; i < cardValues.length; i++) {
			if (cardValues[i] == cardValues[i-1]+1) {
				sequenceCounter += 1;
				
				if (sequenceCounter == 5) {
					return true;
				}
			} else {
				sequenceCounter = 1;
			}
		}
		
		return false;
	}
	
	private boolean checkForFlush(Card cards[]) {
		HashMap<CardType, Integer> typeCounter = new HashMap<>();
		
		for (int i = 0; i < cards.length; i++) {
			CardType type = cards[i].getType();
			int newCount = typeCounter.get(type)+1;
			typeCounter.put(type, newCount);
			
			if (newCount == 5) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkForRoyalFlush(Card cards[]) {
		int[] valuesRequired = { 14, 13, 12, 11, 10 };
		CardType[] typesAllowed = { CardType.HEARTS, CardType.SPARROWS, CardType.CLUBS, CardType.SPADES };
		
		for (int value : valuesRequired) {
			boolean valueFound = false;
			
			for (int i = 0; i < cards.length; i++) {
				if (cards[i].getValue() == value) {
					valueFound = true;
				}
			}
		}
		
		return true;
	}

}
