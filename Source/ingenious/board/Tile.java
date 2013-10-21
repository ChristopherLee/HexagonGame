/** 
 * Tile.java
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
 *  Feb 4, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
*/
package ingenious.board;

import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

/**
 * 
 * Tile consists of 2 colors that represents a physical tile in the game
 */
@Invariant("isNull() || (color0 != null && color1 != null)")
public class Tile {
    
    private final Color color0;
    
    private final Color color1;
    
    public Tile (Color color0, Color color1) {
        this.color0 = color0;
        this.color1 = color1;
    }
    
    public Color getColor0 () {
        return this.color0;
    }
    
    public Color getColor1 () {
        return this.color1;
    }
    
    public boolean isNull() {
        return false; 
    }
    
    public Tile getInvertedTile() {
    	if (isNull())
    		return new NullTile();
    	else
    		return new Tile(color1, color0);
    }
    
    @Override
    public String toString () {
        return "(" + color0 + ", " + color1 + ")";
    }
    
    @Override
    public boolean equals(Object another) {
        
        if(another == null)
            return false;
        
        if(!getClass().equals(another.getClass()))
            return false;
        
        Tile tile = (Tile) another;
        
        if(color0 == null && tile.color0 != null)
            return false;
        if(color1 != null && tile.color1 == null)
            return false;
        if(color0 == null && tile.color0 == null && 
               color1 == null && tile.color1 == null)
            return true;
        
        return (color0.equals(tile.color0) && color1.equals(tile.color1)) ||
        		(color0.equals(tile.color1) && color1.equals(tile.color0));
    }
    
    /**
     * this does the same thing as equals but you cannot swap colors
     */
    public boolean orientationEquals(Object another) {
    	if(another == null)
            return false;
        
        if(!getClass().equals(another.getClass()))
            return false;
        
        Tile tile = (Tile) another;
        
        if(color0 == null && tile.color0 != null)
            return false;
        if(color1 != null && tile.color1 == null)
            return false;
        if(color0 == null && tile.color0 == null && 
               color1 == null && tile.color1 == null)
            return true; 
        
        return color0.equals(((Tile)another).color0) && color1.equals(((Tile)another).color1);
    }
}
