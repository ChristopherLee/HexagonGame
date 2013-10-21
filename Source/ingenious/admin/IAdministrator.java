package ingenious.admin;

import java.util.List;

import com.google.java.contract.Requires;

import ingenious.player.IPlayer;

public interface IAdministrator {
	
	/**
	 * accepts a registration of a player name and an IPlayer.  Returns true if successful.
	 */
	@Requires({
		"isGameInRegistration()",
		"!isEnoughPlayersRegistered()"
	})
	public boolean register(String name, IPlayer player);	
		
	/**
	 * run the game and return a game result
	 */
	@Requires({
		"isEnoughPlayersRegistered()",
		"isGameInRegistration()"
	})
	public GameResult runGame();
	
	/**
	 * 
	 * check if the game is in the registration phase
	 */
	public boolean isGameInRegistration();
	
	/**
	 * returns true if enough players have been registered to start the game
	 */
	public boolean isEnoughPlayersRegistered();
}
