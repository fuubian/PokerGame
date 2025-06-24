package game.player;

public class PlayMove {

	private PlayMoveType playMoveType;
	private int coinValue;
	
	private PlayMove(PlayMoveType playMoveType, int coinValue) {
        this.playMoveType = playMoveType;
        this.coinValue = coinValue;
    }
	
	public static PlayMove createCheckMove() {
        return new PlayMove(PlayMoveType.CHECK, 0);
    }

    public static PlayMove createFoldMove() {
        return new PlayMove(PlayMoveType.FOLD, 0);
    }

    public static PlayMove createCallMove(int coinValue) {
        return new PlayMove(PlayMoveType.CALL, coinValue);
    }

    public static PlayMove createRaiseMove(int coinValue) {
        return new PlayMove(PlayMoveType.RAISE, coinValue);
    }
	
	public PlayMoveType getType() { return this.playMoveType; }
	
	public int getCoinValue() { return this.coinValue; }
}
