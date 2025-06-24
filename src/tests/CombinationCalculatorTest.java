package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import game.deck.Card;
import game.deck.CardType;
import game.deck.CardValue;
import game.utils.CardCombination;
import game.utils.CardCombinationCalculator;
import game.utils.HandRanking;

class CombinationCalculatorTest {

	@Test
	void testHighCard1() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.THREE),
				new Card(CardType.HEARTS, CardValue.TEN)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.SEVEN),
				new Card(CardType.HEARTS, CardValue.NINE),
				new Card(CardType.DIAMONDS, CardValue.KING),
				new Card(CardType.SPADES, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.TWO)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.HIGH_CARD, 
				new CardValue[] {CardValue.ACE, CardValue.KING, CardValue.TEN, CardValue.NINE, CardValue.SEVEN});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testHighCard2() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.THREE),
				new Card(CardType.HEARTS, CardValue.TWO)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.SEVEN),
				new Card(CardType.HEARTS, CardValue.EIGHT),
				new Card(CardType.DIAMONDS, CardValue.ACE),
				new Card(CardType.SPADES, CardValue.FOUR),
				new Card(CardType.DIAMONDS, CardValue.KING)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.HIGH_CARD, 
				new CardValue[] {CardValue.ACE, CardValue.KING, CardValue.EIGHT, CardValue.SEVEN, CardValue.FOUR});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testOnePair1() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.THREE),
				new Card(CardType.HEARTS, CardValue.TEN)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.SEVEN),
				new Card(CardType.HEARTS, CardValue.NINE),
				new Card(CardType.DIAMONDS, CardValue.KING),
				new Card(CardType.SPADES, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.ONE_PAIR, 
				new CardValue[] {CardValue.TEN, CardValue.TEN, CardValue.ACE, CardValue.KING, CardValue.NINE});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testOnePair2() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.SEVEN),
				new Card(CardType.HEARTS, CardValue.TEN)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.SEVEN),
				new Card(CardType.HEARTS, CardValue.NINE),
				new Card(CardType.DIAMONDS, CardValue.KING),
				new Card(CardType.SPADES, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.TWO_PAIR, 
				new CardValue[] {CardValue.TEN, CardValue.TEN, CardValue.SEVEN, CardValue.SEVEN, CardValue.ACE});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testTwoPair1() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.SEVEN),
				new Card(CardType.HEARTS, CardValue.ACE)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.NINE),
				new Card(CardType.HEARTS, CardValue.NINE),
				new Card(CardType.DIAMONDS, CardValue.KING),
				new Card(CardType.SPADES, CardValue.TEN),
				new Card(CardType.DIAMONDS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.TWO_PAIR, 
				new CardValue[] {CardValue.TEN, CardValue.TEN, CardValue.NINE, CardValue.NINE, CardValue.ACE});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testTwoPair2() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.JACK),
				new Card(CardType.HEARTS, CardValue.ACE)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.NINE),
				new Card(CardType.HEARTS, CardValue.NINE),
				new Card(CardType.DIAMONDS, CardValue.ACE),
				new Card(CardType.SPADES, CardValue.TEN),
				new Card(CardType.DIAMONDS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.TWO_PAIR, 
				new CardValue[] {CardValue.ACE, CardValue.ACE, CardValue.TEN, CardValue.TEN, CardValue.JACK});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testTriple1() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.JACK),
				new Card(CardType.CLUBS, CardValue.KING)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.ACE),
				new Card(CardType.HEARTS, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.ACE),
				new Card(CardType.SPADES, CardValue.TEN),
				new Card(CardType.DIAMONDS, CardValue.EIGHT)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.TRIPLE, 
				new CardValue[] {CardValue.ACE, CardValue.ACE, CardValue.ACE, CardValue.KING, CardValue.JACK});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testTriple2() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.TWO),
				new Card(CardType.CLUBS, CardValue.KING)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.TWO),
				new Card(CardType.HEARTS, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.TWO),
				new Card(CardType.SPADES, CardValue.TEN),
				new Card(CardType.DIAMONDS, CardValue.EIGHT)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.TRIPLE, 
				new CardValue[] {CardValue.TWO, CardValue.TWO, CardValue.TWO, CardValue.ACE, CardValue.KING});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testStraight1() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.SEVEN),
				new Card(CardType.CLUBS, CardValue.NINE)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.JACK),
				new Card(CardType.HEARTS, CardValue.TEN),
				new Card(CardType.DIAMONDS, CardValue.EIGHT),
				new Card(CardType.SPADES, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.ACE)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.STRAIGHT, 
				new CardValue[] {CardValue.JACK, CardValue.TEN, CardValue.NINE, CardValue.EIGHT, CardValue.SEVEN});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testStraight2() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.TWO),
				new Card(CardType.CLUBS, CardValue.THREE)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.FOUR),
				new Card(CardType.HEARTS, CardValue.FIVE),
				new Card(CardType.DIAMONDS, CardValue.EIGHT),
				new Card(CardType.SPADES, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.ACE)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.STRAIGHT, 
				new CardValue[] {CardValue.FIVE, CardValue.FOUR, CardValue.THREE, CardValue.TWO, CardValue.ACE});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testStraight3() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.KING),
				new Card(CardType.CLUBS, CardValue.JACK)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.SEVEN),
				new Card(CardType.HEARTS, CardValue.SIX),
				new Card(CardType.DIAMONDS, CardValue.EIGHT),
				new Card(CardType.SPADES, CardValue.NINE),
				new Card(CardType.DIAMONDS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.STRAIGHT, 
				new CardValue[] {CardValue.JACK, CardValue.TEN, CardValue.NINE, CardValue.EIGHT, CardValue.SEVEN});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testStraight4() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.KING),
				new Card(CardType.CLUBS, CardValue.JACK)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.SEVEN),
				new Card(CardType.HEARTS, CardValue.SIX),
				new Card(CardType.DIAMONDS, CardValue.QUEEN),
				new Card(CardType.SPADES, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.STRAIGHT, 
				new CardValue[] {CardValue.ACE, CardValue.KING, CardValue.QUEEN, CardValue.JACK, CardValue.TEN});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testFlush1() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.DIAMONDS, CardValue.JACK),
				new Card(CardType.DIAMONDS, CardValue.KING)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.ACE),
				new Card(CardType.HEARTS, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.TEN),
				new Card(CardType.DIAMONDS, CardValue.EIGHT)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.FLUSH, 
				new CardValue[] {CardValue.ACE, CardValue.KING, CardValue.JACK, CardValue.TEN, CardValue.EIGHT});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testFlush2() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.CLUBS, CardValue.SEVEN),
				new Card(CardType.CLUBS, CardValue.TWO)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.ACE),
				new Card(CardType.CLUBS, CardValue.THREE),
				new Card(CardType.DIAMONDS, CardValue.ACE),
				new Card(CardType.CLUBS, CardValue.TEN),
				new Card(CardType.CLUBS, CardValue.EIGHT)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.FLUSH, 
				new CardValue[] {CardValue.ACE, CardValue.TEN, CardValue.EIGHT, CardValue.SEVEN, CardValue.THREE});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testFullHouse1() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.JACK),
				new Card(CardType.DIAMONDS, CardValue.NINE)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.NINE),
				new Card(CardType.HEARTS, CardValue.NINE),
				new Card(CardType.DIAMONDS, CardValue.ACE),
				new Card(CardType.SPADES, CardValue.TEN),
				new Card(CardType.DIAMONDS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.FULL_HOUSE, 
				new CardValue[] {CardValue.NINE, CardValue.NINE, CardValue.NINE, CardValue.TEN, CardValue.TEN});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testFullHouse2() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.JACK),
				new Card(CardType.DIAMONDS, CardValue.NINE)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.NINE),
				new Card(CardType.HEARTS, CardValue.NINE),
				new Card(CardType.HEARTS, CardValue.TEN),
				new Card(CardType.SPADES, CardValue.TEN),
				new Card(CardType.DIAMONDS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.FULL_HOUSE, 
				new CardValue[] {CardValue.TEN, CardValue.TEN, CardValue.TEN, CardValue.NINE, CardValue.NINE});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testQuads1() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.JACK),
				new Card(CardType.CLUBS, CardValue.TEN)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.NINE),
				new Card(CardType.HEARTS, CardValue.NINE),
				new Card(CardType.HEARTS, CardValue.TEN),
				new Card(CardType.SPADES, CardValue.TEN),
				new Card(CardType.DIAMONDS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.QUADS, 
				new CardValue[] {CardValue.TEN, CardValue.TEN, CardValue.TEN, CardValue.TEN, CardValue.JACK});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testQuads2() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.JACK),
				new Card(CardType.CLUBS, CardValue.JACK)
		};
		Card[] communityCards = {
				new Card(CardType.CLUBS, CardValue.ACE),
				new Card(CardType.HEARTS, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.ACE),
				new Card(CardType.SPADES, CardValue.JACK),
				new Card(CardType.DIAMONDS, CardValue.JACK)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.QUADS, 
				new CardValue[] {CardValue.JACK, CardValue.JACK, CardValue.JACK, CardValue.JACK, CardValue.ACE});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testStraightFlush1() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.KING),
				new Card(CardType.HEARTS, CardValue.JACK)
		};
		Card[] communityCards = {
				new Card(CardType.HEARTS, CardValue.SEVEN),
				new Card(CardType.HEARTS, CardValue.SIX),
				new Card(CardType.HEARTS, CardValue.EIGHT),
				new Card(CardType.HEARTS, CardValue.NINE),
				new Card(CardType.HEARTS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.STRAIGHT_FLUSH, 
				new CardValue[] {CardValue.JACK, CardValue.TEN, CardValue.NINE, CardValue.EIGHT, CardValue.SEVEN});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testStraightFlush2() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.HEARTS, CardValue.KING),
				new Card(CardType.DIAMONDS, CardValue.JACK)
		};
		Card[] communityCards = {
				new Card(CardType.HEARTS, CardValue.SEVEN),
				new Card(CardType.HEARTS, CardValue.SIX),
				new Card(CardType.HEARTS, CardValue.EIGHT),
				new Card(CardType.HEARTS, CardValue.NINE),
				new Card(CardType.HEARTS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.STRAIGHT_FLUSH, 
				new CardValue[] {CardValue.TEN, CardValue.NINE, CardValue.EIGHT, CardValue.SEVEN, CardValue.SIX});
		
		assertEquals(expectedCombination, receivedCombination);
	}
	
	@Test
	void testRoyalFlush() throws Exception {		
		// Input
		Card[] handCards = {
				new Card(CardType.DIAMONDS, CardValue.KING),
				new Card(CardType.DIAMONDS, CardValue.JACK)
		};
		Card[] communityCards = {
				new Card(CardType.DIAMONDS, CardValue.SEVEN),
				new Card(CardType.DIAMONDS, CardValue.SIX),
				new Card(CardType.DIAMONDS, CardValue.QUEEN),
				new Card(CardType.DIAMONDS, CardValue.ACE),
				new Card(CardType.DIAMONDS, CardValue.TEN)
		};
		
		// Output
		CardCombination receivedCombination = CardCombinationCalculator.calculateCombinationValue(handCards, communityCards);
		CardCombination expectedCombination = new CardCombination(
				HandRanking.ROYAL_FLUSH, 
				new CardValue[] {CardValue.ACE, CardValue.KING, CardValue.QUEEN, CardValue.JACK, CardValue.TEN});
		
		assertEquals(expectedCombination, receivedCombination);
	}
}
