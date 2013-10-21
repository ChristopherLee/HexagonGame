/** 
 * Coordinate.java
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

import com.google.java.contract.Requires;

/**
 * Coordinate is data structure for the position of a cell.
 */
public class Coordinate {

    private final int distance;

    private final int angle;

    @Requires("isValid(distance, angle)")
    public Coordinate(int distance, int angle) {
        this.distance = distance;
        this.angle = angle;
    }

    /** is this a valid coordinate? **/
    public static boolean isValid(int distance, int angle){
    	if(distance == 0){
    		return (angle == 0);
    	}
    	else{
    		return (angle < distance*Constants.NUMBER_OF_SIDES &&
    				angle >= 0);
    	}
    }
    
    public int getDistance() {
        return distance;
    }

    public int getAngle() {
        return angle;
    }

    /**
     * Return true if this coordinate belong to the joint position of a 
     * hexagon board.
     */
    public boolean isJointPoint() {
        return angle % distance == 0;
    }

    /**
     * Return true if this coordinate belong to the side position of a 
     * hexagon board.
     */
    public boolean isSidePoint() {
        return angle % distance != 0;
    }
    
    /**
     * @return a Direction representing one of six regions on the board
     */
    public Direction getRegion() {
    	return Direction.values()[angle / distance];
    }
    
  
    
    @Override
    public String toString() {
        return "(" + distance + "," + angle + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) 
            return false;
        if (!Coordinate.class.equals(other.getClass())) 
            return false;
        return distance == ((Coordinate) other).distance
                    && angle == ((Coordinate) other).angle;
    }
    
    @Override
    public int hashCode() {
        int hash = 17;
        hash = hash * 31 + distance;
        hash = hash * 31 + angle;
        return hash;
    }

}
