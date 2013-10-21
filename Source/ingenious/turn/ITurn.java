package ingenious.turn;

import ingenious.board.Placement;
import ingenious.board.PlacementLocation;
import ingenious.board.Score;
import ingenious.board.Tile;

import java.util.ArrayList;
import java.util.List;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

public interface ITurn {
	
	/**
	 * @returns a copy of the list of actions with references to the real placements
	 * this is still secure due to placements being immutable.
	 */
	public List<Placement> getActions();
	
	/**
	 * returns true if the turn object does not have enough placements
	 * for a valid turn
	 */
	public boolean allowedToPlaceMoreTiles();
	
	public List<PlacementLocation> getAvailablePositions();
	
	/**
	 * returns a deep copy of the score so users of this function cannot modify the turn's player score
	 */
	public Score getPlayerScore();
	
	/**
	 * returns a deep copy of the player hand so users of this function cannot modify the turn's player hand
	 */
	public List<Tile> getHand();
	
	public boolean isValidPlacement(Placement placement);
	
	/**
	 * Allows you to place a tile on the board, and returns the tile you receive 
	 * from the admin
	 */
	public Tile placeTile (Placement placement);
	
	public boolean getKickFlag();
	
	public Score scorePlacement(Placement placement);
	
	/**
	 * reracks the players hand. Takes 6 new tiles from the tilebag, and puts the current hand back into the bag.
	 * checks that you are allowed to rerack and you cannot make any more placements
	 */
	public void rerack();
	
	/**
	 * returns true if you are allowed to rerack
	 */
	// TODO: note when canRerack is called, does not check temporal contract
	public boolean canRerack();

}
