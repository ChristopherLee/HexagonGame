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

import javax.annotation.processing.SupportedAnnotationTypes;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.PlacementLocation;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.strategy.IPlayerStrategy;

/**
 * For each possible move, add the numbers for each color together, and pick the move that has the highest number
 */
public class HighestScoreStrategy implements IPlayerStrategy {
    
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
    	Score bestScore = new Score();
    	PlacementLocation bestPlacement = null;
    	Set <PlacementLocation> placements = board.getAvailablePositionsForTile();
    	for (PlacementLocation placement : placements) {
			Score score = board.calculate(placement);
			if (score.compareScoreDiff(bestScore) > 0) {
				bestScore = score;
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
		return false;
	}
    
}
