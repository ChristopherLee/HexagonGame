/** 
 * Placement.java
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

import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

/**
 * Placement is a data structure for an user placement.
 */
@Invariant({
	"coord0 != null",
	"coord0.getDistance() >= 0",
	"coord0.getAngle() >= 0",
	"coord1 != null",
	"coord1.getDistance() >= 0",
	"coord1.getAngle() >= 0"
})
public class Placement {
	protected final Color color0;
	protected final Color color1;
	protected final Coordinate coord0;
	protected final Coordinate coord1;

	@Requires({
		"distance0 >= 0",
		"angle0 >= 0",
		"distance1 >= 0",
		"angle1 >= 0"
	})
	public Placement(int distance0, int angle0, int distance1, int angle1) {
		this.color0 = null;
		this.color1 = null;
		this.coord0 = new Coordinate(distance0, angle0);
		this.coord1 = new Coordinate(distance1, angle1);
	}

	/**
	 * A placement consists of two coordinates and 2 colors associated with the coordinates
	 */
	@Requires({
		"color0 != null",
		"distance0 >= 0",
		"angle0 >= 0",
		"color1 != null",
		"distance1 >= 0",
		"angle1 >= 0"
	})
	public Placement(Color color0, 
			int distance0, 
			int angle0,
			Color color1,
			int distance1, 
			int angle1){
		this.color0 = color0;
		this.color1 = color1;
		this.coord0 = new Coordinate(distance0, angle0);
		this.coord1 = new Coordinate(distance1, angle1);
	}

	public Placement(Tile tile, 
			int distance0, 
			int angle0,
			int distance1, 
			int angle1){
		this.color0 = tile.getColor0();
		this.color1 = tile.getColor1();
		this.coord0 = new Coordinate(distance0, angle0);
		this.coord1 = new Coordinate(distance1, angle1);
	}

	public Tile getTile() {
		if (color0 == null || color1 == null)
			return new NullTile();
		else
			return new Tile(color0, color1);
	}

	public Coordinate getCoordinate0() {
		return coord0;
	}

	public Coordinate getCoordinate1() {
		return coord1;
	}

	@Override
	public String toString() {
		return "(" + color0 + ", " + color1 + "," + coord0 + "," + coord1 + ")";
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof Placement){
			Placement other = (Placement)obj;
			return (this.getCoordinate0().equals(other.getCoordinate0()) &&
					this.getCoordinate1().equals(other.getCoordinate1()) &&
					this.getTile().orientationEquals(other.getTile())) ||
					(this.getCoordinate0().equals(other.getCoordinate1()) &&
					this.getCoordinate1().equals(other.getCoordinate0()) &&
					this.getTile().orientationEquals(other.getTile().getInvertedTile())); 
		}
		return false;
	}
	
	@Override 
	public int hashCode() {
        return (coord0.hashCode() + color0.hashCode()) * (coord1.hashCode() + color1.hashCode());
    }

}
