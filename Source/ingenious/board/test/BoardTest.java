/** 
 * BoardTest.java
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
 *  Feb 2, 2011        Chunzhao, Zheng        Initial
 *  Feb 2, 2011        Christopher, Lee       Initial
 *********************************************************************
*/
package ingenious.board.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.java.contract.ContractAssertionError;

import junit.framework.TestCase;

import ingenious.board.Board;
import ingenious.board.Cell;
import ingenious.board.Color;
import ingenious.board.Coordinate;
import ingenious.board.Placement;
import ingenious.board.PlacementLocation;
import ingenious.board.Score;
import ingenious.board.StateException;

/**
 * Unit tests for Board class
 */
public class BoardTest extends TestCase {

    private Board board;
    private Board board6;
    
    @Override
    protected void setUp() throws Exception {
        board = new Board(2);
        board6 = new Board(6);
    }
    
    /**
     * Place tile not adjacent to any occupied cells
     */
    public void testPlaceIllegalTile1() {
        Placement placement = new Placement(Color.GREEN, 0, 0, Color.BLUE, 1, 1);
        try {
       		board.place(placement);
            fail("It's not legal to put tile without any adjacent occupied cells");
        } catch (StateException e) {
            // passed
        }
        catch (ContractAssertionError e) {
        	System.out.println("ContractAssertionError");
        }
        	
    }
    
    /**
     * Place tile on an occupied cell
     */
    public void testPlaceIllegalTile2() {
    	Placement placement = new Placement(Color.GREEN, 5, 25, Color.BLUE, 4, 20);
        try {
        	board.place(placement);
            fail("It's not legal to put tile on occupied cells");
        } catch (ContractAssertionError e) {
            // passed
        }
    }
    
    /**
     * Place tile where cells are not adjacent
     */
    public void testPlaceIllegalTile3() {
    	Placement placement = new Placement(Color.GREEN, 4, 20, Color.BLUE, 3, 16);
        try {
            	board.place(placement);
            	fail("It's not legal to put tile that does not contain adjacent cells");
        } catch (ContractAssertionError e) {
            // passed
        }
    }
    
    /**
     * First turn placement cannot be adjacent to another players tile
     */
    public void testPlaceFirstTile1() {
    	Placement placement1 = new Placement(Color.GREEN, 5, 26, Color.BLUE, 5, 27);
        Score score1 = null;
        try {
        		score1 = board.calculate(placement1);
        		board.place(placement1);
        } catch (ContractAssertionError e) {
            fail(e.getMessage());
        }
        assertEquals(1, (int)score1.get(Color.GREEN));
        assertEquals(0, (int)score1.get(Color.BLUE));
        
        Placement placement2 = new Placement(Color.GREEN, 5, 28, Color.BLUE, 4, 22);
        Score score2 = null;
        try {
        		score2 = board.calculate(placement2);
        		board.place(placement2);
        		fail("It's not legal to place a first turn tile adjacent to another player's tile");
        } catch (ContractAssertionError e) {
            //passed
        }
    }
    
    /**
     * First turn placement cannot be adjacent to another players tile
     */
    public void testPlaceFirstTile2() {
    	Placement placement1 = new Placement(Color.GREEN, 5, 26, Color.BLUE, 5, 27);
        Score score1 = null;
        try {
        		score1 = board.calculate(placement1);
        		board.place(placement1);
        } catch (ContractAssertionError e) {
            fail(e.getMessage());
        }
        assertEquals(1, (int)score1.get(Color.GREEN));
        assertEquals(0, (int)score1.get(Color.BLUE));
        
        Placement placement2 = new Placement(Color.GREEN, 5, 24, Color.BLUE, 5, 23);
        Score score2 = null;
        try {
        		score2 = board.calculate(placement2);
        		board.place(placement2);
        		fail("It's not legal to place a first turn tile adjacent to the same starting hexagon as another player");
        } catch (ContractAssertionError e) {
            //passed
        }
    }
    
    
    /**
     * Successfully place first turn tiles
     */
    public void testPlaceFirstTile3() {
    	Placement placement = new Placement(Color.GREEN, 5, 26, Color.BLUE, 5, 27);
        Score score = null;
        try {
        		score = board.calculate(placement);
        		board.place(placement);
        } catch (ContractAssertionError e) {
            fail(e.getMessage());
        }
        
        Score expected = new Score();
        expected.setValue(Color.GREEN, 1);
        assertEquals(expected, score);
        
        Placement placement2 = new Placement(Color.GREEN, 5, 19, Color.BLUE, 5, 18);
        Score score2 = null;
        try {
        		score2 = board.calculate(placement2);
        		board.place(placement2);
        } catch (ContractAssertionError e) {
            fail(e.getMessage());
        }
        Score expected2 = new Score();
        expected2.setValue(Color.RED, 1);
        assertEquals(expected2, score2);
    }
    
