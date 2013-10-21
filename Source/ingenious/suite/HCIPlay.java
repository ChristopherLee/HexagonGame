package ingenious.suite;
import java.util.Random;

import javax.swing.JOptionPane;

import ingenious.hci.HCIPlayerStrategy;
import ingenious.admin.Administrator;
import ingenious.admin.GameResult;
import ingenious.board.StateException;
import ingenious.player.IPlayer;
import ingenious.player.Player;
import ingenious.strategy.HighestScoreStrategy;
import ingenious.strategy.IPlayerStrategy;
import ingenious.strategy.IncreaseSmallestScoreStrategy;

/**
 * plays a game with a random number of AI players
 */
public class HCIPlay{
	private static int DEFAULT_TIMEOUT = 60;
	public static void main(String[] args){
		Random random = new Random();
		int numberOfAIPlayers = 2;//random.nextInt(4) + 1;
		Administrator admin = new Administrator(numberOfAIPlayers + 1);  
		
		IPlayer plr1 = new Player(new HCIPlayerStrategy(DEFAULT_TIMEOUT));
		admin.register("HCI", plr1);
		
		for (int i = 0; i < numberOfAIPlayers; i++) {
			IPlayerStrategy strategy;
			if (i % 2 == 0)
				strategy = new IncreaseSmallestScoreStrategy();
			else
				strategy = new HighestScoreStrategy();
			IPlayer plr = new Player(strategy);
			admin.register("AI-player" + (i+2), plr);
		}
		
		try{
			GameResult result = admin.runGame();
			JOptionPane.showMessageDialog(null, result.toString());
		}
		catch(StateException e){
			System.out.println(e);
		}
		
	}
}