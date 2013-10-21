/** 
 * StateException.java
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
package ingenious.board;

import com.google.java.contract.Invariant;

/**
 * StateException is a customized exception to keep track exception for 
 * the state of the board.
 */
@Invariant("toString() != null")
public class StateException extends RuntimeException {

    public StateException() {
        super();
    }
    
    public StateException(String message) {
        super(message);
    }
    
    public StateException(Throwable cause) {
        super(cause);
    }
    
    public StateException(String message, Throwable cause) {
        super(message, cause);
    }

}
