package game.player;

import game.deck.Card;
import main.Constants;

public class Player {
	
	private String playerName;
	private int playerCoins;
	private int currentRoundBetting;
	
	private PlayerState playerState;
	private PlayerHandState playerHandState;
	private Card[] playerCards;
	
	public Player(String playerName, int playerCoins) {
		this.playerName = playerName;
		this.playerCoins = playerCoins;
		
		this.playerCards = new Card[Constants.NUMBER_CARDS_ON_HAND];
		this.playerState = PlayerState.PLAYING;
		this.playerHandState = PlayerHandState.PLAYING_HAND;
	}
	
	public PlayMove requestDecision(int coinDifference) {
		if (coinDifference > 0) {
			return this.call(coinDifference);
		}
		return this.check();
	}
	
	public void dealCards(Card[] dealtCards) throws Exception {
		if (dealtCards.length != Constants.NUMBER_CARDS_ON_HAND)
			throw new Exception("Unexpected number of hand cards received.");
	
		this.playerHandState = PlayerHandState.PLAYING_HAND;
		this.playerCards = dealtCards;
		this.currentRoundBetting = 0;
		
		System.out.println(this.playerName + " received the following cards: " + this.playerCards[0] + " " + this.playerCards[1]);
	}
	
	public int requestCoins(int coinValue) {
		if (coinValue < this.playerCoins) {
			this.playerCoins -= coinValue;
			this.currentRoundBetting += coinValue;
			return coinValue;
		}
		
		// All-In Case
		int remainingCoins = this.playerCoins;
		this.playerCoins = 0;
		this.currentRoundBetting += remainingCoins;
		this.playerHandState = PlayerHandState.ALL_IN;
		return remainingCoins;
	}
	
	public void addReward(int coinsValue) {
		this.playerCoins += coinsValue;
	}
	
	public String getPlayerName() { return this.playerName; }
	
	public PlayerState getPlayerState() { return this.playerState; }
	
	public PlayerHandState getPlayerHandState() { return this.playerHandState; }
	
	public int getPlayerCoins() { return this.playerCoins; }
	
	public int getCurrentRoundBetting() { return this.currentRoundBetting; }
	
	private PlayMove check() {
		try {
			return PlayMove.createCheckMove();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private PlayMove call(int coins) {
		return PlayMove.createCallMove(coins);
	}
	
	private PlayMove raise(int coins) {
		return PlayMove.createRaiseMove(coins);
	}

	private PlayMove foldHand() {
		this.playerHandState = PlayerHandState.FOLDED;
		return PlayMove.createFoldMove();
	}
	
}
