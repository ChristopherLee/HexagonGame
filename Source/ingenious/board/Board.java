/** 
 * Board.java
 *
 * This source file can be used for educational purpose only.
 * Originally developed by Chunzhao Zheng.
 * 
 * Copyright (c) 2011 Chunzhao, College of Computer and Information Science,
 * Northeastern University, Boston, MA 02115. All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met: 
 *     -  Redistributions of source code must retain the above copyright notice.
 *     -  Redistributions in binary form must reproduce the above copyright notice.
 *
 * Revision History
 *       Date             Programmer            Notes
 * ------------------ ------------------ -------------------
 *  Feb 1, 2011        Chunzhao, Zheng        Initial
 *  Feb 1, 2011        Christopher, Lee       Initial
 *********************************************************************
*/
package ingenious.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ingenious.board.util.Constants;

import com.google.java.contract.Ensures;
import com.google.java.contract.Invariant;
import com.google.java.contract.Requires;

/**
 * A facade for the model of a board.
 */
@Invariant("layers != null")
public class Board {
        
    private final static Color[] INITIAL_COLORS = {
        Color.BLUE, Color.ORANGE, Color.YELLOW, Color.PURPLE, Color.RED, Color.GREEN
    };
    
    /** instances of layers that this board contains **/
    private final Layer[] layers;
    private final int numberOfPlayers;
    private final ArrayList<Placement> placements;
    
    
    
    /**
     * @param size A positive number indicating the size of surrounded layers.
     * And the number should be between 2 and 6.
     */
    @Requires({
    	"numberOfPlayers >= 2",
    	"numberOfPlayers <= 6"
    })
    public Board(int numberOfPlayers) {     
        this.numberOfPlayers = numberOfPlayers;
        int size = numberOfPlayers + 4;
        layers = new Layer[size];
        placements = new ArrayList<Placement>();
        
        for(int i = 0; i<size ; i++)
        	layers[i] = new Layer(i);
        connectCells();
        
        populateInitialCells(Constants.INITIAL_STATE_LAYER);  
    }
    
    /**
     * Connect the neighbors for each cell in the board
     */
    private void connectCells() {
    	for (int i = 1; i < layers.length; i++){
    		connectWithinLayer(i);
    		connectWithPreviousLayer(i);
    	}
    }
    
    /**
     * Connect all the cells within the same layer
     */
    private void connectWithinLayer(int distance) {
    	Layer layer = layers[distance];
    	for(Cell cell : layer.cells){
    		int angle = cell.getAngle();
    		Cell nextCell = layer.getCell(normalizeAngle(distance, ++angle));
    		cell.connect(nextCell, findDirectionToNextCellWithinLayer(cell));
    	}
    }
    
    /**
     * returns the direction of the next cell in the layer
     */
    private Direction findDirectionToNextCellWithinLayer(Cell cell) {
    	return Direction.values()[(cell.getRegion().ordinal() + Constants.TWO_DIRECTIONS_CLOCKWISE) % 
    					           Constants.NUMBER_OF_SIDES];
    }
    
    /**
     * connect the cells with their neighbors on the previous or 'inner' layer
     */
    private void connectWithPreviousLayer(int distance) {
    	for(Cell cell : layers[distance].cells){
    		List<Direction> connections = findDirectionsToInnerCell(cell);
    		for (Direction direction : connections) {
    			Cell innerCell = layers[distance-1].getCell(findInnerAngle(cell, direction));
    			cell.connect(innerCell, direction);
    		}
    	}
    }
    
    /**
     * returns the angle of the cell one layer in, in the direction given.
     */
    /*@Requires({
    	""
    }) TODO: do a contract*/ 
    public static int findInnerAngle(Cell cell, Direction direction) {
    	if(direction == Direction.S){
    		return (cell.getRegion() == Direction.NW) ? 
    		              cell.getAngle() - Constants.NUMBER_OF_SIDES : 
    		              cell.getAngle();
    	}
    	int previousLayerDistance = cell.getDistance()-1;
    	return normalizeAngle(previousLayerDistance, 
    	                      (cell.getAngle() - (direction.ordinal() + 3) % 6));
    }
    
    /**
     * Given a cell, returns a list of relative direction to all adjacent cells on the inner layer.
     */
    private List<Direction> findDirectionsToInnerCell (Cell cell) {
    	List<Direction> directions = new ArrayList<Direction> ();
    	Direction region = cell.getRegion();
    	if (cell.isJointPoint()) {
    		directions.add(region.inverse());
    	}
    	else {
    		Direction inverseDirection = region.inverse();
    		directions.add(inverseDirection);
    		directions.add(inverseDirection.next());
    	}
    	return directions;
    }
    
