package ingenious.board;

import com.google.java.contract.Requires;

/**
 * A placement that contains a nulltile, used when generating available valid positions for tiles without a specific tile
 */
public class PlacementLocation extends Placement {
    
    @Requires({
        "distance0 >= 0",
        "angle0 >= 0",
        "distance1 >= 0",
        "angle1 >= 0"
    })
	public PlacementLocation(int distance0, int angle0, int distance1, int angle1) {
		super(distance0, angle0, distance1, angle1);
	}
    
    public PlacementLocation(Cell cell0, Cell cell1) {
		super(cell0.getDistance(), cell0.getAngle(), cell1.getDistance(), cell1.getAngle());
	}
	
    @Requires({
        "tile != null"
    })
	public Placement createPlacementWithColor(Tile tile) {
		return new Placement(tile.getColor0(), coord0.getDistance(), coord0.getAngle(), 
							 tile.getColor1(), coord1.getDistance(), coord1.getAngle());
	}
    
    @Override 
	public int hashCode() {
        return coord0.hashCode() * coord1.hashCode();
    }
}
