package game.utils;

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
public class CardCombination {
	
	private HandRanking ranking;
	private int cardValues[];
	
	public CardCombination(HandRanking ranking, int cardValues[]) throws Exception {
		if (cardValues.length != Constants.NUMBER_COMMUNITY_CARDS) {
			throw new Exception("Unexpected number of cards received: " + cardValues.length);
		}
		
		this.ranking = ranking;
		this.cardValues = cardValues;
	}
}
