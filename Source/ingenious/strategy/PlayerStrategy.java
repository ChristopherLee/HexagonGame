/** 
 * PlayerStrategy.java
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
 *  Feb 23, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
*/
package ingenious.strategy;

import java.util.List;

import com.google.java.contract.Requires;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.turn.Turn;

/**
 * Simple PlayerStrategy to return whatever actions were passed in to the constructor 
 */
public class PlayerStrategy implements IPlayerStrategy {

    private final List<Placement> actions;
    
    @Requires({
        "actions != null"
    })
    public PlayerStrategy(List<Placement> actions) {
        this.actions = actions;
    }
    
    @Override
    public Placement getAction(Board board, List<Tile> playerHand, Score playerScore) {
    	if (actions.isEmpty())
    		return null;
    	return actions.remove(0);
    }
    
    @Override
	public boolean wantToRerack(Board board, List<Tile> playerHand, Score playerScore) {
		return false;
	}
}
