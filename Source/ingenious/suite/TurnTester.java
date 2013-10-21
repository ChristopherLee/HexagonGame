/** 
 * TurnTester.java
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.java.contract.ContractAssertionError;

import ingenious.admin.TileBag;
import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.StateException;
import ingenious.board.Tile;
import ingenious.turn.Turn;

public class TurnTester {
    
    public static void main(String[] args) {                
        execute(System.in);
    }
    
    public static TurnResult execute(InputStream input) {
        
        TurnScriptExecutor executor = TurnScriptExecutor.getInstance();

        TurnResult result;
        try {
            TurnXML pojo = (TurnXML) executor.readXml(input);
            Board board = createBoard(pojo.getNumberOfPlayers(), pojo.getCurrentPlacements());
            Turn turn = new Turn(board, pojo.getPlayerHand(), 
                                pojo.getPlayerScore(), new TileBag(pojo.getTileBag()));
            result = simulate(turn, pojo.getActions());
        } catch (ContractAssertionError e) {
            result = new TurnResult(null, e.getMessage());
        }
        
        String xml = executor.writeXml(result);
        System.out.println(xml);

        return result;
    }
    
    /**
     * simulates a turn being played out with the given actions
     */
    private static TurnResult simulate(Turn turn, List<Placement> actions) {
        TurnResult result;
       
        ArrayList<Tile> tilesFromAdmin = new ArrayList<Tile>();
        try {
            for (Placement placement : actions) {
                Tile tile = turn.placeTile(placement);
                tilesFromAdmin.add(tile);
            }
            if (turn.allowedToPlaceMoreTiles()) {
                throw new StateException ("The player satisfied the 18 point rule but did not take an extra turn.");
            }
                
            result = new TurnResult (tilesFromAdmin);
        }
        catch (StateException e) {
            result = new TurnResult(tilesFromAdmin, e.getMessage());
        }
        
        return result;
    }
    
    /**
     * creates a board with the number of players and the current placements on the board
     */
    private static Board createBoard (int numberOfPlayers, List<Placement> currentPlacements) throws ContractAssertionError{
        Board board = new Board(numberOfPlayers);
        for (Placement placement : currentPlacements) {
            board.place(placement);
        }
        return board;
    }

}
