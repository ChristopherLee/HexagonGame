package ingenious.turn.test;

import java.util.ArrayList;
import java.util.List;

import com.google.java.contract.ContractAssertionError;

import junit.framework.TestCase;
import ingenious.admin.TileBag;
import ingenious.board.Board;
import ingenious.board.Color;
import ingenious.board.NullTile;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.StateException;
import ingenious.board.Tile;
import ingenious.turn.Turn;

/**
 * Unit tests for Board class
 */
public class TurnTest extends TestCase {
    Turn turn;
    Turn turn2;
    Turn extraTurn;
    Turn adminNoTilesTurn;
    ArrayList<Tile> adminNoTilesHand;
    Turn rerackTurn1;
    Turn rerackTurn2;
    @Override
    protected void setUp() throws Exception {
    	   	
    	Board board = new Board(2);
        ArrayList<Tile> hand = new ArrayList<Tile> ();
        hand.add(new Tile(Color.GREEN, Color.YELLOW));
        hand.add(new Tile(Color.GREEN, Color.YELLOW));
        hand.add(new Tile(Color.GREEN, Color.YELLOW));
        Score playerScore = new Score();
        
        ArrayList<Tile> bagList = new ArrayList<Tile> ();
        bagList.add(new Tile(Color.RED, Color.GREEN));
        bagList.add(new Tile(Color.GREEN, Color.BLUE));
        bagList.add(new Tile(Color.RED, Color.GREEN));
        bagList.add(new Tile(Color.RED, Color.GREEN));
        TileBag bag = new TileBag(bagList);
        turn = new Turn(board, hand, playerScore, bag);
        
        Board board2 = new Board(2);
        ArrayList<Tile> hand2 = new ArrayList<Tile> ();
        hand2.add(new Tile(Color.GREEN, Color.YELLOW));
        hand2.add(new Tile(Color.BLUE, Color.YELLOW));
        hand2.add(new Tile(Color.GREEN, Color.RED));
        Score playerScore2 = new Score();
        ArrayList<Tile> bagList2 = new ArrayList<Tile> ();
        bagList2.add(new Tile(Color.RED, Color.GREEN));
        bagList2.add(new Tile(Color.GREEN, Color.BLUE));
        bagList2.add(new Tile(Color.RED, Color.GREEN));
        bagList2.add(new Tile(Color.RED, Color.GREEN));
        TileBag bag2 = new TileBag(bagList2);
        turn2 = new Turn(board2, hand2, playerScore2, bag2);
        
        Board extraBoard = new Board(2);
        extraBoard.place(new Placement(Color.RED, 4, 8, Color.GREEN, 5, 11));
        extraBoard.place(new Placement(Color.RED, 4, 4, Color.GREEN, 3, 3));
        ArrayList<Tile> extraHand = new ArrayList<Tile> ();
        extraHand.add(new Tile(Color.GREEN, Color.YELLOW));
        extraHand.add(new Tile(Color.GREEN, Color.YELLOW));
        extraHand.add(new Tile(Color.GREEN, Color.YELLOW));
        Score extraPlayerScore = new Score();
        extraPlayerScore.setValue(Color.GREEN, 17);
        ArrayList<Tile> extraBagList = new ArrayList<Tile> ();
        extraBagList.add(new Tile(Color.RED, Color.GREEN));
        extraBagList.add(new Tile(Color.BLUE, Color.GREEN));
        extraBagList.add(new Tile(Color.RED, Color.GREEN));
        extraBagList.add(new Tile(Color.RED, Color.GREEN));
        TileBag extraBag = new TileBag(extraBagList);
        extraTurn = new Turn(extraBoard, extraHand, extraPlayerScore, extraBag);
        
        Board adminNoTilesBoard = new Board(2);
        adminNoTilesBoard.place(new Placement(Color.RED, 4, 8, Color.GREEN, 5, 11));
        adminNoTilesBoard.place(new Placement(Color.RED, 4, 4, Color.GREEN, 3, 3));
        adminNoTilesHand = new ArrayList<Tile> ();
        adminNoTilesHand.add(new Tile(Color.GREEN, Color.YELLOW));
        adminNoTilesHand.add(new Tile(Color.GREEN, Color.YELLOW));
        extraHand.add(new Tile(Color.GREEN, Color.YELLOW));
        Score adminNoTilesPlayerScore = new Score();
        TileBag adminNoTilesBag = new TileBag(0);
        adminNoTilesTurn = new Turn(adminNoTilesBoard, adminNoTilesHand, adminNoTilesPlayerScore, adminNoTilesBag);

        Board rerackBoard = new Board(2);
        ArrayList<Tile> rerackHand = new ArrayList<Tile> ();
        rerackHand.add(new Tile(Color.GREEN, Color.YELLOW));
        rerackHand.add(new Tile(Color.GREEN, Color.YELLOW));
        rerackHand.add(new Tile(Color.GREEN, Color.YELLOW));
        rerackHand.add(new Tile(Color.GREEN, Color.YELLOW));
        Score rerackPlayerScore = new Score();
        rerackPlayerScore.addValue(Color.BLUE, 1);
        rerackPlayerScore.addValue(Color.RED, 2);
        rerackPlayerScore.addValue(Color.GREEN, 2);
        rerackPlayerScore.addValue(Color.YELLOW, 2);
        rerackPlayerScore.addValue(Color.ORANGE, 2);
         
        ArrayList<Tile> rerackBagList = new ArrayList<Tile> ();
        rerackBagList.add(new Tile(Color.PURPLE, Color.GREEN));
        rerackBagList.add(new Tile(Color.GREEN, Color.BLUE));
        rerackBagList.add(new Tile(Color.RED, Color.GREEN));
        rerackBagList.add(new Tile(Color.RED, Color.GREEN));
        rerackBagList.add(new Tile(Color.RED, Color.GREEN));
        rerackBagList.add(new Tile(Color.RED, Color.GREEN));
        rerackBagList.add(new Tile(Color.RED, Color.GREEN));
        TileBag rerackBag = new TileBag(rerackBagList);
        rerackTurn1 = new Turn(rerackBoard, rerackHand, rerackPlayerScore, rerackBag);
        
        ArrayList<Tile> rerackHand2 = new ArrayList<Tile> ();
        rerackHand2.add(new Tile(Color.GREEN, Color.YELLOW));
        rerackHand2.add(new Tile(Color.GREEN, Color.YELLOW));
        rerackHand2.add(new Tile(Color.GREEN, Color.YELLOW));
        rerackHand2.add(new Tile(Color.GREEN, Color.YELLOW));
        rerackHand2.add(new Tile(Color.GREEN, Color.YELLOW));
        rerackHand2.add(new Tile(Color.GREEN, Color.YELLOW));
        rerackTurn2 = new Turn(rerackBoard, rerackHand2, rerackPlayerScore, rerackBag);
    }
    
