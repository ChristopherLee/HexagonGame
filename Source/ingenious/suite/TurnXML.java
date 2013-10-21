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
package ingenious.suite;

import java.util.List;

import com.google.java.contract.Invariant;

import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;

@Invariant({
    "currentPlacements != null",
    "playerHand != null",
    "playerScore != null",
    "tileBag != null",
    "actions != null"
})
class TurnXML {
    private final int numberOfPlayers;
    private final List<Placement> currentPlacements;
    private final List<Tile> playerHand;
    private final Score playerScore;
    private final List<Tile> tileBag;
    private final List <Placement> actions;
    
    public TurnXML(int numberOfPlayers, List<Placement> currentPlacements, 
                    List<Tile> playerHand, Score playerScore, List<Tile> tileBag, List<Placement> actions) {
        this.numberOfPlayers = numberOfPlayers;
        this.currentPlacements = currentPlacements;
        this.playerHand = playerHand;
        this.playerScore = playerScore;
        this.tileBag = tileBag;
        this.actions = actions;
    }

    List<Placement> getActions() {
        return actions;
    }

    
    int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    
    List<Placement> getCurrentPlacements() {
        return currentPlacements;
    }

    
    List<Tile> getPlayerHand() {
        return playerHand;
    }

    
    List<Tile> getTileBag() {
        return tileBag;
    }
    
    Score getPlayerScore() {
        return playerScore;
    }

}
