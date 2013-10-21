/** 
 * BoardXML.java
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
 *  Feb 14, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
*/
package ingenious.suite.network;

import java.util.List;
import ingenious.board.Tile;

/**
 * AcceptXML is a POJO to encapsulate all information in the xml.
 */
public class AcceptXML {
        
    private int numberOfPlayers;
    private List<Tile> tiles;
    
    public AcceptXML(int numberOfPlayers, List<Tile> tiles) {
        this.numberOfPlayers = numberOfPlayers;
        this.tiles = tiles;
    }
    
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }
    
    public List<Tile> getTiles() {
    	return this.tiles;
    }
    
    @Override
    public String toString() {
    	return XMLConverter.getInstance().writeXml(this);
    }
    
}