    /**
     * Place a tile after the first turn on the board
     */
    public void testPlaceNormalTile() {
    	Placement placement1 = new Placement(Color.GREEN, 5, 26, Color.BLUE, 5, 27);
    	Placement placement2 = new Placement(Color.GREEN, 5, 19, Color.BLUE, 5, 18);
    	
        try {
        		board.place(placement1);
        		board.place(placement2);
        } catch (ContractAssertionError e) {
            fail(e.getMessage());
        }
              
        Placement placement3 = new Placement(Color.GREEN, 4, 15, Color.BLUE, 3, 12);
        Score score = null;
        try {
        		score = board.calculate(placement3);
        		board.place(placement3);
        } catch (ContractAssertionError e) {
            fail(e.getMessage());
        }
        Score expected = new Score();
        expected.setValue(Color.BLUE, 1);
        expected.setValue(Color.GREEN, 1);
        assertEquals(expected, score);
    }
    
    /**
     * Place tiles in a row
     */
    public void testPlaceNormalTile2() {
    	Placement placement1 = new Placement(Color.GREEN, 5, 26, Color.BLUE, 5, 27);
    	Placement placement2 = new Placement(Color.GREEN, 5, 19, Color.BLUE, 5, 18);
    	
        try {
        		board.place(placement1);
        		board.place(placement2);
        } catch (ContractAssertionError e) {
            fail(e.getMessage());
        }
    	
        Placement placement3 = new Placement(Color.GREEN, 4, 20, Color.BLUE, 4, 21);
        Score score3 = null;
        try {
        		score3 = board.calculate(placement3);
        		board.place(placement3);
        } catch (ContractAssertionError e) {
            fail(e.getMessage());
        }
        Score expected3 = new Score();
        expected3.setValue(Color.BLUE, 1);
        expected3.setValue(Color.GREEN, 3);
        assertEquals(expected3, score3);
        
        Placement placement4 = new Placement(Color.GREEN, 4, 19, Color.BLUE, 3, 15);
        Score score4 = null;
        try {
        		score4 = board.calculate(placement4);
        		board.place(placement4);
        } catch (ContractAssertionError e) {
            fail(e.getMessage());
        }
        Score expected4 = new Score();
        expected4.setValue(Color.BLUE, 2);
        expected4.setValue(Color.GREEN, 4);
        assertEquals(expected4, score4);
    }
    