    /**
     * returns the correct angle when angle is overflowed
     */
    private static int normalizeAngle (int distance, int angle) {
    	if(distance == 0)
    		return 0;
    	int maxAngleForInnerLayer = (distance) * Constants.NUMBER_OF_SIDES;
    	int result = angle % maxAngleForInnerLayer;
    	if(result < 0 )
    		return normalizeAngle(distance, result+maxAngleForInnerLayer);
    	return result;
    }
    

    /**
     * Set initial color/state to this board for the layer on the given distance 
     *
     * @param distance
     */
    private void populateInitialCells(int distance) {
        for(int i = 0; i < INITIAL_COLORS.length; i++) {
            Cell cell = getCell(distance, i*distance);
            cell.setColor(INITIAL_COLORS[i]);
        }
    }

    /**
     * Get a cell on the position that is specified by the given distance 
     * and angle.
     */
    @Requires({
    	"distance >= 0",
    	"distance < layers.length",
    	"angle >= 0",
    	"angle < layers[distance].size()",
    	"(angle < distance * 6) || angle == 0"
    })
    @Ensures({
    	"result != null",
    	"result.getAngle() >= 0",
    	"result.getDistance() >= 0"
    })
    public Cell getCell(int distance, int angle) {
        Layer layer = layers[distance];
        Cell cell = layer.getCell(angle);
        return cell;
    }
    
    /** 
     * Get a cell on the position that is specified by the given coordinate.
     */
    @Requires({
    	"coord != null"
    })
    public Cell getCell(Coordinate coord) {
        return getCell(coord.getDistance(), coord.getAngle());
    }
    
    public List<Placement> getPlacements() {
    	return placements;
    }
    
    /**
     * returns true if the coordinate is an existing coordinate on the board
     */
    @Requires("coord != null")
    public boolean contains(Coordinate coord) {
        return contains(coord.getDistance(), coord.getAngle());
    }
    
    /**
     * returns true if the coordinate is an existing coordinate on the board
     */
    public boolean contains(int distance, int angle) {
        if(distance >= layers.length)
            return false;
        return angle < layers[distance].size();
    }
    
    /**
     * Place the given tile on this board
     * @throws StateException if the placement violate the rule of the game
     */
    @Requires({
    	"placement != null",
    	"verifyValidPlacement(placement)"
    })
    public void place(Placement placement) { 
    	update(placement);
    	placements.add(placement);
    }
    
