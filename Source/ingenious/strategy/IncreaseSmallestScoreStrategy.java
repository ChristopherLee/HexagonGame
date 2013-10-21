/** 
 * BestScoreStrategy.java
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
import java.util.Set;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.PlacementLocation;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.strategy.IPlayerStrategy;

/**
 * for each possible move, maximize the smallest score value for the player
 */
public class IncreaseSmallestScoreStrategy implements IPlayerStrategy {

	@Requires({
        "board != null",
        "playerHand != null",
        "!playerHand.isEmpty()",
        "playerScore != null"
    })
    @Ensures({
    	"result != null"
    })
    @Override
    public Placement getAction(Board board, List<Tile> playerHand, Score playerScore) {
    	Score playerBestScore = playerScore;
    	PlacementLocation bestPlacement = null;
    	Set <PlacementLocation> placements = board.getAvailablePositionsForTile();
    	for (PlacementLocation placement : placements) {
			Score newScore = board.calculate(placement);
			Score buffer = playerScore.deepCopy();
			buffer.add(newScore);
			if (!playerBestScore.pickBestSmallestScore(playerBestScore, buffer).equals(playerBestScore)) {
				playerBestScore = buffer;
				bestPlacement = placement;
			}
    	}
    	
        return bestPlacement.createPlacementWithColor(playerHand.get(0));
    }
    
    @Requires({
        "board != null",
        "playerHand != null",        
        "playerScore != null"
    })
    @Override
	public boolean wantToRerack(Board board, List<Tile> playerHand, Score playerScore) {
		return true;
	}

}
