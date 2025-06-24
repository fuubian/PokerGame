package game.utils.calculator;

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
import game.utils.CardCombination;
import game.utils.HandRanking;
import main.Constants;

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
		CardCombination highestCombination = RoyalStraightFlushCalculator.checkForRoyalStraightFlush(cards);	
		
		if (highestCombination == null)
			highestCombination = PairCalculator.checkForPairTripleQuads(valueCounter, 4);	
		if (highestCombination == null)
			highestCombination = FullHouseCalculator.checkForFullHouse(valueCounter);	
		if (highestCombination == null)
			highestCombination = FlushCalculator.checkForFlush(cards);	
		if (highestCombination == null)
			highestCombination = StraightCalculator.checkForStraight(valueCounter.keySet());	
		if (highestCombination == null)
			highestCombination = PairCalculator.checkForPairTripleQuads(valueCounter, 3);
		if (highestCombination == null)
			highestCombination = PairCalculator.checkForTwoPair(valueCounter);
		if (highestCombination == null)
			highestCombination = PairCalculator.checkForPairTripleQuads(valueCounter, 2);		
		if (highestCombination == null)
			highestCombination = HighestCardCalculator.checkForHighestCard(valueCounter);
		
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
