/** 
 * Cell.java
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

import java.util.HashMap;
import java.util.Map;

import com.google.java.contract.Requires;

/**
 * Cell is a data structure for hexagon
 */
public class Cell {
    
    /** The coordinate of a hexagon **/
    private final Coordinate coordinate;
    
    private Map<Direction, Cell> neighbors;
    
    @Requires("distance >= 0 && angle >= 0")
    public Cell(int distance, int angle) {
        this.coordinate = new Coordinate(distance, angle);
        this.neighbors = new HashMap<Direction, Cell> ();
    }
    
    /**
     * get the neighboring cell in the given direction
     */
    public Cell getNeighbor(Direction direction) {
    	return neighbors.get(direction);
    }
    
    /**
     * get map of the neighbors of this cell
     */
    public Map<Direction, Cell> getNeighbors() {
    	return neighbors;
    }
    
    /**
     * returns true if this cell is a neighbor of the given cell on the board
     */
    public boolean isAdjacent(Cell otherCell) {
         return neighbors.containsValue(otherCell);
    }
    
    /**
     * returns true if this cell has a neighboring occupied cell
     */
    public boolean hasNeighboringOccupiedCell () {
        for(Cell neighbor : neighbors.values()) {
            if(neighbor.isOccupied()) {
            	return true;
            }
        }
        return false;
    }
    
    /** connect this cell's neighbor in the <dir> direction to the <otherCell> **/
    public void setNeighbor(Direction direction, Cell cell) {
    	neighbors.put(direction, cell);
    }
    

	/** The color of a hexagon **/
    private Color color;
    
    
    /**
     * Change the color of this cell.
     * @param color The instance of Color enum
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    /**
     * Get the distance of this cell
     */
    public int getDistance() {
        return coordinate.getDistance();
    }
    
    /**
     * Get the angle of this cell
     */
    public int getAngle() {
        return coordinate.getAngle();
    }
    
    /**
     * Get the coordinate of this cell
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }
    
    /**
     * Get the color of this cell
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Return true if the color of this cell is not null
     */
    public boolean isOccupied() {
        return color != null;
    }    
    
    /** Connects the two cells **/
    public void connect(Cell otherCell, Direction dir){
    	if (neighbors.get(dir) != null) {
    		System.out.println("connecting a already set direction: " + this + " " + dir + "currNeighbor: " + neighbors.get(dir));
    		return;
    	}
    	if (otherCell.getNeighbors().get(dir.inverse()) != null) {
    		System.out.println("connecting a already set direction: " + otherCell + " " + dir.inverse() + " currNeighbor: " + otherCell.getNeighbor(dir));
    		return;
    	}
    	this.setNeighbor(dir, otherCell);
    	otherCell.setNeighbor(dir.inverse(), this);
    }
    
    /**
     * Return true if this cell belong to the joint position of a 
     * hexagon board.
     */
    public boolean isJointPoint() {
        return coordinate.isJointPoint();
    }

    /**
     * Return true if this cell belong to the side position of a 
     * hexagon board.
     */
    public boolean isSidePoint() {
        return coordinate.isSidePoint();
    }
    
    /**
     * return the region the coordinate is located in.  
     */
    public Direction getRegion() {
        return coordinate.getRegion();
    }
    
    /** connects this cell to the other cell in the given direction **/
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if(color != null) {
            buffer.append("(").append(color).append(",")
                  .append(coordinate).append(")");
        } else{
            buffer.append(coordinate);
        }
        return buffer.toString();
    }
}
