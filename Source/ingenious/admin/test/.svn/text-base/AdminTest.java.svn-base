package ingenious.admin.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.java.contract.ContractAssertionError;

import ingenious.admin.Administrator;
import ingenious.admin.GameResult;
import ingenious.admin.PlayerRecord;
import ingenious.board.Color;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.StateException;
import ingenious.board.Tile;
import ingenious.player.BadPlayer;
import ingenious.player.Player;
import ingenious.strategy.HighestScoreStrategy;
import ingenious.strategy.IncreaseSmallestScoreStrategy;
import ingenious.strategy.InvalidMoveOnExtraTurnStrategy;
import ingenious.strategy.NonFirstTurnInvalidStrategy;
import ingenious.strategy.PlayerStrategy;
import ingenious.strategy.RandomMoveStrategy;
import ingenious.strategy.RerackEveryTurnStrategy;
import junit.framework.TestCase;

public class AdminTest extends TestCase {
    
    @Override
    protected void setUp() throws Exception {

    }
    
    @Override
    protected void tearDown() throws Exception {
        
    }
    
    public void testInvalidNumberOfPlayers() {
        try {
        	Administrator admin = new Administrator(1);
        	fail("Failed to detect invalid number of players");
        }
        catch (ContractAssertionError e) {
        	//passed
        }
    }
    public void testAcceptedRegistration() {
        Administrator admin = new Administrator(2);
        Player player = new Player(new RandomMoveStrategy());
        boolean registered = admin.register("player-1", player);
        assertTrue(registered);
    }
    
    public void testSameNameRegistration() {
        Administrator admin = new Administrator(2);
        Player player = new Player(new RandomMoveStrategy());
        
        boolean registered = admin.register("player-1", player);
        assertTrue(registered);
        
        boolean enough = admin.isEnoughPlayersRegistered();
        assertFalse(enough);
        
        boolean registered2 = admin.register("player-1", player);
        assertTrue(registered2);
        
        enough = admin.isEnoughPlayersRegistered();
        assertTrue(enough);
        
        try {
        	registered = admin.register("player-3", player);
        	fail("Should throw contract error on too many players registered");
        }
        catch (ContractAssertionError e) {
        	// passed
        }
    }
    
    public void testStartGameTooEarly() {
        Administrator admin = new Administrator(2);
        Player player = new Player(new RandomMoveStrategy());
        
        boolean registered = admin.register("player-1", player);
        assertTrue(registered);
        try {
        	if (admin.isEnoughPlayersRegistered()) {
        		admin.runGame();
        		fail("Ran game with only one player");
        	}
        }
        catch (StateException e) {
        	// passed
        }
    }
    
    /**
     * Test game with 2 normally functioning players
     */
    public void testNormalGame1() {
        Administrator admin = new Administrator(2);
        Player player1 = new Player(new HighestScoreStrategy());
        Player player2 = new Player(new IncreaseSmallestScoreStrategy());
        String player1Name = "player-1";
        String player2Name = "player-2";
        
        admin.register(player1Name, player1);
        admin.register(player2Name, player2);
       	try {
       		GameResult result = admin.runGame();
       		assertEquals(2, result.getPlayerRankings().size());
       	}
       	catch (StateException e) {
       		fail("Should not throw an error in runGame");
       	}
    }
    
    /**
     * Test game with 3 normally functioning players
     */
    public void testNormalGame2() {
        Administrator admin = new Administrator(3);
        Player player1 = new Player(new RandomMoveStrategy());
        Player player2 = new Player(new HighestScoreStrategy());
        Player player3 = new Player(new IncreaseSmallestScoreStrategy());
        String player1Name = "player-1";
        String player2Name = "player-2";
        String player3Name = "player-3";
        
        admin.register(player1Name, player1);
        admin.register(player2Name, player2);
        admin.register(player3Name, player3);
       	try {
       		GameResult result = admin.runGame();
       		assertEquals(3, result.getPlayerRankings().size());
       	}
       	catch (StateException e) {
       		fail("Should not throw an error in runGame");
       	}
    }   
     
    /**
     * player-2 will be kicked after the first turn, player-1 will finish the game successfully
     */
    public void testKickPlayer1() {
        Administrator admin = new Administrator(2);
        Player player1 = new Player(new IncreaseSmallestScoreStrategy());
        Player player2 = new Player(new NonFirstTurnInvalidStrategy());
        String player1Name = "player-1";
        String player2Name = "player-2";
        
        admin.register(player1Name, player1);
        admin.register(player2Name, player2);
       	try {
       		GameResult result = admin.runGame();
       		int expected = 1;
       		assertEquals(expected, result.getPlayerRankings().size());
       	}
       	catch (StateException e) {
       		fail("Should not throw an error in runGame");
       	}
    }   
    
    /**
     * player-2 will try to rerack whenever possible
     */
    public void testRerack1() {
        Administrator admin = new Administrator(2);
        Player player1 = new Player(new IncreaseSmallestScoreStrategy());
        Player player2 = new Player(new RerackEveryTurnStrategy());
        String player1Name = "player-1";
        String player2Name = "player-2";
        
        admin.register(player1Name, player1);
        admin.register(player2Name, player2);
       	try {
       		GameResult result = admin.runGame();
       		int expected = 2;
       		assertEquals(expected, result.getPlayerRankings().size());
       	}
       	catch (StateException e) {
       		fail("Should not throw an error in runGame");
       	}
    }  
    
    /**
     * player-2 will try to rerack whenever possible
     */
    public void testRerack2() {
        Administrator admin = new Administrator(2);
        Player player1 = new Player(new IncreaseSmallestScoreStrategy());
        BadPlayer player2 = new BadPlayer(new RerackEveryTurnStrategy());
        String player1Name = "player-1";
        String player2Name = "player-2";

        admin.register(player1Name, player1);
        admin.register(player2Name, player2);
        try {
                GameResult result = admin.runGame();
                int expected = 1;
                assertEquals(expected, result.getPlayerRankings().size());
        }
        catch (StateException e) {
                fail("Should not throw an error in runGame");
        }
    }
 
    /**
     * player-2 will make a bad placement on an extra turn
     */
    public void testBadMoveOnExtraTurn() {
        Administrator admin = new Administrator(2);
        Player player1 = new Player(new IncreaseSmallestScoreStrategy());
        Player player2 = new Player(new InvalidMoveOnExtraTurnStrategy());
        String player1Name = "player-1";
        String player2Name = "player-2";
        
        admin.register(player1Name, player1);
        admin.register(player2Name, player2);
       	try {
       		GameResult result = admin.runGame();
       		int expected = 1;
       		assertEquals(expected, result.getPlayerRankings().size());
       	}
       	catch (StateException e) {
       		fail("Should not throw an error in runGame");
       	}
    }   
    
}
