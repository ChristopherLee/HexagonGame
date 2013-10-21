package ingenious.suite;

import java.util.List;

import ingenious.board.Board;
import ingenious.board.Placement;
import ingenious.board.Score;
import ingenious.board.Tile;
import ingenious.suite.network.IAdminResponse;
import ingenious.suite.network.ITurnAction;

import ingenious.suite.network.TurnXML;

public class TurnTestXML {
	private final Board board;
	private final Score score;
	private final List<Tile> hand;
	private final List<ITurnAction> actions;
	private final List<IAdminResponse> responses;
	public TurnTestXML(TurnXML turn, List<ITurnAction> actions, List<IAdminResponse> responses) {
		this.board = new Board(turn.getNumberOfPlayers());
		for (Placement placement : turn.getCurrentPlacements())
			this.board.place(placement);
		this.score = turn.getPlayerScore();
		this.hand = turn.getPlayerHand();
		this.actions = actions;
		this.responses = responses;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Score getScore() {
		return score;
	}
	
	public List<Tile> getHand() {
		return hand;
	}
	
	public List<ITurnAction> getActions() {
		return actions;
	}
	
	public List<IAdminResponse> getResponses() {
		return responses;
	}
	
}
