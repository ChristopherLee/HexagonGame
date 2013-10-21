/** 
 * BoardResult.java
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
 *  Feb 23, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
 */
package ingenious.suite;

import java.util.ArrayList;
import java.util.List;

import ingenious.board.Score;

/**
 * Class to hold result for Board
 */
public class BoardResult {

    private final List<Score> scores;
    
    private String bad;

    public BoardResult() {
        scores = new ArrayList<Score>();
    }

    public void addScore(Score score) {
        scores.add(score);
    }

    /**
     * Get scores from this result.
     */
    public Score[] getScores() {
        return scores.toArray(new Score[0]);
    }

    /**
     * Get bad reason from this result.
     */
    public String getReason() {
        return bad;
    }
    
    public void setReason(String reason) {
        bad = reason;
    }

    /**
     * Return if true if this result contains bad reason.
     */
    public boolean isBad() {
        return bad != null;
    }

}
