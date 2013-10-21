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

import ingenious.board.Board;
import ingenious.board.Cell;
import ingenious.board.Coordinate;
import ingenious.board.Direction;

import junit.framework.TestCase;

/**
 * Unit test for Strategy class
 */
public class CellNeighborTest extends TestCase {

    private Board board;

    @Override
    protected void setUp() throws Exception {
        board = new Board(6);
    }

    public void testInnerAngle1() {
    	assertEquals(0, Board.findInnerAngle(new Cell(1, 0), Direction.S));
    	assertEquals(0, Board.findInnerAngle(new Cell(1, 1), Direction.SW));
    	assertEquals(0, Board.findInnerAngle(new Cell(1, 2), Direction.NW));
    	assertEquals(0, Board.findInnerAngle(new Cell(1, 3), Direction.N));
    	assertEquals(0, Board.findInnerAngle(new Cell(1, 4), Direction.NE));
    	assertEquals(0, Board.findInnerAngle(new Cell(1, 5), Direction.SE));
    	
    	assertEquals(0, Board.findInnerAngle(new Cell(1, 3), Direction.N));
    	assertEquals(1, Board.findInnerAngle(new Cell(2, 2), Direction.SW));
    	assertEquals(11, Board.findInnerAngle(new Cell(3, 17), Direction.S));
    	assertEquals(2, Board.findInnerAngle(new Cell(4, 2), Direction.S));
    	assertEquals(4, Board.findInnerAngle(new Cell(2, 8), Direction.NE));
    	assertEquals(5, Board.findInnerAngle(new Cell(2, 10), Direction.SE));
    	assertEquals(2, Board.findInnerAngle(new Cell(2, 4), Direction.NW));
    	assertEquals(0, Board.findInnerAngle(new Cell(3, 1), Direction.SW));
    	assertEquals(0, Board.findInnerAngle(new Cell(3, 17), Direction.SE));
    }
    
