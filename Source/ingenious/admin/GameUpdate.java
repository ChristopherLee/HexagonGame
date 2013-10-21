package ingenious.admin;

import java.util.List;

import ingenious.board.Placement;

public class GameUpdate {
	private final String name;
	private final List<Placement> placements;
	
	public GameUpdate(String name, List<Placement> placements) {
		this.name = name;
		this.placements = placements;
	}

	public String getName() {
		return name;
	}

	public List<Placement> getPlacements() {
		return placements;
	}
	
	
	public String toString(){
		String string =  "Player: "+ name + "\nPlaced: ";
		for(Placement placement : placements){
			string += placement.toString();
		}
		return string; 
	}

}
