/** 
 * BoardTester.java
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
 *  Feb 2, 2011        Chunzhao ZHENG         Initial
 *********************************************************************
*/
package ingenious.suite;

import java.util.List;

import com.google.java.contract.ContractAssertionError;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.StateException;
import ingenious.suite.network.BoardXML;


/**
 * 
 * BoardTester is a ... to ...
 *
 * @author chunzhao
 * @version
 */
public class BoardTester {

    public static void main(String[] args) {
        BoardScriptExecutor executor = BoardScriptExecutor.getInstance();
        BoardXML pojo = (BoardXML) executor.readXml(System.in);
        Board board = new Board(pojo.getNumberOfPlayers());
        BoardResult result = simulate(board, pojo.getCurrentPlacements());
        String xml = executor.writeXml(result);
        System.out.println(xml);
    }
    
    private static BoardResult simulate(Board board, List<Placement> currentPlacements) {
        BoardResult result = new BoardResult();
        for(Placement placement: currentPlacements) {
        	try {
        		Score score= board.calculate(placement);
            	board.place(placement);
            	result.addScore(score);
            }
            catch (ContractAssertionError e) {
            	result.setReason(e.getMessage());
            	break;
            }
        }
        return result;
    }

}
