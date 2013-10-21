package ingenious.player.test;

import java.util.ArrayList;
import java.util.List;

import com.google.java.contract.ContractAssertionError;

import junit.framework.TestCase;
import ingenious.admin.TileBag;
import ingenious.board.Board;
import ingenious.board.Color;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.StateException;
import ingenious.board.Tile;
import ingenious.player.Player;
import ingenious.player.StrategyException;
import ingenious.strategy.PlayerStrategy;
import ingenious.turn.Turn;

/**
 * Unit tests for Board class
 */
public class PlayerTest extends TestCase {
    private Turn firstTurn;
    private Turn notFirstTurn;
    
    private ArrayList<Placement> notFirstTurnBoardPlacements;
    
    @Override
    protected void setUp() throws Exception {
    	Board board = new Board(2);
        ArrayList<Tile> hand = new ArrayList<Tile> ();
        hand.add(new Tile(Color.GREEN, Color.YELLOW));
        hand.add(new Tile(Color.GREEN, Color.YELLOW));
        hand.add(new Tile(Color.GREEN, Color.YELLOW));
        hand.add(new Tile(Color.GREEN, Color.YELLOW));
        hand.add(new Tile(Color.GREEN, Color.YELLOW));
        hand.add(new Tile(Color.GREEN, Color.YELLOW));
        Score playerScore = new Score();
        TileBag bag = new TileBag(4);
        firstTurn = new Turn(board, hand, playerScore, bag);
    	
        Board board2 = new Board(2);
        notFirstTurnBoardPlacements = new ArrayList<Placement>();
        notFirstTurnBoardPlacements.add(new Placement(Color.GREEN, 4, 4, Color.GREEN, 3, 3));
        notFirstTurnBoardPlacements.add(new Placement(Color.GREEN, 5, 26, Color.GREEN, 4, 20));
        for (Placement placement : notFirstTurnBoardPlacements)
        	board2.place(placement);
        ArrayList<Tile> hand2 = new ArrayList<Tile> ();
        hand2.add(new Tile(Color.RED, Color.GREEN));
        hand2.add(new Tile(Color.RED, Color.GREEN));
        hand2.add(new Tile(Color.RED, Color.GREEN));
        hand2.add(new Tile(Color.RED, Color.GREEN));
        hand2.add(new Tile(Color.RED, Color.GREEN));
        hand2.add(new Tile(Color.RED, Color.GREEN));

        Score playerScore2 = new Score();
        playerScore2.setValue(Color.GREEN, 17);
        TileBag bag2 = new TileBag(4);
        notFirstTurn = new Turn(board2, hand2, playerScore2, bag2);
    }
    
    /**
     * Not enough actions for a valid turn
     */
    public void testShortStrategy() {
    	ArrayList<Placement> shortActions = new ArrayList<Placement>();
    	PlayerStrategy shortStrategy = new PlayerStrategy(shortActions);
    	Player shortPlayer = new Player(shortStrategy);
    	
    	try {
    		ArrayList<Tile> hand = new ArrayList<Tile> ();
    		hand.add(new Tile(Color.GREEN, Color.YELLOW));
    		hand.add(new Tile(Color.GREEN, Color.YELLOW));
    		hand.add(new Tile(Color.GREEN, Color.YELLOW));
    		hand.add(new Tile(Color.GREEN, Color.YELLOW));
    		hand.add(new Tile(Color.GREEN, Color.YELLOW));
    		hand.add(new Tile(Color.GREEN, Color.YELLOW));
    		shortPlayer.acceptNumberOfPlayersAndHand(2, hand);
    		
    		shortPlayer.take(firstTurn, new ArrayList<Placement>());
    		fail ("Should throw a strategy exception for a short strategy");
    	}
    	catch (StateException e) {
    		fail("Should not throw state exception for a short strategy");
    	}
    	catch (StrategyException e) {
    		// passed
    	}
    }
    
    /**
     * Not enough actions for a valid turn
     */
    public void testShortStrategy2() {
    	ArrayList<Placement> shortActions = new ArrayList<Placement>();
    	shortActions.add(new Placement(Color.RED, 4, 5, Color.GREEN, 5, 7));
    	PlayerStrategy shortStrategy = new PlayerStrategy(shortActions);
    	Player shortPlayer = new Player(shortStrategy);

    	try {
    		ArrayList<Tile> hand = new ArrayList<Tile> ();
    		hand.add(new Tile(Color.RED, Color.GREEN));
    		hand.add(new Tile(Color.RED, Color.GREEN));
    		hand.add(new Tile(Color.RED, Color.GREEN));
    		hand.add(new Tile(Color.RED, Color.GREEN));
    		hand.add(new Tile(Color.RED, Color.GREEN));
    		hand.add(new Tile(Color.RED, Color.GREEN));
    		shortPlayer.acceptNumberOfPlayersAndHand(2, hand);
    		try {
    			shortPlayer.take(notFirstTurn, notFirstTurnBoardPlacements);
    			fail ("Should throw a strategy exception for a short strategy");
    		}
    		catch (ContractAssertionError e) {
    			// passed
    		}
    	}
    	catch (StateException e) {
    		fail("Should not throw state exception for a short strategy");
    	}
    	catch (StrategyException e) {
    		// passed
    	}
    }
}
