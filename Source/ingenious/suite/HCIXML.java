package ingenious.suite;

import java.util.List;

import com.google.java.contract.Invariant;

import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;

@Invariant({
    "currentPlacements != null",
    "playerHand != null",
    "playerScore != null",
})
class HCIXML {
    private final int numberOfPlayers;
    List<Placement> currentPlacements;
    private final Score playerScore;
    private final List<Tile> playerHand;
    private final int timeout;
        
    HCIXML(int numberOfPlayers, List<Placement> currentPlacements, 
                    List<Tile> playerHand, Score playerScore, int timeout) {
        this.numberOfPlayers = numberOfPlayers;
        this.currentPlacements = currentPlacements;
        this.playerScore = playerScore;
        this.playerHand = playerHand;
        this.timeout = timeout;
    }
    
    int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    
    List<Placement> getCurrentPlacements() {
        return currentPlacements;
    }

    
    List<Tile> getPlayerHand() {
        return playerHand;
    }

    
    Score getPlayerScore() {
        return playerScore;
    }
    
    int getTimeout() {
    	return timeout;
    }

}
