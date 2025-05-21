package game;

import game.deck.Card;
import game.deck.Deck;
import game.player.PlayMove;
import game.player.PlayMoveType;
import game.player.Player;
import game.player.PlayerHandState;
import game.player.PlayerState;
import main.Constants;

public class Game {
	
	private Deck gameDeck;
	private Card comunnityCards[];
	private Player playerList[];
	private Phase gamePhase;
	private int buttonPosition;
	private int smallBlind;
	private int currentMainPot;
	private int currentRoundNumber;
	
	public Game() {
		this.playerList = new Player[Constants.NUMBER_OF_PLAYERS];
		this.gamePhase = Phase.PRE_FLOP;
		this.buttonPosition = (int) (Math.random() * Constants.NUMBER_OF_PLAYERS);
		this.smallBlind = Constants.SMALL_BLIND_START_VALUE;
		this.currentMainPot = 0;
		this.currentRoundNumber = 0;
		
		for (int i = 0; i < Constants.NUMBER_OF_PLAYERS; i++) {
			String playerName = "Player " + (i+1);
			this.playerList[i] = new Player(playerName, Constants.COINS_START_VALUE);
		}
	}
	
	/*
	 * Main game function. Running the entire game.
	 */
	public void runGame() {
		System.out.println("Game is running.");
		
		while (this.getNumberOfPlayersLeft() > 1) {
			this.initializeRound();
			while (this.gamePhase != Phase.SHOWDOWN && this.getNumberOfActivePlayers() > 1) {
				this.runBettingRound();
				this.updateRoundPhase();
			}
			// TODO: Execute showdown
			break;
		}
		
		System.out.println("Game has been completed.");
	}
	
	private void runShowdown() {
		int winningPlayerIndex;
		
		// Case of only one player left
		if (this.getNumberOfActivePlayers() == 1) {
			for (int i = 0; i < this.playerList.length; i++) {
				if (this.playerList[i].getPlayerHandState() != PlayerHandState.FOLDED) {
					winningPlayerIndex = i;
					break;
				}
			}
		}
		
		// Case of multiple players left
		for (int i = 0; i < this.playerList.length; i++) {
			if (this.playerList[i].getPlayerHandState() != PlayerHandState.FOLDED) {
				
			}
		}
	}
	
	/*
	 * Initializing a round by requesting the blinds and handing out the cards.
	 */
	private void initializeRound() {
		try {			
			System.out.println("A new round has started.\n");
			
			// Handing out the hand cards
			this.gameDeck = new Deck();
			this.comunnityCards = new Card[Constants.NUMBER_COMMUNITY_CARDS];
			for (Player player : playerList) {
				if (player.getPlayerState() != PlayerState.ELIMINATED) {
					Card[] playerHandCards = new Card[Constants.NUMBER_CARDS_ON_HAND];
					for (int i = 0; i < Constants.NUMBER_CARDS_ON_HAND; i++)
						playerHandCards[i] = this.gameDeck.drawCard();
					
					player.dealCards(playerHandCards);
				}
			}
			System.out.println();
			
			// Getting the blind positions
			int[] blindPositions = this.obtainBlindPositions();
			
			// Requesting the blinds
			for (int i = 1; i <= 2; i++) {
				this.currentMainPot += this.playerList[blindPositions[i-1]].requestCoins(i * this.smallBlind);
				
				String playerName = this.playerList[blindPositions[i-1]].getPlayerName();
				System.out.println(playerName + " paid the blind of " + (i * this.smallBlind) + " coins.");
			}
			
			// Increasing round counter
			this.currentRoundNumber++;
			
			System.out.println();
		} catch (Exception e) {
			System.out.println("Unable to initialize round.");
			e.printStackTrace();
		}
	}
	
