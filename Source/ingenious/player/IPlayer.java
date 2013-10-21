/** 
 * IPlayer.java
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
 *  Feb 18, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
*/
package ingenious.player;

import java.util.List;

import com.google.java.contract.Requires;

import ingenious.admin.GameUpdate;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.StateException;
import ingenious.board.Tile;
import ingenious.board.Board;

import ingenious.board.util.Constants;
import ingenious.turn.Turn;

/**
 * Interface for a player class
 */
public interface IPlayer extends Constants{
    
    /**
     * @param numberOfPlayers: the number of players
     * @param tiles: an array of initial tiles in players' hand
     */
    @Requires({
        "numberOfPlayers >= 2",
        "numberOfPlayers <= 6",
        "tiles != null",
        "tiles.size() == 6",
    })
    public void acceptNumberOfPlayersAndHand(int numberOfPlayers, List<Tile> tiles);
    
    /**
     * @param placements: an array of board placements
     * @param turn: An turn object 
     */
    @Requires({
        "turn != null",
        "currentPlacements != null"
    })
    public void take(Turn turn, List<Placement> currentPlacements) throws StateException, StrategyException;
    
    public void receiveUpdate(GameUpdate update);    
    
    public void acceptPlayerNames(List<String> playerNames);
}