    /**
     * Place tile not adjacent to any occupied cells
     */
    public void testPlaceFirstTurn() {
        Placement placement = new Placement(Color.GREEN, 0, 0, Color.YELLOW, 1, 1);
        try {
            turn.placeTile(placement);
            fail("It's not legal to put tile without any adjacent occupied cells");
        } catch (ContractAssertionError e) {
            // passed
        }
    }
    
    /**
     * Place tile on an occupied cell
     */
    public void testPlaceIllegalTile2() {
    	Placement placement = new Placement(Color.GREEN, 5, 25, Color.YELLOW, 4, 20);
        try {
        	turn.placeTile(placement);
            fail("It's not legal to put tile on occupied cells");
        } catch (ContractAssertionError e) {
            // passed
        }
    }
    
    /**
     * Place tile where cells are not adjacent
     */
    public void testPlaceIllegalTile3() {
    	Placement placement = new Placement(Color.GREEN, 4, 20, Color.YELLOW, 3, 16);
        try {
        		turn.placeTile(placement);
        		fail("It's not legal to put tile that does not contain adjacent cells");
        } catch (ContractAssertionError e) {
            // passed
        }
    }
    
    /**
     * Place 2 tiles when can only place one
     */
    public void testPlaceExtraTile() {
    	Placement placement1 = new Placement(Color.GREEN, 5, 26, Color.YELLOW, 5, 27);
    	Placement placement2 = new Placement(Color.GREEN, 5, 19, Color.YELLOW, 5, 18);
    	
        try {
        	turn.placeTile(placement1);
        		turn.placeTile(placement2);
        		fail("Turn allowed user to place more tiles than allowed onto the board");
        } catch (ContractAssertionError e) {
            // passed
        }
    }
    
