/** 
 * RandomMoveStrategy.java
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
 *  Mar 4, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
*/
package ingenious.strategy;

import java.util.List;
import java.util.Random;
import java.util.Set;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.PlacementLocation;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.strategy.IPlayerStrategy;
import ingenious.turn.Turn;

/**
 *  Picks a random valid placement with a random tile in his hand and performs that move
 */
public class RandomMoveStrategy implements IPlayerStrategy {

    private final Random rand;
    
    public RandomMoveStrategy() {
        rand = new Random();
    }
    
    @Override
    public Placement getAction(Board board, List<Tile> playerHand, Score playerScore) {
    	Set <PlacementLocation> placements = board.getAvailablePositionsForTile();
        PlacementLocation placement = (PlacementLocation)placements.toArray()[rand.nextInt(placements.size())];
        return placement.createPlacementWithColor(playerHand.get(rand.nextInt(playerHand.size())));
    }
    
    @Override
	public boolean wantToRerack(Board board, List<Tile> playerHand, Score playerScore) {
		return false;
	}

}
