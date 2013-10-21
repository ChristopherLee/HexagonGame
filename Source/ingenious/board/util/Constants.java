/** 
 * Constants.java
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
 *  Feb 1, 2011        Chunzhao, Zheng        Initial
 *  Feb 1, 2011        Christopher, Lee       Initial
 *********************************************************************
*/
package ingenious.board.util;

import com.google.java.contract.Invariant;

/**
 * ...
 */
@Invariant("this != null")
public interface Constants {
    
    public final static int MIN_NUMBER_PLAYERS = 2;
    
    public final static int TWO_DIRECTIONS_CLOCKWISE = 2;
    
    public final static int THREE_DIRECTIONS_CLOCKWISE = 3; 
    
    public final static int MAX_NUMBER_PLAYERS = 6;
      
    public final static int NUMBER_OF_STARTING_TILES_IN_HAND = 6;
    
    public final static int NUMBER_OF_SIDES = 6;
    
    public final static int INITIAL_STATE_LAYER = 5;
    
    public final static int ADMIN_TILE_BAG_SIZE = 120;
    
    // --------------------- FOR NETWORK ---------------------
    public final static int NETWORK_TIMEOUT_INTERVAL = 10000;
    public final static String TAG_REGISTER = "register";
    public final static String ATTRIBUTE_REGISTER_NAME = "name";
    
    public final static String TAG_ACCEPT = "accept";
    public final static String ATTRIBUTE_ACCEPT_PLAYER = "player";
    
    public final static String TAG_TILE = "tile";
    public final static String ATTRIBUTE_TILE_C0= "c0";
    public final static String ATTRIBUTE_TILE_C1= "c1";
    
    public final static String TAG_NULL_TILE = "false";
    
    public final static String TAG_PLAYERS  = "players";
    
    public final static String TAG_PLAYER = "player";
    public final static String ATTRIBUTE_PLAYER_NAME = "name";
    
    public final static String TAG_TURN = "turn";
    
    public final static String TAG_BOARD = "board";
    public final static String ATTRIBUTE_BOARD_PLAYERS = "players";
    
    public final static String TAG_PLACEMENT = "placement";
    public final static String ATTRIBUTE_PLACEMENT_C0 = "c0";
    public final static String ATTRIBUTE_PLACEMENT_D0 = "d0";
    public final static String ATTRIBUTE_PLACEMENT_A0 = "a0";
    public final static String ATTRIBUTE_PLACEMENT_C1 = "c1";
    public final static String ATTRIBUTE_PLACEMENT_D1 = "d1";
    public final static String ATTRIBUTE_PLACEMENT_A1 = "a1";
    
    public final static String TAG_SCORE = "score";
    public final static String ATTRIBUTE_SCORE_ORANGE = "orange";
    public final static String ATTRIBUTE_SCORE_RED = "red";
    public final static String ATTRIBUTE_SCORE_GREEN = "green";
    public final static String ATTRIBUTE_SCORE_YELLOW = "yellow";
    public final static String ATTRIBUTE_SCORE_PURPLE = "purple";
    public final static String ATTRIBUTE_SCORE_BLUE = "blue";
    
    public final static String TAG_RERACKABLE = "rerackable";
    
    public final static String TAG_TRUE = "true";
    public final static String TAG_FALSE = "false";
    
    public final static String TAG_RERACK = "rerack";
    
    public final static String TAG_RESULT = "result";
    public final static String ATTRIBUTE_RESULT_NAME = "name";
    
    public final static String TAG_END = "end";

    // ------------------------------------------------------
    
    public final static String TAG_TEST_TURN = "turntest";
    
    public final static String TAG_ADMIN = "administrator";
    
    public final static String TAG_ACTIONS = "actions";
    
    public final static String TAG_RESULTS = "results";
    
    public final static String TAG_BAD = "bad";
    
    public final static String TAG_REASON = "reason";

	public static final String TAG_STRATEGY = "strategy";
    
}