    /**
     * Place 18 point rule, given 2 turns
     */
    public void testPlaceExtraTile2() {
    	Placement placement1 = new Placement(Color.GREEN, 4, 5, Color.YELLOW, 5, 7);
    	Placement placement2 = new Placement(Color.GREEN, 5, 19, Color.YELLOW, 5, 18);
    	
        try {
        	Tile expectedTile1 = new Tile(Color.RED, Color.GREEN);
        	Tile expectedTile2 = new Tile(Color.BLUE, Color.GREEN);
        	List<Tile> expectedHand = new ArrayList<Tile> ();
            expectedHand.add(new Tile(Color.GREEN, Color.YELLOW));
            expectedHand.add(new Tile(Color.RED, Color.GREEN));
            expectedHand.add(new Tile(Color.BLUE, Color.GREEN));
        	
        	Tile tile1 = extraTurn.placeTile(placement1);
        	assertEquals(expectedTile1, tile1);
        	Tile tile2 = extraTurn.placeTile(placement2);
        	assertEquals(expectedTile2, tile2);
        	
        	assertTrue(extraTurn.getHand().contains(expectedHand.get(0)));
        	assertTrue(extraTurn.getHand().contains(expectedHand.get(1)));
        	assertTrue(extraTurn.getHand().contains(expectedHand.get(2)));
        	// should check that it does not contain tile in placement1 and placement2
        } catch (ContractAssertionError e) {
        	fail("Player earned an extra turn but is not allowed to place another tile");
        }
    }
    
    /**
     * place a first tile on the board, check that the hand removes the tile and adds the new tile
     */
    public void testPlaceNormal() {
    	Placement placement = new Placement(Color.GREEN, 4, 4, Color.RED, 5, 4);
    	    	
        try {
        	Tile expectedTile = new Tile(Color.RED, Color.GREEN);
        	List<Tile> expectedHand = new ArrayList<Tile> ();
            expectedHand.add(new Tile(Color.GREEN, Color.YELLOW));
            expectedHand.add(new Tile(Color.BLUE, Color.YELLOW));
            expectedHand.add(expectedTile);
        	
        	Tile tile1 = turn2.placeTile(placement);
        	assertEquals(expectedTile, tile1);
        	assertTrue(turn2.getHand().contains(expectedHand.get(0)));
        	assertTrue(turn2.getHand().contains(expectedHand.get(1)));
        	assertTrue(turn2.getHand().contains(expectedHand.get(2)));
        	// should check that it does not contain tile in placement
        } catch (ContractAssertionError e) {
        	fail("Error thrown on valid turn");
        }
    }
    
    /**
     * Test tile equals
     */
    public void testTileEquals() {
    	Tile tile1 = new Tile(Color.RED, Color.GREEN);
    	Tile tile2 = new Tile(Color.RED, Color.GREEN);
    	Tile tile3 = new Tile(Color.GREEN, Color.RED);
    	assertTrue(tile1.equals(tile2));
    	assertTrue(tile1.equals(tile3));
    	assertEquals(tile1, tile3);
    }
    
    /**
     * Admin gives nullTiles when no more tiles left
     */
    public void testReceiveNullTiles() {
    	Placement placement1 = new Placement(Color.GREEN, 4, 5, Color.YELLOW, 5, 7);
    	
        try {
        	Tile expectedTile1 = new NullTile();
        	Tile tile1 = adminNoTilesTurn.placeTile(placement1);
        	assertEquals(expectedTile1, tile1);
        } catch (ContractAssertionError e) {
        	fail("Should not throw an error, return a nulltile");
        }
    }
    
