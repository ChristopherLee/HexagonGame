/** 
 * Player.java
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
package ingenious.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.java.contract.ContractAssertionError;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

import ingenious.admin.GameUpdate;
import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.hci.GUIGameState;
import ingenious.strategy.IPlayerStrategy;
import ingenious.turn.Turn;

/**
 *  Player is a container to run the given strategy on the turn.
 *  A player object can only be used for one game.
 */
@Invariant({
    "score != null",
    "tilesInHand != null"
})
public class Player implements IPlayer {

	/** variable to keep track of temporal contract so startGame is not called more than once */
	private enum State {BeforeGameStart, AfterGameStart};
    /** player's current score */
    private final Score score;
    
    /** player's current hand */
    private List<Tile> tilesInHand;
    
    private int numberOfPlayers;
    
    private final IPlayerStrategy strategy;
    
    private State state;
    
    private final HashMap<String, Score> otherPlayerNames;
    private Board board;
    
    /**
     * the constructor is a function of a PlayerStrategy.  The strategy is saved and if the play needs 
     * to take a turn, it delegates the function making placements on the board to the strategy
     */
    public Player (IPlayerStrategy strategy) {
        this.score = new Score();
        this.strategy = strategy;
        this.tilesInHand = new ArrayList<Tile>();
        this.state = State.BeforeGameStart;
        this.otherPlayerNames = new HashMap<String, Score> ();
    }
    
    
    @Override
    public String toString() {
        String str = "Player: ";
        for (Tile tile : tilesInHand) {
            str += tile + ", ";
        }
        str += "\n" + score.toString();
        return str;
    }

    /**
     * provides the number of players and the player hand to the player.
     * Can only be called once.
     */
    @Override
    @Requires({
    	"numberOfPlayers >= 2",
        "numberOfPlayers <= 6",
        "tiles != null",
        "tiles.size() == 6",
        "otherPlayerNames != null",
        "otherPlayerNames.size() == numberOfPlayers - 1",
    	"state == State.BeforeGameStart"
    })
    public void acceptNumberOfPlayersAndHand(int numberOfPlayers, List<Tile> tiles) {
        this.numberOfPlayers = numberOfPlayers;
        this.tilesInHand.addAll(tiles);
        this.state = State.AfterGameStart;
        
        board = new Board(numberOfPlayers);
    }
    
    /**
     * populate list of opponents
     */
    public void acceptPlayerNames(List<String> playerNames) {
    	for (String name : playerNames) {
    		this.otherPlayerNames.put(name, new Score());
    	}
    }
    
    /**
     * allows the player to take a turn, using the current placements or the turn object 
     * to make an intelligent move
     */
    @Override
    @Requires({
    	"turn != null",
    	"currentPlacements != null"
    })
    public void take(Turn turn, List<Placement> currentPlacements) throws StrategyException {
    	board = new Board(numberOfPlayers);
    	for (Placement placement : currentPlacements)
    		board.place(placement);
    	while (turn.allowedToPlaceMoreTiles()) {
    		Placement placement;
    		try {
    			placement = strategy.getAction(board, tilesInHand, score);
    		}
    		catch (ContractAssertionError e) {
    			throw new StrategyException("Strategy too short");
    		} 
    		// could catch all exceptions here and/or do validation on the placement, if the player does not trust 
    		// the strategy

    		Score placementScore = turn.scorePlacement(placement);
    		Tile tile = turn.placeTile(placement);
    		board.place(placement);
    		tilesInHand.remove(placement.getTile());
    		
    		if (!tile.isNull())
    			tilesInHand.add(tile);
    		score.add(placementScore);
    	}

    	if (turn.canRerack() && strategy.wantToRerack(board, turn.getHand(), score))
    		turn.rerack();
    	tilesInHand = turn.getHand();
    }

    public Score getScore() {
    	return score.deepCopy();
    }
    
    public Tile[] getTilesInHand() {
    	return tilesInHand.toArray(new Tile[0]);
    }

    public Board getBoard() {
    	return board;
    }

    /**
     * Receive and process a GameUpdate for an opposing player
     */
    public void receiveUpdate(GameUpdate update) {
    	if (!otherPlayerNames.containsKey(update.getName()))
    		return; 
    	for(Placement placement : update.getPlacements()) {    		
    		try{
    			Score scoreForTurn = board.calculate(placement);
    			Score previousScore = otherPlayerNames.get(update.getName());
    			board.place(placement);
    			previousScore.add(scoreForTurn);	      
    		}
    		catch(ContractAssertionError e){
    			System.out.println("Error in recieveUpdate:\nBad Placement:"+ placement +"\nError Message:" + e);
    		}
    	}
    }
}
