/** 
 * Direction.java
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
 *  Feb 1, 2011        Chunzhao, Zheng        Initial
 *  Feb 1, 2011        Christopher, Lee       Initial
 *********************************************************************
*/
package ingenious.board;

import ingenious.board.util.Constants;

import com.google.java.contract.Invariant;

/**
 * Direction is a enum data structure for the possible directions that
 * a hexagon can move to.
 */
@Invariant("ordinal() >= 0")
public enum Direction {
    N, NE, SE, S, SW, NW;
    
    /** returns the opposite direction **/
    public Direction inverse(){
    	return Direction.values()[(this.ordinal() +
    	                          Constants.THREE_DIRECTIONS_CLOCKWISE) %
    	                          Constants.NUMBER_OF_SIDES];
	}
    
    public Direction next(){
    	return values()[(this.ordinal() + 1) % Constants.NUMBER_OF_SIDES];
    }
}
