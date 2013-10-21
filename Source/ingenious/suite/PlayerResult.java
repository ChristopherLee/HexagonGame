/** 
 * PlayerResult.java
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
package ingenious.suite;

import com.google.java.contract.ContractAssertionError;

import ingenious.board.Score;
import ingenious.board.StateException;
import ingenious.player.StrategyException;

/**
 * Structure to store the result for the PlayerTester
 */
public class PlayerResult {
	private final PlayerResultType type;
	private Score score;
	private String error;
	
	public PlayerResult(Score score) {
		type = PlayerResultType.SCORE;
		this.score = score;
	}
	
	public PlayerResult(StateException exception) {
		type = PlayerResultType.BAD;
		this.error = "Contract Violated: " + exception.getMessage();
	}
	
	public PlayerResult(StrategyException exception) {
		type = PlayerResultType.STRATEGY;
	}
	
	public PlayerResultType getType() {
		return type;
	}
	
	public Score getScore() {
		return score;
	}
	
	public String getError() {
		return error;
	}
}