	/*
	 * Run the betting phase. The phase of the round (Flop, Turn, River, etc.) and the minimum betting value are automatically derived.
	 */
	private void runBettingRound() {
		// Print current state
		this.printRoundStateBettingRound();
		
		// Determining the first player to start the round
		int[] startingValues = this.computeFirstPlayerOfBettingRound();
		int currentPlayerIndex = startingValues[0];
		int minimumBet = startingValues[1];
		
		// Number of non-folded players and a value to ensure that every player gets the opportunity to make a move
		int activePlayers = this.getNumberOfActivePlayers();
		int playerMovesRemaining = activePlayers;
		
		// Requesting bets of each player
		while ((playerMovesRemaining > 0 || !this.checkBetRoundCompletion(minimumBet)) && activePlayers > 1 ) {
			if (this.playerList[currentPlayerIndex].getPlayerHandState() == PlayerHandState.PLAYING_HAND) {
				playerMovesRemaining--;
				
				try {
					String playerName = this.playerList[currentPlayerIndex].getPlayerName();
					int coinDifference = minimumBet - this.playerList[currentPlayerIndex].getCurrentRoundBetting();
					
					PlayMove playMove = this.playerList[currentPlayerIndex].requestDecision(coinDifference);
					
					switch (playMove.getType()) {
					case PlayMoveType.CHECK:
						System.out.println(playerName + " checked.");
						break;
					case PlayMoveType.FOLD:
						activePlayers--;
						System.out.println(playerName + " has folded their hand.");
						break;
					case PlayMoveType.CALL:
						int calledValue = this.playerList[currentPlayerIndex].requestCoins(playMove.getCoinValue());
						this.currentMainPot += calledValue;
						System.out.println(playerName + " called " + calledValue + " coins.");
						break;
					case PlayMoveType.RAISE:
						int raisedValue = this.playerList[currentPlayerIndex].requestCoins(playMove.getCoinValue());
						this.currentMainPot += raisedValue;
						minimumBet = raisedValue;
						break;
					default:
						throw new Exception("Unexpected MoveType received: " + playMove.getType());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			// Update currentIndex
			currentPlayerIndex = (currentPlayerIndex + 1) % Constants.NUMBER_OF_PLAYERS;
		}
		
		System.out.println("\nBetting round competed.");
		System.out.println("Current main pot: " + this.currentMainPot);
		System.out.println("Number of active players: " + activePlayers + "\n");
	}
	
	/*
	 * Updating the current phase of the round and the community cards.
	 */
	private void updateRoundPhase() {
		if (this.getNumberOfActivePlayers() == 1) {
			this.gamePhase = Phase.SHOWDOWN;
		} else {
			switch(this.gamePhase) {
			case PRE_FLOP:
				this.gamePhase = Phase.FLOP;
				this.comunnityCards[0] = this.gameDeck.drawCard();
				this.comunnityCards[1] = this.gameDeck.drawCard();
				this.comunnityCards[2] = this.gameDeck.drawCard();
				break;
			case FLOP:
				this.gamePhase = Phase.TURN;
				this.comunnityCards[3] = this.gameDeck.drawCard();
				break;
			case TURN:
				this.gamePhase = Phase.RIVER;
				this.comunnityCards[4] = this.gameDeck.drawCard();
				break;
			case RIVER:
				this.gamePhase = Phase.SHOWDOWN;
				break;
			default:
				break;
			}
		}
	}
	
	/*
	 * @return Indexes of the players having the small blind and big blind.
	 */
	private int[] obtainBlindPositions() {
		int smallBlindPosition = (this.buttonPosition + 1) % Constants.NUMBER_OF_PLAYERS;
		while (this.playerList[smallBlindPosition].getPlayerState() == PlayerState.ELIMINATED) {
			smallBlindPosition = (smallBlindPosition + 1) % Constants.NUMBER_OF_PLAYERS;
		}
		int bigBlindPosition = (smallBlindPosition + 1) % Constants.NUMBER_OF_PLAYERS;
		while (this.playerList[bigBlindPosition].getPlayerState() == PlayerState.ELIMINATED) {
			bigBlindPosition = (bigBlindPosition + 1) % Constants.NUMBER_OF_PLAYERS;
		}
		
		return new int[]{smallBlindPosition, bigBlindPosition};
	}
	
	/*
	 * This function computes the first player that has to make its move during a new betting round.
	 * The index of the player to start and the minimum betting value are returned.
	 */
	private int[] computeFirstPlayerOfBettingRound() {
		int currentPlayerIndex = (this.buttonPosition + 1) % Constants.NUMBER_OF_PLAYERS;
		int minimumBet = 0;
		
		if (this.gamePhase == Phase.PRE_FLOP) {
			currentPlayerIndex = (this.obtainBlindPositions()[1] + 1) % Constants.NUMBER_OF_PLAYERS;
			while (this.playerList[currentPlayerIndex].getPlayerState() == PlayerState.ELIMINATED) {
				currentPlayerIndex = (currentPlayerIndex + 1) % Constants.NUMBER_OF_PLAYERS;
			}
			
			// Update minimumBet
			minimumBet = this.smallBlind * 2;
		}
		
		return new int[] { currentPlayerIndex, minimumBet };
	}
	
	/*
	 * Prints the current round state at the beginning of a betting round. 
	 */
	private void printRoundStateBettingRound() {
		System.out.println("Betting Round: " + this.gamePhase);
		
		if (this.gamePhase != Phase.PRE_FLOP) {
			System.out.print("Community Cards: ");
			for (Card card : this.comunnityCards) {
				if (card != null)
					System.out.print(card + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/*
	 * Check whether the current betting round is completed.
	 * This means that every active non-all-in player has called the current betting value (or all players have checked.)
	 */
	private boolean checkBetRoundCompletion(int minBet) {
		for (Player player : this.playerList) {
			if (player.getPlayerState() == PlayerState.PLAYING) {
				if (player.getPlayerHandState() == PlayerHandState.PLAYING_HAND && player.getCurrentRoundBetting() < minBet) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/*
	 * @return Number of active players in the current round.
	 */
	private int getNumberOfActivePlayers() {
		int numberActivePlayers = 0;
		
		for (Player player : this.playerList) {
			if (player.getPlayerState() == PlayerState.PLAYING && player.getPlayerHandState() == PlayerHandState.PLAYING_HAND) {
				numberActivePlayers++;
			}
		}
		
		return numberActivePlayers;
	}
	
	/**
	 * @return Number of non-eliminated players in the game.
	 */
	private int getNumberOfPlayersLeft() {
		int numberPlayersLeft = 0;
		
		for (Player player : this.playerList) {
			if (player.getPlayerState() == PlayerState.PLAYING) {
				numberPlayersLeft++;
			}
		}
		
		return numberPlayersLeft;
	}
}
