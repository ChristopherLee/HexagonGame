/** 
 * TurnXML.java
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
package ingenious.suite.network;

import java.util.List;

import com.google.java.contract.Invariant;

import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;

/**
 * TurnXML is a POJO to encapsulate all information in the xml.
 */
@Invariant({
    "currentPlacements != null",
    "playerHand != null",
    "playerScore != null"
})
public class TurnXML {
    private final int numberOfPlayers;
    private final List<Placement> currentPlacements;
    private final List<Tile> playerHand;
    private final Score playerScore;
    
    public TurnXML(int numberOfPlayers, List<Placement> currentPlacements, 
                    List<Tile> playerHand, Score playerScore) {
        this.numberOfPlayers = numberOfPlayers;
        this.currentPlacements = currentPlacements;
        this.playerHand = playerHand;
        this.playerScore = playerScore;
    }
   
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    
    public List<Placement> getCurrentPlacements() {
        return currentPlacements;
    }

    
    public List<Tile> getPlayerHand() {
        return playerHand;
    }
   
    public Score getPlayerScore() {
        return playerScore;
    }

    @Override
    public String toString() {
    	return XMLConverter.getInstance().writeXml(this);
    }
}
