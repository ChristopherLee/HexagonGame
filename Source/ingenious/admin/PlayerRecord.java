package ingenious.admin;

import java.util.List;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.player.IPlayer;
import ingenious.turn.Turn;

public class PlayerRecord {
    
    /** The name of the registered player **/
	private final String name;   
	
	/** The registered player **/
	private final IPlayer player;
	
	/** The score of the associated player gained **/
	private Score score;
	
	/** THe list of tiles in registered player's hand **/
	private List<Tile> hand;
	
	public PlayerRecord (String name, IPlayer player) {
		this.name = name;
		this.player = player;
		this.score = new Score();
	}

	public String getName() {
		return name;
	}

	public IPlayer getPlayer() {
		return player;
	}

	public Score getScore() {
		return score;
	}
	
	public void setScore(Score score) {
		this.score = score;
	}

	public List<Tile> getHand() {
		return hand;
	}
	
	public void setHand (List<Tile> hand) {
		this.hand = hand;
	}	

	public boolean equals(Object obj) {
		if (!(obj instanceof PlayerRecord))
			return false;
		PlayerRecord otherRecord = (PlayerRecord)obj;
		return name.equals(otherRecord.getName()) &&
				player == otherRecord.getPlayer() &&
				score.equals(otherRecord.getScore()) &&
				hand.equals(otherRecord.getHand());
	
	}
	
	public int compareTo(PlayerRecord record){
		return this.score.compareTo(record.getScore());
	}

	public void getPublication(GameUpdate updates) {
		player.receiveUpdate(updates);		
	}
}
