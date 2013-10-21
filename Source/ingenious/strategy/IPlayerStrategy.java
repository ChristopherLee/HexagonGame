/** 
 * IPlayerStrategy.java
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
 *  Feb 18, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
*/
package ingenious.strategy;

import java.util.List;

import com.google.java.contract.Ensures;
import com.google.java.contract.Requires;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;

/**
 * Interface for a PlayerStrategy class
 */
public interface IPlayerStrategy {
    
	/**
	 * Based on the strategy, return the placement for the given Turn.  
	 * We provide a board, player hand, and the player's score. With this information 
	 * 
	 */
    @Requires({
        "board != null",
        "playerHand != null",
        "!playerHand.isEmpty()",
        "playerScore != null"
    })
    @Ensures({
    	"result != null"
    })
    public Placement getAction(Board board, List<Tile> playerHand, Score playerScore);
    
    /**
     * Allows the strategy to dictate whether to rerack or not.
     */
    @Requires({
        "board != null",
        "playerHand != null",        
        "playerScore != null"
    })
    public boolean wantToRerack(Board board, List<Tile> playerHand, Score playerScore);
}
