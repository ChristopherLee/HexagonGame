package ingenious.suite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ingenious.admin.Administrator;
import ingenious.admin.GameResult;
import ingenious.admin.PlayerRanking;
import ingenious.admin.PlayerRecord;
import ingenious.board.StateException;
import ingenious.player.Player;
import ingenious.strategy.HighestScoreStrategy;
import ingenious.strategy.IncreaseSmallestScoreStrategy;
import ingenious.strategy.RandomMoveStrategy;

public class StrategyFarm {
	private static Administrator administrator;
	public static void main (String[] args) {
		int numberOfGames = 100;
		int numberOfPlayers = 2;
		List<GameResult> results = new ArrayList<GameResult>();
		for (int i = 0; i < numberOfGames; i++) {
			administrator = new Administrator(numberOfPlayers);
//			Player player2 = new Player(new RandomMoveStrategy());
			Player player3 = new Player(new HighestScoreStrategy());
			Player player4 = new Player(new IncreaseSmallestScoreStrategy());
//			registerPlayer (player2, "RandomMoveStrategy");
			registerPlayer (player4, "IncreaseSmallestScoreStrategy");
			registerPlayer (player3, "HighestScoreStrategy");
			try {
				GameResult result = administrator.runGame();
				results.add(result);
				System.out.print(result.toString());
				System.out.println();
			}
			catch (StateException e) {
				System.out.println("StateException: " + e.getMessage());
			}
		}
		compileResults(administrator, results);
	}
	
	private static void registerPlayer(Player player, String name) {
		boolean register = administrator.register(name, player);
		if (!register)
			System.out.println ("Error with registration for " + name);
	}
	
	private static void compileResults (Administrator admin, List<GameResult> results) {
		HashMap <String, Integer> numberOfWins = new HashMap<String, Integer>();
		for (String playerName : administrator.getPlayerNames())
			numberOfWins.put(playerName, 0);
		
		for (GameResult result : results) {
			List<PlayerRanking> rankings = result.getPlayerRankings();
			for (int i = 0; i < rankings.size(); i++) {
				if (rankings.get(i).getRank() == 1) {
					String winner = rankings.get(i).getPlayerRecord().getName();
					numberOfWins.put(winner, numberOfWins.get(winner) + 1);
				}
				else
					break;
			}
		}
		
		for (String playerName : numberOfWins.keySet()) {
			System.out.println("Name: " + playerName + " Wins: " + numberOfWins.get(playerName));
		}
	}
}