    /**
     * Place tiles in a row
     */
    public void testBoardAvailablePositions() {
    	assertTrue(board6.hasAvailablePositionsForTile());
    	List<PlacementLocation> expected = new ArrayList<PlacementLocation>();
    	board6.place(new Placement(Color.RED, 5, 21, Color.RED, 4, 16));
    	board6.place(new Placement(Color.RED, 4, 12, Color.RED, 5, 14));
    	board6.place(new Placement(Color.RED, 4, 8, Color.RED, 5, 11));
    	board6.place(new Placement(Color.RED, 5, 4, Color.RED, 4, 4));
    	board6.place(new Placement(Color.RED, 5, 1, Color.RED, 4, 0));
    	
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 31)), board6.getCell(new Coordinate(5, 26))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(5, 26)), board6.getCell(new Coordinate(4, 20))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(4, 20)), board6.getCell(new Coordinate(5, 24))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(5, 24)), board6.getCell(new Coordinate(6, 29))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 29)), board6.getCell(new Coordinate(6, 30))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 30)), board6.getCell(new Coordinate(6, 31))));
    	
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 29)), board6.getCell(new Coordinate(6, 28))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(5, 24)), board6.getCell(new Coordinate(6, 28))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(5, 24)), board6.getCell(new Coordinate(5, 23))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(5, 24)), board6.getCell(new Coordinate(4, 19))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(4, 20)), board6.getCell(new Coordinate(4, 19))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(4, 20)), board6.getCell(new Coordinate(3, 15))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(4, 20)), board6.getCell(new Coordinate(4, 21))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(5, 26)), board6.getCell(new Coordinate(4, 21))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(5, 26)), board6.getCell(new Coordinate(5, 27))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(5, 26)), board6.getCell(new Coordinate(6, 32))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 31)), board6.getCell(new Coordinate(6, 32))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 30)), board6.getCell(new Coordinate(7, 34))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 29)), board6.getCell(new Coordinate(7, 33))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 29)), board6.getCell(new Coordinate(7, 34))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 30)), board6.getCell(new Coordinate(7, 35))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 30)), board6.getCell(new Coordinate(7, 36))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 31)), board6.getCell(new Coordinate(7, 36))));
    	expected.add(new PlacementLocation(board6.getCell(new Coordinate(6, 31)), board6.getCell(new Coordinate(7, 37))));
    	
    	
    	for (PlacementLocation location : board6.getAvailablePositionsForTile()) {
    		if (!expected.contains(location))
    			System.out.println("expected does not contain location: " + location);
    	}
    	Set<PlacementLocation> locations = board6.getAvailablePositionsForTile();
    	for (PlacementLocation location : locations) {
    		System.out.println(location);
    	}
    	for (PlacementLocation location : expected) {
    		if (!locations.contains(location))
    			System.out.println("expected2 does not contain location: " + location);
    	}
    	
    	assertEquals(expected, board6.getAvailablePositionsForTile());
    	
    	
    }
    
    public void testPlacementEquals() {
    	Placement p1 = new Placement(Color.RED, 5, 21, Color.RED, 4, 16);
    	Placement p2 = new Placement(Color.RED, 4, 16, Color.RED, 5, 21);
    	assertTrue(p1.equals(p2));
    	
    	Placement p3 = new Placement(Color.RED, 5, 21, Color.GREEN, 4, 16);
    	Placement p4 = new Placement(Color.GREEN, 4, 16, Color.RED, 5, 21);
    	assertTrue(p3.equals(p4));
    	
    	Placement p5 = new Placement(Color.RED, 5, 21, Color.GREEN, 4, 16);
    	Placement p6 = new Placement(Color.GREEN, 5, 21, Color.RED, 4, 16);
    	assertFalse(p5.equals(p6));
    	
    	PlacementLocation p7 = new PlacementLocation(5, 21, 4, 16);
    	PlacementLocation p8 = new PlacementLocation(5, 21, 4, 16);
    	assertTrue(p7.equals(p8));
    	
    	PlacementLocation p9 = new PlacementLocation(4, 16, 5, 21);
    	PlacementLocation p10 = new PlacementLocation(5, 21, 4, 16);
    	assertTrue(p9.equals(p10));
    }
    
    public void testPlacementHashCode() {
    	Placement p1 = new Placement(Color.RED, 5, 21, Color.RED, 4, 16);
    	Placement p2 = new Placement(Color.RED, 4, 16, Color.RED, 5, 21);
    	assertTrue(p1.hashCode() == p2.hashCode());
    	
    	Placement p3 = new Placement(Color.RED, 5, 21, Color.GREEN, 4, 16);
    	Placement p4 = new Placement(Color.GREEN, 4, 16, Color.RED, 5, 21);
    	assertTrue(p3.hashCode() == p4.hashCode());
    	
    	PlacementLocation p7 = new PlacementLocation(5, 21, 4, 16);
    	PlacementLocation p8 = new PlacementLocation(5, 21, 4, 16);
    	assertTrue(p7.hashCode() == p8.hashCode());
    	
    	PlacementLocation p9 = new PlacementLocation(4, 16, 5, 21);
    	PlacementLocation p10 = new PlacementLocation(5, 21, 4, 16);
    	assertTrue(p9.hashCode() == p10.hashCode());
    }
    
}
