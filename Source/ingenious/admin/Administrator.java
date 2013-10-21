package ingenious.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;
import com.google.java.contract.ContractAssertionError;

import ingenious.admin.TileBag;
import ingenious.board.Board;
import ingenious.board.Color;
import ingenious.board.Placement;
import ingenious.board.StateException;
import ingenious.board.Tile;
import ingenious.board.NullTile;
import ingenious.board.util.Constants;
import ingenious.player.IPlayer;
import ingenious.turn.Turn;

@Invariant({ 
	"playerRecords.size() <= startingNumberOfPlayers", 
})
public class Administrator implements IAdministrator{
	private int            		           startingNumberOfPlayers;
	private final ArrayList<PlayerRecord>  playerRecords;
	private GameState                      state;
	private final TileBag				   tileBag;
	
	@Requires({
		"numberOfPlayers >= 2",
		"numberOfPlayers <= 6"
	})
	public Administrator(int numberOfPlayers) {
		this.startingNumberOfPlayers = numberOfPlayers;
		playerRecords = new ArrayList<PlayerRecord>();
		state = GameState.REGISTRATION;
		tileBag = new TileBag(Constants.ADMIN_TILE_BAG_SIZE);
	}

	/**
	 * return the names from all records
	 */
	public List<String> getPlayerNames(){
		List<String> names = new ArrayList<String>();
		for(PlayerRecord record : playerRecords){
			names.add(record.getName());
		}
		return names;
	}

	public boolean register(String name, IPlayer player) {
		PlayerRecord record = new PlayerRecord(name + playerRecords.size(), player);
		subscribe(record);
		distributeInitialTilesToPlayer(record);
		return true;
	}

	/**
	 * returns true if enough players have been registered to start the game
	 */
	public boolean isEnoughPlayersRegistered() {
		return playerRecords.size() == startingNumberOfPlayers;
	}
	
	/**
	 * 
	 * check if the game is in the registration phase
	 */
	public boolean isGameInRegistration(){
		return state == GameState.REGISTRATION;
	}

	public GameResult runGame() throws StateException {
		state = GameState.RUNGAME;
		Board board = new Board(startingNumberOfPlayers);

		broadcastPlayerNames();

		int currentIndex = 0;
		List <Placement> placements = new ArrayList<Placement> ();
		while (!isGameOver(board)) {
			PlayerRecord currentPlayerRecord = playerRecords.get(currentIndex);
			Turn turn = new Turn(board, currentPlayerRecord.getHand(), currentPlayerRecord.getScore(), tileBag);
			playRound(turn, currentPlayerRecord, board);
			if (!turn.getKickFlag()) {
				placements.addAll(turn.getActions());
				currentIndex++;
			}
			else
				board = recreateBoard(placements);
			if (playerRecords.size() != 0) {
				// wraps the index to 0 if it is greater than the number of players
				currentIndex = (currentIndex % playerRecords.size());
			}
		}
		state = GameState.GAMEOVER;
		return new GameResult(playerRecords);
	}

	
	/**
	 * send player names to all players except your own name
	 */
	private void broadcastPlayerNames() {	
		for (PlayerRecord record : playerRecords){
			System.out.println("PlayerRecord: " + record.getName());
			List<String> otherPlayerNames = getAllPlayerNamesExceptYourself(record.getName());		
			record.getPlayer().acceptPlayerNames(otherPlayerNames);
		}	
	}
	
	/**
	 * distribute initial tiles from tile bag to given PlayerRecord
	 */
	private void distributeInitialTilesToPlayer(PlayerRecord playerRecord) throws StateException {
		ArrayList<Tile> hand = new ArrayList<Tile>();
		for (int i = 0; i < Constants.NUMBER_OF_STARTING_TILES_IN_HAND; i++) {
			if (tileBag.isEmpty()) {
				throw new StateException("Administrator tileBag is not big enough to hand out initial tiles to all players");
			}
			hand.add(tileBag.getNextTile());
		}
		playerRecord.setHand(hand);
		playerRecord.getPlayer().acceptNumberOfPlayersAndHand(startingNumberOfPlayers, hand);		
	}
	
	/**
	 * recreates the board given the list of placements, and the number of first turns.
	 * Handles the case where player is kicked, reverting the board to before their turn. 
	 * numberOfFirstTurns is the same as startignNumberOfPlayers, unless the player is kicked within the first turn
	 */
	private Board recreateBoard(List<Placement> placements){
		Board board = new Board(startingNumberOfPlayers);
		for (Placement placement : placements)
			board.place(placement);
		return board;
	}
	/**
	 * play a round with the given board, tileBag, for the given player.  If player is not terminated, return a list of
	 * all his placements.  Otherwise, return an empty list
	 */
	private void playRound(Turn turn, PlayerRecord playerRecord, Board board) {    	
		IPlayer player = playerRecord.getPlayer();
		if (playerRecord.getHand().isEmpty())
			return;
		try {
			player.take(turn, board.getPlacements());
			GameUpdate gameUpdate;
			if(turn.getKickFlag()){
				terminatePlayer(playerRecord, board);
				gameUpdate = new GameUpdate(playerRecord.getName(), new ArrayList<Placement>());
			}
			else {
				playerRecord.setScore(turn.getPlayerScore());
				gameUpdate = new GameUpdate(playerRecord.getName(), turn.getActions());
			}
			broadcastGameUpdate(gameUpdate);
		} catch (Exception e) {
			terminatePlayer(playerRecord, board);
		} catch (ContractAssertionError e) {	
			terminatePlayer(playerRecord, board);
		}  
	}

	
	/**
	 * removes the player from the game
	 */

	@Requires({ 
		"playerRecord != null"
	})
	private void terminatePlayer(PlayerRecord playerRecord, Board board) {
		//System.out.println("Terminated Player: " + playerRecord.getName() + "\nReason: " + e.getMessage());
		playerRecords.remove(playerRecord);
	}

	/**
	 * returns true if the game is over for the given board.  This is true when:
	 * 		- there are no available positions to place a tile
	 * 		- all player's have no tiles left in hand to place
	 */
	private boolean isGameOver(Board board) {
		if (!board.hasAvailablePositionsForTile() || playerRecords.size() == 0) 
			return true;

		boolean allPlayerHandsEmpty = true;
		for (PlayerRecord record : playerRecords) {
			if (!record.getHand().isEmpty()) {
				allPlayerHandsEmpty = false;
				break;
			}
		}
		return allPlayerHandsEmpty;
	}

	/**
	 * Adds a subscriber to the list of playerRecords.  
	 */
	
	private void subscribe(PlayerRecord record) {
		playerRecords.add(record);

	}

	/**
	 * Notify each subscriber with the list of placements added for one person's turn
	 */
	private void broadcastGameUpdate(GameUpdate update) {
		for (PlayerRecord record : playerRecords) {
			record.getPublication(update);
		}
	}
	
	/**
	 * get list of all the player names in the game except for the name passed in (the one being notified)
	 */
	private List<String> getAllPlayerNamesExceptYourself(String name) {
		List<String> playerNames = new ArrayList<String> ();
    	for (PlayerRecord playerRecord : playerRecords) {
    		if (!playerRecord.getName().equals(name))
    			playerNames.add(playerRecord.getName());
    	}
    	return playerNames;
	}
}
