/** 
 * Turn.java
 *
 * This source file can be used for educational purpose only.
 * Originally developed by Chunzhao Zheng.
 * 
 * Copyright (c) 2011 Chunzhao, College of Computer and Information Science,
 * Northeastern University, Boston, MA 02115. All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met: 
 *     -  Redistributions of source code must retain the above copyright notice.
 *     -  Redistributions in binary form must reproduce the above copyright notice.
 *
 * Revision History
 *       Date             Programmer            Notes
 * ------------------ ------------------ -------------------
 *  Feb 4, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
 */
package ingenious.turn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ingenious.board.util.Constants;
import ingenious.board.Board;
import ingenious.board.Color;
import ingenious.board.NullTile;
import ingenious.board.Placement;
import ingenious.board.PlacementLocation;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.admin.TileBag;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

/**
 * Proxy object for Turn: to provide an API to interact with all parts
 * associated with the turn
 */
public class Turn {
	protected final Board board;
	protected final List<Tile> playerHand;
	protected final Score playerScore;
	private int numberOfMoves;
	private final TileBag tileBag; 

	protected final ArrayList<Placement> actions;
	protected boolean kickFlag;
	protected boolean hasReracked;

	@Requires({
		"board != null",
		"playerHand != null",
		"playerHand.size() > 0",
		"playerHand.size() <= 6",
		"playerScore != null",
		"tileBag != null"
	})
	public Turn (Board board, List<Tile> playerHand, Score playerScore, TileBag tileBag) {
		this.board = board;
		this.playerHand = playerHand;
		this.playerScore = playerScore;
		this.tileBag = tileBag;
		this.actions = new ArrayList<Placement> ();
		numberOfMoves = 1;

		kickFlag = false;
		hasReracked = false;
	}

	/**
	 * @returns a copy of the list of actions with references to the real placements
	 * this is still secure due to placements being immutable.
	 */
	public List<Placement> getActions(){
		List<Placement> actionsCopy = new ArrayList<Placement>();
		for(Placement placement : actions)
			actionsCopy.add(placement);
		return actionsCopy;
	}

	/**
	 * returns true if the turn object does not have enough placements
	 * for a valid turn
	 */
	public boolean allowedToPlaceMoreTiles() {
		return board.hasAvailablePositionsForTile() && 
			   numberOfMoves > 0 && 
			   playerHand.size() > 0;
	}

	public Set<PlacementLocation> getAvailablePositions() {
		return board.getAvailablePositionsForTile();
	}

	/**
	 * returns true if this is the first placement on the turn object
	 */
	protected boolean isFirstPlacement () {
		return actions.size() == 0;
	}

	/**
	 * returns a deep copy of the score so users of this function cannot modify the turn's player score
	 */
	public Score getPlayerScore() {
		return playerScore.deepCopy();
	}
	
	/**
	 * returns a deep copy of the player hand so users of this function cannot modify the turn's player hand
	 */
	public List<Tile> getHand() {
		List<Tile> copy = new ArrayList<Tile>();
		for (Tile tile : playerHand) 
			copy.add(tile);
		return copy;
	}

	@Requires({
		"placement != null"
	})
	public boolean isValidPlacement(Placement placement) {
		return board.verifyValidPlacement(placement);
	}

	protected boolean checkLegalPlacement(Placement placement){
		if( (placement != null) && allowedToPlaceMoreTiles() && playerHand.contains(placement.getTile()) &&
				isValidPlacement(placement))
			return true;
		kickFlag = true;
		return false; 	
	}

	/**
	 * Allows you to place a tile on the board, and returns the tile you receive 
	 * from the admin
	 */
	@Requires({
		"!kickFlag",
		"checkLegalPlacement(placement)"
	})
	@Ensures("result != null")
	public Tile placeTile (Placement placement) {
		
		
		Score placementScore = board.calculate(placement);
		board.place(placement);
		
		Score originalScore = playerScore.deepCopy();
		
		
		Tile tileFromAdmin = tileBag.getNextTile();

		playerHand.remove(placement.getTile());
		playerScore.add(placementScore);
		if (!tileFromAdmin.isNull())
			playerHand.add(tileFromAdmin);

		actions.add(placement);
		
		numberOfMoves = numberOfMoves - 1 + calculateExtraMoves(originalScore, playerScore);
		return tileFromAdmin;
	}

	public boolean getKickFlag() {
		return kickFlag;
	}

	@Requires({
		"placement != null",
		"allowedToPlaceMoreTiles()",
		"playerHand.contains(placement.getTile())"
	})
	public Score scorePlacement(Placement placement) {
		Score score = board.calculate(placement);
		return score;
	}
	
	/**
	 * reracks the players hand. Takes 6 new tiles from the tilebag, and puts the current hand back into the bag.
	 * checks that you are allowed to rerack and you cannot make any more placements
	 */
	@Requires({
		"!kickFlag",
		"checkRerack()",
		"!allowedToPlaceMoreTiles()"
	})
	public void rerack() {
		hasReracked = true;
		List<Tile> newHand = new ArrayList<Tile> ();
		for (int i = 0; i < Constants.NUMBER_OF_STARTING_TILES_IN_HAND; i++) {
			newHand.add(tileBag.getNextTile());
		}
		
		for (int i = 0; i < Constants.NUMBER_OF_STARTING_TILES_IN_HAND; i++) {
			tileBag.addTileToBag(playerHand.remove(0));
		}
		
		playerHand.addAll(newHand);
	}

	/**
	 * wrapper for canRerack that additionally sets the kickFlag to true
	 * if the player attempted to illegally rerack. 
	 */
	protected boolean checkRerack(){
		if(canRerack())
			return true;
		kickFlag = true;
		return false;
	}

	/**
	 * returns true if you are allowed to rerack
	 */
	// TODO: note when canRerack is called, does not check temporal contract
	public boolean canRerack() {
		return !hasReracked && 
			isBagRerackable() && 
			playerHand.size() == 6 &&
			handDoesNotContainLowestScoringColor(); 
	}
	
	public boolean isBagRerackable() {
		return tileBag.size() >= 6;
	}
	
	/**
	 * returns the number of Colors that are over 17 in the new score but not over 17 in the old score
	 */
	private int calculateExtraMoves (Score oldScore, Score newScore) {
		return newScore.colorsOver17() - oldScore.colorsOver17();  
	}
	
	/**
	 * If the hand contains the score of any of the player's score's lowest colors
	 */
	protected boolean handDoesNotContainLowestScoringColor() {
		List<Color> lowColors = playerScore.getLowestScoringColors();
	
		for (Color color : lowColors) {
			if (handContainsColor(color))
				return false;		
		}
		return true;
	}

	/**
	 * returns true if any tile in the hand contains the given color
	 */	
	private boolean handContainsColor(Color color) {
		for (Tile tile : playerHand) {
			if (tile.getColor0().equals(color) ||
			    tile.getColor1().equals(color))
				return true;
		}
		return false;
	}
}

