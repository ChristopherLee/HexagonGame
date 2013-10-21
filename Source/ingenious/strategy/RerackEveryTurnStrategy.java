package ingenious.strategy;

import java.util.List;
import java.util.Set;

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
public class RerackEveryTurnStrategy implements IPlayerStrategy {
    
    @Override
    public Placement getAction(Board board, List<Tile> playerHand, Score playerScore) {
        
    	Set <PlacementLocation> placements = board.getAvailablePositionsForTile();
		return placements.iterator().next().createPlacementWithColor(playerHand.get(0));
    }
    
    @Override
	public boolean wantToRerack(Board board, List<Tile> playerHand, Score playerScore) {
		return true;
	}
}
