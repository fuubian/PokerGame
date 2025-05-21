package game.player;

public class PlayMove {

	private PlayMoveType playMoveType;
	private int coinValue;
	
	/*
	 * Constructor for Check and Fold moves.
	 */
	public PlayMove(PlayMoveType playMoveType) throws Exception {
		this.playMoveType = playMoveType;
		this.coinValue = 0;
		
		if (this.playMoveType == PlayMoveType.CALL || this.playMoveType == PlayMoveType.RAISE)
			throw new Exception("This constructor cannot be used for Call or Raise moves.");
	} 
	
	/*
	 * Constructor for Call and Raise moves.
	 */
	public PlayMove(PlayMoveType playMoveType, int coinValue) {
		this.playMoveType = playMoveType;
		this.coinValue = coinValue;
	}
	
	public PlayMoveType getType() { return this.playMoveType; }
	
	public int getCoinValue() { return this.coinValue; }
}
