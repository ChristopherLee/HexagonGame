/** 
 * Player.java
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
 *  Feb 8, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
*/
package ingenious.player;

import java.util.List;

import com.google.java.contract.Invariant;

import ingenious.board.Placement;
import ingenious.strategy.IPlayerStrategy;
import ingenious.turn.Turn;

/**
 *  BadPlayer uses the given strategy but will always try to rerack at end of turn
 */
public class BadPlayer extends Player {
	public BadPlayer(IPlayerStrategy strategy) {
		super(strategy);
	}
    public void take(Turn turn, List<Placement> currentPlacements) throws StrategyException {
    	super.take(turn, currentPlacements);
    	turn.rerack();
    }
}
