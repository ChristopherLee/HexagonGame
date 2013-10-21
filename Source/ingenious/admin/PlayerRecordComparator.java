package ingenious.admin;

import java.util.Comparator;


public class PlayerRecordComparator implements Comparator<PlayerRecord>{
    
	public int compare(PlayerRecord record1, PlayerRecord record2) {
		return record2.compareTo(record1);
	}

}
