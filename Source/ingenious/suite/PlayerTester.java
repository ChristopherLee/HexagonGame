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
import ingenious.board.Score;
import ingenious.board.StateException;
import ingenious.board.Tile;
import ingenious.player.Player;
import ingenious.player.StrategyException;
import ingenious.strategy.PlayerStrategy;
import ingenious.turn.Turn;

public class PlayerTester {
    
    public static void main(String[] args) {                
        execute(System.in);
    }
    
    public static PlayerResult execute(InputStream input) {
        
        TurnScriptExecutor executor = TurnScriptExecutor.getInstance();
        
        PlayerResult result = null;
        
        TurnXML pojo = (TurnXML) executor.readXml(input);
        Player player = createPlayer(pojo);
        
        try {
        	Turn turn = createTurn(pojo);
        	result = simulate(player, turn, pojo);
        }
        catch (StateException e) {
        	result = new PlayerResult(e);
        }
       
        String xml = executor.writeXml(result);
        System.out.println(xml);
        
        return result;
    }
    
    private static Board createBoard(TurnXML pojo) throws ContractAssertionError{
        Board board = new Board(pojo.getNumberOfPlayers());
        for (Placement placement : pojo.getCurrentPlacements()) {
            board.place(placement);
        }
        return board;
    }
    
    private static Turn createTurn(TurnXML pojo) throws StateException{
        Board board = createBoard(pojo);
        Turn turn = new Turn(board, pojo.getPlayerHand(), 
                        pojo.getPlayerScore(), new TileBag(pojo.getTileBag()));
        return turn;
    }
    
    private static Player createPlayer(TurnXML pojo) {
        PlayerStrategy strategy = new PlayerStrategy(pojo.getActions());
        Player player = new Player(strategy);
        return player;
    }
    
    
    private static PlayerResult simulate(Player player, Turn turn, TurnXML pojo) {
        PlayerResult result;
    	player.acceptNumberOfPlayersAndHand(pojo.getNumberOfPlayers(), pojo.getPlayerHand());
        try {
        	player.take(turn, pojo.getCurrentPlacements());
        	Score scoreForTurn = player.getScore();
        	Score initialPlayerScore = pojo.getPlayerScore(); 
        	initialPlayerScore.add(scoreForTurn);
        	result = new PlayerResult(initialPlayerScore);
        }
        catch (StateException e) {
        	result = new PlayerResult(e);
        }
        catch (StrategyException e) {
        	result = new PlayerResult(e);
        }
        
        return result;
    }

}
