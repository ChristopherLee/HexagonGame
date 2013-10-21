/** 
 * NullTile.java
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
 *  Feb 9, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
*/
package ingenious.board;

import com.google.java.contract.Invariant;

/**
 * class to represent a false tile, given by the admin if the admin has no more tiles
 */
@Invariant("isNull() == true")
public class NullTile extends Tile {
    
    public NullTile () {
        super(null, null);
    }
    
    public boolean isNull() {
        return true; 
    }
    
    public String toString() {
        return "Tile: false";
    }
    
    @Override
    public boolean equals(Object another) {    
        if(another == null)
            return false;
        if(!getClass().equals(another.getClass()))
            return false;
        return true;
    }
    
    @Override
    public boolean orientationEquals(Object another) {
    	return equals(another);
    }
}