    /**
     * Player tries to place a tile that he does not have
     */
    
    public void testPlaceTileNotInHand() {
    	Placement placement1 = new Placement(Color.GREEN, 4, 5, Color.BLUE, 5, 7);
    	
        try {
        	Tile tile1 = adminNoTilesTurn.placeTile(placement1);
        	fail("Cannot place a tile that you do not have in your hand");
        } catch (ContractAssertionError e) {
        	//passed
        }
    }
   
    /**
     * Player tries to place an invalid move, set kick flag true, cannot modify turn anymore
     */
    public void testKickFlag() {
    	
    }
    
    // admin has no tiles after placeTile, also breaks that player has only 5 tiles remaining
    public void testRerackNoAdminTiles() {
	Placement placement1 = new Placement(Color.GREEN, 4, 5, Color.BLUE, 5, 7);

        try {
                Tile tile1 = adminNoTilesTurn.placeTile(placement1);
                adminNoTilesTurn.rerack();
                fail("Cannot re-rack when the admin has less than 6 tiles");
        } catch (ContractAssertionError e) {
		// passed                
        }

    }
    // admin has 4 tiles
     public void testRerackWith4AdminTiles() {
        Placement placement1 = new Placement(Color.GREEN, 4, 4, Color.YELLOW, 5, 4);

        try {
                Tile tile1 = turn.placeTile(placement1);
                turn.rerack();
                fail("Cannot re-rack when the admin has less than 6 tiles");
        } catch (ContractAssertionError e) {
		//passed
        }
    }
    // player has less than 6 tiles
    public void testRerackWithMoreThan6AdminTiles1() {
        Placement placement1 = new Placement(Color.GREEN, 4, 4, Color.YELLOW, 5, 4);

        try {
                Tile tile1 = rerackTurn1.placeTile(placement1);
                rerackTurn1.rerack();
                fail("Cannot re-rack when the player has less than 6 tiles");
        } catch (ContractAssertionError e) {
                //passed
        }
    }

    // player has a tile that contains his lowest color score, so should not be able to rerack
    public void testRerackWithMoreThan6AdminTiles2() {
        Placement placement1 = new Placement(Color.GREEN, 4, 0, Color.YELLOW, 3, 0);

        try {
                Tile tile1 = rerackTurn2.placeTile(placement1);
                List<Tile> handBeforeRerack = rerackTurn2.getHand();
                assertEquals(6, handBeforeRerack.size());
                rerackTurn2.rerack();
                                
                fail("Cannot re-rack when the player has a tile that contains his lowest color score");
        } catch (ContractAssertionError e) {
                //passed
        }
    }
    
    // normal rerack
    public void testRerackWithMoreThan6AdminTiles3() {
        Placement placement1 = new Placement(Color.GREEN, 4, 12, Color.YELLOW, 5, 14);

        try {
                Tile tile1 = rerackTurn2.placeTile(placement1);
                List<Tile> handBeforeRerack = rerackTurn2.getHand();
                assertEquals(6, handBeforeRerack.size());
                rerackTurn2.rerack();
                List<Tile> handAfterRerack = rerackTurn2.getHand();
                assertEquals(6, handAfterRerack.size());
                assertFalse(hasTileInCommon(handBeforeRerack, handAfterRerack));                
        } catch (ContractAssertionError e) {
        		System.out.println(e);
                fail("Should be a good rerack");
        }
    }

    
    /**
     * if any tile in list1 is the same Object as in list2, return true
     */
    private boolean hasTileInCommon (List<Tile> list1, List<Tile> list2) {
    	for (Tile tile1 : list1) {
    		for (Tile tile2 : list2) {
    			if (tile1 == tile2)
    				return true;
    		}
    	}
    	return false;
    }
}

