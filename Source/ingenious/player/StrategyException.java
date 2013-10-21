package ingenious.player;

import com.google.java.contract.Invariant;

/**
 * StrategyException is a customized exception to keep track of the exception for 
 * when the strategy returns a list of placements that is too short
 */
@Invariant("toString() != null")
public class StrategyException extends Exception {

    public StrategyException() {
        super();
    }
    
    public StrategyException(String message) {
        super(message);
    }
    
    public StrategyException(Throwable cause) {
        super(cause);
    }
    
    public StrategyException(String message, Throwable cause) {
        super(message, cause);
    }

}