package ingenious.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import ingenious.admin.PlayerRecordComparator;
import ingenious.board.Score;

/**
 * Contains the results of the game
 */
public class GameResult {
	private final List<PlayerRanking> playerRankings;
	
	public GameResult(List<PlayerRecord> playerRecords) {
		this.playerRankings = createRankings(playerRecords);
	}
	
	public List<PlayerRanking> getPlayerRankings() {
		return playerRankings;
	}
	
	private List<PlayerRanking> createRankings(List<PlayerRecord> playerRecords) {
		List<PlayerRanking> rankings = new ArrayList<PlayerRanking>();
		if (playerRecords.isEmpty())
			return rankings;
		Collections.sort(playerRecords, new PlayerRecordComparator());

		int currentRank = 1;
		int nextRank = 2;
		Score lastScore = playerRecords.get(0).getScore();
		rankings.add(new PlayerRanking(currentRank, playerRecords.get(0)));
		
		for (int i = 1; i < playerRecords.size(); i++) {
			if (!playerRecords.get(i).getScore().equals(lastScore))
				currentRank = nextRank;
			PlayerRanking rank = new PlayerRanking(currentRank, playerRecords.get(i));
			rankings.add(rank);
			nextRank++;
			lastScore = playerRecords.get(i).getScore();
		}
		return rankings;
	}
	
	public String toString(){
		String result = "";
		if (playerRankings.size() == 0)
			return "No rankings, all players were kicked";
		for (int i = 0; i < playerRankings.size(); i++) {
			int rank = playerRankings.get(i).getRank();
			PlayerRecord record = playerRankings.get(i).getPlayerRecord();
			result += (rank + ": " + record.getName() + " - " + record.getScore()) + "\n";
		}
		return result; 
	}
	
}
