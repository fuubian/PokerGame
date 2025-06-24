package game.utils;

import java.util.Arrays;

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
public class CardCombination {
	
	private HandRanking ranking;
	private CardValue cardValues[];
	
	public CardCombination(HandRanking ranking, CardValue cardValues[]) throws Exception {
		if (cardValues.length != Constants.NUMBER_COMMUNITY_CARDS) {
			throw new Exception("Unexpected number of cards received: " + cardValues.length);
		}
		
		this.ranking = ranking;
		this.cardValues = cardValues;
	}
	
	@Override
	public String toString() {
		String stringOutput = "Ranking: " + this.ranking + "\n";
		stringOutput += "Top Values:";
		
		for (int i = 0; i < cardValues.length; i++) {
			stringOutput += " " + cardValues[i];
		}
		
		return stringOutput+"\n";
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    
	    CardCombination other = (CardCombination) obj;
		
	    if (this.ranking != other.ranking) return false;
	    
	    for (int i = 0; i < Constants.NUMBER_COMMUNITY_CARDS; i++) {
	        if (this.cardValues[i] != other.cardValues[i]) {
	            return false;
	        }
	    }
		
		return true;
	}
	
	@Override
	public int hashCode() {
	    int result = ranking.hashCode();
	    result = 31 * result + Arrays.hashCode(cardValues);
	    return result;
	}
}
