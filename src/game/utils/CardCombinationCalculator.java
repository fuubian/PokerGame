package game.utils;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	private CardCombination checkForOnePair(Card cards[]) {
		ArrayList<Integer> valueList = new ArrayList<>();
		int pairValue = 0;
		
		for (int i = 0; i < cards.length; i++) {
			int cardValue = cards[i].getValue();
			
			if (valueList.contains(cardValue)) {
				pairValue = cardValue;
			}
			valueList.add(cardValue);
		}
		
		// Return CardCombination if a pair was found
		if (pairValue != 0) {
			int topValues[] = new int[Constants.NUMBER_COMMUNITY_CARDS];
			
			valueList.remove(pairValue);
			valueList.remove(pairValue);
			topValues[0] = pairValue;
			topValues[1] = pairValue;
			
			valueList.sort( (a,b) -> { return -1 * a.compareTo(b); } );
			for (int i = 0; i < topValues.length-2; i++) {
				topValues[i+2] = valueList.get(i);
			}
			
			try {
				return new CardCombination(HandRanking.ONE_PAIR, topValues);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// If no pair was found
		return null;
	}
	
	private boolean checkForTwoPair(Card cards[]) {
		ArrayList<Integer> valueList = new ArrayList<>();
		int numberOfPairs = 0;
		
		for (int i = 0; i < cards.length; i++) {
			int cardValue = cards[i].getValue();
			
			if (valueList.contains(cardValue)) {
				numberOfPairs += 1;
				
				if (numberOfPairs == 2) {
					return true;
				}
			}
			valueList.add(cardValue);
		}
		
		return false;
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