    /**
     * Verify the given tile to see if it's legal to put it on this board.
     */
    public boolean verifyValidPlacement(Placement placement) {
    	if(!contains(placement.getCoordinate0()) 
    			|| !contains(placement.getCoordinate1()))
    		return false;

    	if (placement.getCoordinate0().equals(placement.getCoordinate1()))
    		return false;
    	Cell cell0 = getCell(placement.getCoordinate0());
    	Cell cell1 = getCell(placement.getCoordinate1());

    	if (cell0.isOccupied() || cell1.isOccupied()) {
    		return false;
    	}

    	if (!cell0.isAdjacent(cell1)) {
    		return false;
    	}

    	if (isFirstTurn()) {
    		return isValidInitialPlacement(cell0, cell1);
    	}
    	else {
    		if(!cell0.hasNeighboringOccupiedCell() && !cell1.hasNeighboringOccupiedCell()) {
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * returns true if the two cells placed (representing a tile) is a valid placement for
     * a first turn
     */
    private boolean isValidInitialPlacement(Cell cell0, Cell cell1) {
        for (int i = 0; i < Color.values().length; i++) {
            Cell cell = getCell(Constants.INITIAL_STATE_LAYER, 
                        i*Constants.INITIAL_STATE_LAYER);
            if (!cell.hasNeighboringOccupiedCell()) {
                if (cell.isAdjacent(cell0) || cell.isAdjacent(cell1)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * returns true if this is one of the first turns in the game for a player
     * (the first turn is the first tile the player places on the board)
     */
    private boolean isFirstTurn () throws StateException{
        int num = 0;
        for (int i = 0; i < Color.values().length; i++) {
            Cell cell = getCell(Constants.INITIAL_STATE_LAYER, 
                        i*Constants.INITIAL_STATE_LAYER);
            num += cell.hasNeighboringOccupiedCell() ? 1 : 0;
        }
        return num < numberOfPlayers;
    }
    
    /**
     * Calculate the score for the placement of the given tile.
     */
    @Requires({
        "placement != null",
        "verifyValidPlacement(placement)"
    })
    public Score calculate(Placement placement) {
    	
        Cell cell0 = getCell(placement.getCoordinate0());
        Score score0 = calculate(cell0);
       
        Cell cell1 = getCell(placement.getCoordinate1());
        Score score1 = calculate(cell1);
       
        score0.add(score1);    
        return score0;
    }
    
    /**
     * Calculate the score the given coordinate on the given board.
     * @return A score for the finished placement.
     */
    private Score calculate(Cell cell) {
        Score score = new Score();
        Direction[] directions = Direction.values();
        for (int i = 0; i < directions.length; i++) {
            Cell neighbor = cell.getNeighbor(directions[i]);
            if (neighbor == null)
            	break;
            else { 
                int value = calculate(neighbor, neighbor.getColor(), directions[i]);
                score.addValue(neighbor.getColor(), value); 
            }
        }
        return score;
    }
    
    /**
     * Calculate an integer score for the specified board, coordinate, direction
     * and color.
     */
    private int calculate(Cell cell, Color color, Direction direction) {
        if (!cell.isOccupied() || !cell.getColor().equals(color)) {
            return 0;
        }
        Cell neighbor = cell.getNeighbor(direction);
        return 1 + calculate(neighbor, color, direction);
    } 
    
   /**
     * Get a neighboring coordinate for the specified coordinate and direction.
    **/
   /* private Coordinate getNeighbor(Coordinate coord, Direction direction) {
        return TransferStrategyFactory.getInstance().move(coord, direction);
    }*/
        
    /**
     * Update this board with the given tile.
     */
    private void update(Placement placement) {
        getCell(placement.getCoordinate0())
                .setColor(placement.getTile().getColor0());
        getCell(placement.getCoordinate1())
                .setColor(placement.getTile().getColor1());
 
    }
    
    /**
     * returns true if there is a neighboring cell with the given coordinate that is not occupied
     */
    private boolean canCellBeUsedAsLegalPlacement(Cell cell) {
    	if (!cell.isOccupied()) {
	    	for (Direction dir : Direction.values()) {
	    		Cell neighbor = cell.getNeighbor(dir);
	    		if (neighbor != null && !neighbor.isOccupied())
	    			return true;
	    	}
    	}
    	return false;
    }
    
    /**
     * returns true if there is a neighboring cell with the given coordinate that is not occupied
     */
    private Set<PlacementLocation> getLegalPlacementsForCell(Cell cell) {
    	Set<PlacementLocation> placements = new LinkedHashSet<PlacementLocation>();
    	if (!cell.isOccupied()) {
	    	for (Direction dir : Direction.values()) {
	    		Cell neighbor = cell.getNeighbor(dir);
	    		if (neighbor == null)
	    			continue;
	    		PlacementLocation placement = new PlacementLocation(cell, neighbor);
	    		if (verifyValidPlacement(placement))
	    			placements.add(placement);
	    	}
    	}
    	return placements;
    }
    
    /**
     * returns true if there is at least one available position to place a tile on the board
     */
    public boolean hasAvailablePositionsForTile() {
    	for (Layer layer : layers) {
    		for (Cell cell : layer.cells) {
    			if (canCellBeUsedAsLegalPlacement(cell))
    				return true;
    		}
    	}
    	return false;
    }
   
    public Set<PlacementLocation> getAvailablePositionsForTile() {
    	Set<PlacementLocation> placements = new LinkedHashSet<PlacementLocation>(); 
    	for (Layer layer : layers) {
    		for (Cell cell : layer.cells) {
    			if (canCellBeUsedAsLegalPlacement(cell))
    				placements.addAll(getLegalPlacementsForCell(cell));
    		}
    	}
    	return placements;
    }
    
    
    @Override
    @Ensures("result != null")
    public String toString() {
        String str = "Board: ";
        for (Layer layer: layers) {
            str += layer.toString();
        }
        return str;
    }
    
    /**
     * Layer is a data structure that a board comprised of.
     */
    class Layer {
        
        /** The distance of this layer **/
        private final int distance;
        
        /** An array of occupied cells in this layer **/
        private final Cell[] cells;
        
        /**
         * @param distance A positive number indicating the size of the layer
         */
        @Requires({
            "distance >= 0"
        })
        Layer(int distance) {
        	if (distance == 0)
        		this.cells = new Cell[1];
        	else 
        		this.cells = new Cell[distance*Constants.NUMBER_OF_SIDES];
            this.distance = distance;
            initialize();
        }
        
        
        public void initialize() {
            for(int i = 0; i < cells.length; i++) {
                cells[i] = new Cell(distance, i);
            }
        }
        
        
        /**
         * Place a hexagon with the given color on the given angle of this layer
         * 
         * @param color The color of a cell
         * @param angle The angle that the hexagon is targeting to
         */
        void place(Color color, int angle) {
            Cell cell = getCell(angle);
            if(cell != null) {
                getCell(angle).setColor(color);
            }        
        }
               
        /**
         * Get the cell on the position that is specified by the given angle
         */
        Cell getCell(int angle){
            return cells[angle];
        }
        
        /**
         * Get size of cells array
         */
        int size() {
            return cells.length;
        }
        
        @Override
        public String toString() {
            String str = "";
            for (Cell cell: cells) {
                if (cell.isOccupied())
                    str += cell + ", ";
            }
            return str;
        }
    }
    
}