    public void testOriginStrategy() {
        Cell origin = board.getCell(new Coordinate(0, 0));
        assertEquals(board.getCell(new Coordinate(1, 0)), origin.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(1, 1)), origin.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(1, 2)), origin.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(1, 3)), origin.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(1, 4)), origin.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(1, 5)), origin.getNeighbor(Direction.NW));
    }

    public void testJointStrategy0() {
        Cell j1 = board.getCell(new Coordinate(1, 0));
        Cell j2 = board.getCell(new Coordinate(2, 0));
        Cell j3 = board.getCell(new Coordinate(3, 0));
        Cell j4 = board.getCell(new Coordinate(4, 0));

        assertEquals(board.getCell(new Coordinate(2, 0)), j1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 1)), j1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(1, 1)), j1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(0, 0)), j1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(1, 5)), j1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 11)), j1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(3, 0)), j2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 1)), j2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 1)), j2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(1, 0)), j2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 11)), j2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 17)), j2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(4, 0)), j3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(4, 1)), j3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 1)), j3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 0)), j3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 17)), j3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(4, 23)), j3.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(5, 0)), j4.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(5, 1)), j4.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(4, 1)), j4.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 0)), j4.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(4, 23)), j4.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(5, 29)), j4.getNeighbor(Direction.NW));
    }

    public void testJointStrategy1() {
        Cell j1 = board.getCell(new Coordinate(1, 1));
        Cell j2 = board.getCell(new Coordinate(2, 2));
        Cell j3 = board.getCell(new Coordinate(3, 3));

        assertEquals(board.getCell(new Coordinate(2, 1)), j1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 2)), j1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 3)), j1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(1, 2)), j1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(0, 0)), j1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(1, 0)), j1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(3, 2)), j2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 3)), j2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 4)), j2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 3)), j2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(1, 1)), j2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 1)), j2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(4, 3)), j3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(4, 4)), j3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(4, 5)), j3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 4)), j3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 2)), j3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 2)), j3.getNeighbor(Direction.NW));
    }

    public void testJointStrategy2() {
        Cell j1 = board.getCell(new Coordinate(1, 2));
        Cell j2 = board.getCell(new Coordinate(2, 4));
        Cell j3 = board.getCell(new Coordinate(3, 6));

        assertEquals(board.getCell(new Coordinate(1, 1)), j1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 3)), j1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 4)), j1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 5)), j1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(1, 3)), j1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(0, 0)), j1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(2, 3)), j2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 5)), j2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 6)), j2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 7)), j2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 5)), j2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(1, 2)), j2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(3, 5)), j3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(4, 7)), j3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(4, 8)), j3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(4, 9)), j3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 7)), j3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 4)), j3.getNeighbor(Direction.NW));
    }

    public void testJointStrategy3() {
        Cell j1 = board.getCell(new Coordinate(1, 3));
        Cell j2 = board.getCell(new Coordinate(2, 6));
        Cell j3 = board.getCell(new Coordinate(3, 9));

        assertEquals(board.getCell(new Coordinate(0, 0)), j1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(1, 2)), j1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 5)), j1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 6)), j1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 7)), j1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(1, 4)), j1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(1, 3)), j2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 5)), j2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 8)), j2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 9)), j2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 10)), j2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 7)), j2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(2, 6)), j3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 8)), j3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(4, 11)), j3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(4, 12)), j3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(4, 13)), j3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 10)), j3.getNeighbor(Direction.NW));
    }

    public void testJointStrategy4() {
        Cell j1 = board.getCell(new Coordinate(1, 4));
        Cell j2 = board.getCell(new Coordinate(2, 8));
        Cell j3 = board.getCell(new Coordinate(3, 12));

        assertEquals(board.getCell(new Coordinate(1, 5)), j1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(0, 0)), j1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(1, 3)), j1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 7)), j1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 8)), j1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 9)), j1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(2, 9)), j2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(1, 4)), j2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 7)), j2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 11)), j2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 12)), j2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 13)), j2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(3, 13)), j3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 8)), j3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 11)), j3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(4, 15)), j3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(4, 16)), j3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(4, 17)), j3.getNeighbor(Direction.NW));
    }

    public void testJointStrategy5() {
        Cell j1 = board.getCell(new Coordinate(1, 5));
        Cell j2 = board.getCell(new Coordinate(2, 10));
        Cell j3 = board.getCell(new Coordinate(3, 15));

        assertEquals(board.getCell(new Coordinate(2, 11)), j1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(1, 0)), j1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(0, 0)), j1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(1, 4)), j1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 9)), j1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 10)), j1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(3, 16)), j2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 11)), j2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(1, 5)), j2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 9)), j2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 14)), j2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 15)), j2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(4, 21)), j3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 16)), j3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 10)), j3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 14)), j3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(4, 19)), j3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(4, 20)), j3.getNeighbor(Direction.NW));
    }
    
    public void testSideStrategy0() {
        Cell s1 = board.getCell(new Coordinate(2, 1));
        Cell s2 = board.getCell(new Coordinate(3, 1));
        Cell s3 = board.getCell(new Coordinate(3, 2));

        
        assertEquals(board.getCell(new Coordinate(3, 1)), s1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 2)), s1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 2)), s1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(1, 1)), s1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(1, 0)), s1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 0)), s1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(4, 1)), s2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(4, 2)), s2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 2)), s2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 1)), s2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 0)), s2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 0)), s2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(4, 2)), s3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(4, 3)), s3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 3)), s3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 2)), s3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 1)), s3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 1)), s3.getNeighbor(Direction.NW));
    }

    public void testSideStrategy1() {
        Cell s1 = board.getCell(new Coordinate(2, 3));
        Cell s2 = board.getCell(new Coordinate(3, 4));
        Cell s3 = board.getCell(new Coordinate(3, 5));

        assertEquals(board.getCell(new Coordinate(2, 2)), s1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 4)), s1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 5)), s1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 4)), s1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(1, 2)), s1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(1, 1)), s1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(3, 3)), s2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(4, 5)), s2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(4, 6)), s2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 5)), s2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 3)), s2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 2)), s2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(3, 4)), s3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(4, 6)), s3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(4, 7)), s3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 6)), s3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 4)), s3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 3)), s3.getNeighbor(Direction.NW));
    }

    public void testSideStrategy2() {
        Cell s1 = board.getCell(new Coordinate(2, 5));
        Cell s2 = board.getCell(new Coordinate(3, 7));
        Cell s3 = board.getCell(new Coordinate(3, 8));

        assertEquals(board.getCell(new Coordinate(1, 2)), s1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 4)), s1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 7)), s1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 8)), s1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 6)), s1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(1, 3)), s1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(2, 4)), s2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 6)), s2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(4, 9)), s2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(4, 10)), s2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 8)), s2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 5)), s2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(2, 5)), s3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 7)), s3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(4, 10)), s3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(4, 11)), s3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 9)), s3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 6)), s3.getNeighbor(Direction.NW));
    }

    public void testSideStrategy3() {
        Cell s1 = board.getCell(new Coordinate(2, 7));
        Cell s2 = board.getCell(new Coordinate(3, 10));
        Cell s3 = board.getCell(new Coordinate(3, 11));

        assertEquals(board.getCell(new Coordinate(1, 4)), s1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(1, 3)), s1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 6)), s1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 10)), s1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 11)), s1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(2, 8)), s1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(2, 7)), s2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 6)), s2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 9)), s2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(4, 13)), s2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(4, 14)), s2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 11)), s2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(2, 8)), s3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 7)), s3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(3, 10)), s3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(4, 14)), s3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(4, 15)), s3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 12)), s3.getNeighbor(Direction.NW));
    }

    public void testSideStrategy4() {
        Cell s1 = board.getCell(new Coordinate(2, 9));
        Cell s2 = board.getCell(new Coordinate(3, 13));
        Cell s3 = board.getCell(new Coordinate(3, 14));

        assertEquals(board.getCell(new Coordinate(2, 10)), s1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(1, 5)), s1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(1, 4)), s1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 8)), s1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 13)), s1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 14)), s1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(3, 14)), s2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 9)), s2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 8)), s2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 12)), s2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(4, 17)), s2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(4, 18)), s2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(3, 15)), s3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 10)), s3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 9)), s3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(3, 13)), s3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(4, 18)), s3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(4, 19)), s3.getNeighbor(Direction.NW));
    }

    public void testSideStrategy5() {
        Cell s1 = board.getCell(new Coordinate(2, 11));
        Cell s2 = board.getCell(new Coordinate(3, 16));
        Cell s3 = board.getCell(new Coordinate(3, 17));

        assertEquals(board.getCell(new Coordinate(3, 17)), s1.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(2, 0)), s1.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(1, 0)), s1.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(1, 5)), s1.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(2, 10)), s1.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(3, 16)), s1.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(4, 22)), s2.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 17)), s2.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 11)), s2.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 10)), s2.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 15)), s2.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(4, 21)), s2.getNeighbor(Direction.NW));

        assertEquals(board.getCell(new Coordinate(4, 23)), s3.getNeighbor(Direction.N));
        assertEquals(board.getCell(new Coordinate(3, 0)), s3.getNeighbor(Direction.NE));
        assertEquals(board.getCell(new Coordinate(2, 0)), s3.getNeighbor(Direction.SE));
        assertEquals(board.getCell(new Coordinate(2, 11)), s3.getNeighbor(Direction.S));
        assertEquals(board.getCell(new Coordinate(3, 16)), s3.getNeighbor(Direction.SW));
        assertEquals(board.getCell(new Coordinate(4, 22)), s3.getNeighbor(Direction.NW));
    }
}
