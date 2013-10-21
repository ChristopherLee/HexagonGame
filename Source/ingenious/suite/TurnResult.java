/** 
 * TurnResult.java
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
package ingenious.suite;

import java.util.ArrayList;
import java.util.List;

import com.google.java.contract.Invariant;

import ingenious.board.Tile;

@Invariant({
    "tiles != null || (tiles == null && reason != null)"
})
public class TurnResult {
    
    private final List<Tile> tiles;
    
    private final String reason;
    
    public TurnResult(List<Tile> tiles) {
        this(tiles, null);
    }
    
    public TurnResult(List<Tile> tiles, String reason) {
        this.tiles = new ArrayList<Tile>();
        if(tiles != null) {
            this.tiles.addAll(tiles);
        }
        if(reason != null) {
            this.reason = reason;
        } else {
            this.reason = "";
        }
    }

    public List<Tile> getTiles() {
        return tiles;
    }
    
    public boolean isBad() {
        return !reason.isEmpty();
    }
    
    public String getReason() {
        return reason;
    }
    
    @Override
    public boolean equals(Object another) {
        if(another == null)
            return false;
        
        if(!getClass().equals(another.getClass()))
            return false;
        
        TurnResult result = (TurnResult) another;
        return tiles.equals(result.tiles) && reason.equals(result.reason);
    }
    
}
