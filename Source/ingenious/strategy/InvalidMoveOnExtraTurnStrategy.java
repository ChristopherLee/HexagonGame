package ingenious.strategy;

import java.util.List;

import com.google.java.contract.Requires;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.turn.Turn;
import ingenious.board.PlacementLocation;

/**
 * Test player strategy that makes an invalid first move 
 */
public class InvalidMoveOnExtraTurnStrategy implements IPlayerStrategy {
    private boolean nextTurnIsExtraTurn; 
    public InvalidMoveOnExtraTurnStrategy() {
        nextTurnIsExtraTurn = false;
    }
    
    @Override
    public Placement getAction(Board board, List<Tile> playerHand, Score playerScore) {
    	Placement placement;
    	Tile tile = playerHand.get(0);
    	if (nextTurnIsExtraTurn) {
    		
    		placement = new Placement(tile, 5, 0, 5, 1);
    	}
    	else {
	    	placement = board.getAvailablePositionsForTile().iterator().next().createPlacementWithColor(tile);
	        Score currentScore = playerScore.deepCopy();
	        currentScore.add(board.calculate(placement));    	
	        if (numberOfExtraTurns(playerScore, currentScore) > 0)
	        	nextTurnIsExtraTurn = true;
    	}
    	return placement;
    }
    
    /**
     * 
     * How many scores have crossed the 18 point mark. 
     */
    private int numberOfExtraTurns(Score previousScore, Score currentScore) {
    	return currentScore.colorsOver17() - previousScore.colorsOver17();
    }
    
    @Override
	public boolean wantToRerack(Board board, List<Tile> playerHand, Score playerScore) {
		return false;
	}
}
