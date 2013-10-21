package ingenious.admin;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import ingenious.board.NullTile;
import ingenious.board.Tile;
import ingenious.board.Color;

import com.google.java.contract.Requires;

/**
 * Represents the tileBag in the Ingenious game
 */
public class TileBag {
	private final List<Tile> list;
	/**
	 * creates a random list of tiles with the specifed number
	 *
	 */
	public TileBag(int numberOfTiles) {
		list = new ArrayList<Tile> ();
		for (int i = 0; i < numberOfTiles; i++) {
			list.add(createRandomTile());
		}
	}
	
	/**
	 * creates a tileBag with the given list of tiles
	 */
	public TileBag(List<Tile> tiles) {
		list = new ArrayList<Tile> ();
    	list.addAll(tiles);
    }

	/**
	 * create a random tile (2 random colors) and return it
	 */
	private Tile createRandomTile() {
		Random random = new Random();
		int firstColor = random.nextInt(Color.values().length);
		int secondColor = random.nextInt(Color.values().length);
		return new Tile(Color.values()[firstColor], Color.values()[secondColor]);
	}

	/**
	 * returns the next tile in the tile bag.  Returns nullTile is bag is empty
	 */
	public Tile getNextTile() {
		if (list.size() == 0)
			return new NullTile();
		return list.remove(0);
	}

	/**
	 * returns true if there are no more tiles in the bag
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Requires({
		"tile != null",
		"!tile.isNull()"
	})
	public void addTileToBag(Tile tile) {
		list.add(tile);
	}
	
	public int size() {
		return list.size();
	}
}
