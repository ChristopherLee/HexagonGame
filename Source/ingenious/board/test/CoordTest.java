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

import ingenious.board.Coordinate;

import junit.framework.TestCase;

/**
 * Unit test for Coordinate class
 */
public class CoordTest extends TestCase {

    Coordinate c1;
    Coordinate c2;
    Coordinate c3;
    Coordinate c4;
    Coordinate c5;
    Coordinate c6;
    Coordinate c7;
    Coordinate c8;
    Coordinate c9;
    Coordinate c10;
    Coordinate c11;
    Coordinate c12;
    Coordinate c13;
    Coordinate c14;
    Coordinate c15;
    Coordinate c16;
    Coordinate c17;
    Coordinate c18;
    Coordinate c19;
    Coordinate c20;

    @Override
    protected void setUp() throws Exception {
        c1 = new Coordinate(0, 0);
        c2 = new Coordinate(1, 0);
        c3 = new Coordinate(1, 1);
        c4 = new Coordinate(1, 2);
        c5 = new Coordinate(1, 3);
        c6 = new Coordinate(1, 4);
        c7 = new Coordinate(1, 5);
        c8 = new Coordinate(2, 0);
        c9 = new Coordinate(2, 1);
        c10 = new Coordinate(2, 2);
        c11 = new Coordinate(2, 3);
        c12 = new Coordinate(2, 4);
        c13 = new Coordinate(2, 5);
        c14 = new Coordinate(2, 6);
        c15 = new Coordinate(2, 7);
        c16 = new Coordinate(2, 8);
        c17 = new Coordinate(2, 9);
        c18 = new Coordinate(2, 10);
        c19 = new Coordinate(2, 11);
        c20 = new Coordinate(5, 0);
    }

    public void testIsJointPoint() {
        assertEquals(true, c2.isJointPoint());
        assertEquals(true, c3.isJointPoint());
        assertEquals(true, c4.isJointPoint());
        assertEquals(true, c5.isJointPoint());
        assertEquals(true, c6.isJointPoint());
        assertEquals(true, c7.isJointPoint());
        assertEquals(true, c8.isJointPoint());
        assertEquals(false, c9.isJointPoint());
        assertEquals(true, c10.isJointPoint());
        assertEquals(false, c11.isJointPoint());
        assertEquals(true, c12.isJointPoint());
        assertEquals(false, c13.isJointPoint());
        assertEquals(true, c14.isJointPoint());
        assertEquals(false, c15.isJointPoint());
        assertEquals(true, c16.isJointPoint());
        assertEquals(false, c17.isJointPoint());
        assertEquals(true, c18.isJointPoint());
        assertEquals(false, c19.isJointPoint());
        assertEquals(true, c20.isJointPoint());
    }

    public void testIsSidePoint() {
        assertEquals(false, c2.isSidePoint());
        assertEquals(false, c3.isSidePoint());
        assertEquals(false, c4.isSidePoint());
        assertEquals(false, c5.isSidePoint());
        assertEquals(false, c6.isSidePoint());
        assertEquals(false, c7.isSidePoint());
        assertEquals(false, c8.isSidePoint());
        assertEquals(true, c9.isSidePoint());
        assertEquals(false, c10.isSidePoint());
        assertEquals(true, c11.isSidePoint());
        assertEquals(false, c12.isSidePoint());
        assertEquals(true, c13.isSidePoint());
        assertEquals(false, c14.isSidePoint());
        assertEquals(true, c15.isSidePoint());
        assertEquals(false, c16.isSidePoint());
        assertEquals(true, c17.isSidePoint());
        assertEquals(false, c18.isSidePoint());
        assertEquals(true, c19.isSidePoint());
        assertEquals(false, c20.isSidePoint());
    }
}