package game.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SortOrder;

import game.deck.Card;
import game.deck.CardType;
import game.deck.CardValue;
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
	
	public static CardCombination calculateCombinationValue(Card handCards[], Card communityCards[]) throws Exception {
		if (handCards.length != Constants.NUMBER_CARDS_ON_HAND || communityCards.length != Constants.NUMBER_COMMUNITY_CARDS) {
			throw new Exception("Unexpected number of cards received.\nReceived hand cards: " + handCards.length + 
					"\nReceived community cards: " + communityCards.length);
		}
		
		Card[] combinedCards = CardCombinationCalculator.combineCommunityHandCards(handCards, communityCards);	
		return CardCombinationCalculator.getHighestCardCombination(combinedCards);
	}
	
	private static CardCombination getHighestCardCombination(Card cards[]) throws Exception {
		HashMap<CardValue, Integer> valueCounter = CardCombinationCalculator.countOccurrencesPerValue(cards);	
		CardCombination highestCombination = checkForRoyalStraightFlush(cards);	
		
		if (highestCombination == null)
			highestCombination = CardCombinationCalculator.checkForPairTripleQuads(valueCounter, 4);	
		if (highestCombination == null)
			highestCombination = CardCombinationCalculator.checkForFullHouse(valueCounter);	
		if (highestCombination == null)
			highestCombination = CardCombinationCalculator.checkForFlush(cards);	
		if (highestCombination == null)
			highestCombination = CardCombinationCalculator.checkForStraight(valueCounter.keySet());	
		if (highestCombination == null)
			highestCombination = CardCombinationCalculator.checkForPairTripleQuads(valueCounter, 3);
		if (highestCombination == null)
			highestCombination = CardCombinationCalculator.checkForTwoPair(valueCounter);
		if (highestCombination == null)
			highestCombination = CardCombinationCalculator.checkForPairTripleQuads(valueCounter, 2);		
		if (highestCombination == null)
			highestCombination = CardCombinationCalculator.checkForHighestCard(valueCounter);
		
		return highestCombination;
	}

	private static HashMap<CardValue, Integer> countOccurrencesPerValue(Card[] cards) {
		HashMap<CardValue, Integer> valueCounter = new HashMap<>();
		
		// Store occurrences in HashMap
		for (int i = 0; i < cards.length; i++) {
			CardValue cardValue = cards[i].getValue();
			
			if (valueCounter.containsKey(cardValue)) {
				valueCounter.put(cardValue, valueCounter.get(cardValue)+1);
			} else {
				valueCounter.put(cardValue, 1);
			}
		}
		return valueCounter;
	}

	private static CardCombination checkForRoyalStraightFlush(Card cards[]) throws Exception {
		CardType flushType = getCardTypeForFlush(cards);
		if (flushType == null) {
			return null;
		}
		
		ArrayList<CardValue> flushCardValues = getAllCardValuesOfFlushType(cards, flushType);
		flushCardValues.sort( (a,b) -> { return -1 * a.compareTo(b); } );
		
		CardValue[] foundStraight = getStraightStreak(flushCardValues);
		if (foundStraight == null) {
			return null;
		}
		
		HandRanking ranking = HandRanking.STRAIGHT_FLUSH;
		if (foundStraight[0] == CardValue.ACE) {
			ranking = HandRanking.ROYAL_FLUSH;
		}
		
		return new CardCombination(ranking, foundStraight);
	}

	private static CardCombination checkForPairTripleQuads(HashMap<CardValue, Integer> valueCounter, int checkedCount) throws Exception {
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

	private static CardCombination checkForFullHouse(HashMap<CardValue, Integer> valueCounter) throws Exception {
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

	private static CardCombination checkForFlush(Card cards[]) throws Exception {
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

	private static CardCombination checkForStraight(Set<CardValue> values) throws Exception {
		ArrayList<CardValue> sortedValues = getSortedArrayList(values);	
		CardValue[] foundStraight = getStraightStreak(sortedValues);
		
		if (foundStraight == null) {
			return null;
		}
		
		return new CardCombination(HandRanking.STRAIGHT, foundStraight);
	}

	private static CardCombination checkForTwoPair(HashMap<CardValue, Integer> valueCounter) throws Exception {
		HashSet<CardValue> foundPairs = getAllPairs(valueCounter);
		if (foundPairs.size() < 2) {
			return null;
		}
		
		ArrayList<CardValue> sortedPairs = getSortedArrayList(foundPairs);
		
		// Remove top two pairs from valueCounter
		valueCounter.remove(sortedPairs.get(0));
		if (sortedPairs.size() > 1) {
			valueCounter.remove(sortedPairs.get(1));
		}
		
		ArrayList<CardValue> sortedValues = getSortedArrayList(valueCounter.keySet());
		
		CardValue[] topValues = getTopValuesTwoPair(sortedPairs, sortedValues);
		return new CardCombination(HandRanking.TWO_PAIR, topValues);
	}

	private static CardCombination checkForHighestCard(HashMap<CardValue, Integer> valueCounter) throws Exception {
		CardValue topValues[] = getTopValuesPairTripleQuads(valueCounter, 0);
		return new CardCombination(HandRanking.HIGH_CARD, topValues);
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
	
	private static CardValue[] getTopValuesPairTripleQuads(HashMap<CardValue, Integer> valueCounter, int checkedCount) {
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
		ArrayList<CardValue> sortedValues = getSortedArrayList(valueCounter.keySet());
		
		// Add highest cards to topValues
		for (int i = checkedCount; i < Constants.NUMBER_COMMUNITY_CARDS; i++) {
			topValues[i] = sortedValues.get(i-checkedCount);
		}
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
	
	private static CardValue[] getStraightStreak(ArrayList<CardValue> sortedValues) {
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
	
	private static ArrayList<CardValue> getAllCardValuesOfFlushType(Card[] cards, CardType flushType) {
		ArrayList<CardValue> flushCardValues = new ArrayList<>();
		for (Card card : cards) {
			if (card.getType() == flushType) {
				flushCardValues.add(card.getValue());
			}
		}
		return flushCardValues;
	}

	private static CardType getCardTypeForFlush(Card[] cards) {
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
	
	private static CardCombination getTopValuesFullHouse(HashSet<CardValue> tripletValues,
			HashSet<CardValue> pairValues) throws Exception {
		ArrayList<CardValue> sortedTriplets = getSortedArrayList(tripletValues);
		ArrayList<CardValue> sortedPairs = getSortedArrayList(pairValues);
		
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
	
	private static ArrayList<CardValue> getSortedArrayList(Set<CardValue> cardValues) {
		ArrayList<CardValue> sortedValues = new ArrayList<>(cardValues);
		sortedValues.sort( (a,b) -> { return -1 * a.compareTo(b); } );
		
		return sortedValues;
	}

	/*
	 * Combining handCards and communityCards into a single array.
	 */
	private static Card[] combineCommunityHandCards(Card handCards[], Card communityCards[]) {
		Card[] combinedCards = new Card[handCards.length + communityCards.length];
		int currentIndex = 0;
		
		// Appending handCards
		for (int i = 0; i < handCards.length; i++) {
			combinedCards[currentIndex] = handCards[i];
			currentIndex++;
		}
		
		// Appending communityCards
		for (int i = 0; i < communityCards.length; i++) {
			combinedCards[currentIndex] = communityCards[i];
			currentIndex++;
		}
		
		return combinedCards;
		
	}
}
