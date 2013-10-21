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

import java.util.ArrayList;
import java.util.List;
import ingenious.board.Placement;
import ingenious.board.Tile;
import ingenious.distributed.ProxyTurn;

/**
 * RerackXML is a POJO to encapsulate all information in the xml.
 */
public class RerackXML implements IAdminResponse, ITurnAction{      
    private final List<Tile> tiles;

	public RerackXML(List<Tile> tiles) {
		this.tiles = tiles;
	}
	
	public RerackXML() {
		this.tiles = new ArrayList<Tile>();
	}

	public List<Tile> getTiles() {
		return tiles;
	}
	
	@Override
    public String toString() {
    	return XMLConverter.getInstance().writeXml(this);
    }

	@Override
	public IAdminResponse perform(ProxyTurn turn) {
		turn.rerack();
		return new RerackXML(turn.getHand());
	}
}
