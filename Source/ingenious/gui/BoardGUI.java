package ingenious.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;

import ingenious.board.Board;
import ingenious.board.Cell;
import ingenious.board.Coordinate;
import ingenious.board.Direction;

/**
 * JPanel to display the board
 */
public class BoardGUI extends JPanel{
	static int width = 560;
	static int height = 700;
	
	/**  widthOfHexagon
	 *  |---|
	 *   __   ___
	 *  /  \   | <-- heightOfHexagon
	 *  \__/  _|_   
	 * \
	 */


	// list of all hexagon objects to paint
	private final List<HexagonGUI> hexagons;
	
	/**
	 * constructor takes in a board, and generates a list of hexagons that should be drawn for the board
	 */
	public BoardGUI(Board board) {
		super();
		setPreferredSize(new Dimension(width, height));
                setMaximumSize(new Dimension(width, height));
		this.setBackground(java.awt.Color.WHITE);
		this.hexagons = new ArrayList<HexagonGUI> ();
		HashMap<Coordinate, Boolean> drawnCoordinates = new HashMap<Coordinate, Boolean> ();
		drawCellAndNeighbors(board, board.getCell(0, 0), new Point(width/2, height/2), drawnCoordinates, getGraphics());
	}
	
	public List<HexagonGUI> getHexagons () {
		return hexagons;
	}
	
	/**
	 * uses the hexagons list to draw each hexagon on the given graphics
	 */
	@Override
	public void paintComponent(Graphics graphics) {
		graphics.setFont(GUIConstants.coordFont);
		super.paintComponent(graphics);
		for (HexagonGUI hexagon : hexagons) {
			hexagon.draw(graphics);
		}
	}
	
	/**
	 * recursive function that populates hexagons list with HexagonGUI objects for each cell
	 *  Note: currently not very efficient, may revisit to devise better algorithm. 
	 *  Generative Recursion via accumulator 
	 */
	private void drawCellAndNeighbors(Board board, Cell cell, Point point, HashMap<Coordinate, Boolean> drawnCoordinates, Graphics graphics) {
		Coordinate coord = cell.getCoordinate();
		if (drawnCoordinates.containsKey(coord) || coord == null)
			return;
		hexagons.add(new HexagonGUI(point, coord, cell.getColor()));
		drawnCoordinates.put(cell.getCoordinate(), true);
		drawNeighbor(board, cell, point, Direction.N, drawnCoordinates, graphics);
		drawNeighbor(board, cell, point, Direction.NE, drawnCoordinates, graphics);
		drawNeighbor(board, cell, point, Direction.SE, drawnCoordinates, graphics);
		drawNeighbor(board, cell, point, Direction.S, drawnCoordinates, graphics);
		drawNeighbor(board, cell, point, Direction.SW, drawnCoordinates, graphics);
		drawNeighbor(board, cell, point, Direction.NW, drawnCoordinates, graphics);
	}

	/**
	 * helper function to draw a neighbor
	 */
	private void drawNeighbor(Board board, Cell cell, Point point, Direction direction, HashMap<Coordinate, Boolean> drawnCoordinates, Graphics graphics) {
		Cell neighbor = cell.getNeighbor(direction);
		if (neighbor == null)
			return;
		drawCellAndNeighbors(board, neighbor, getAnchorPoint(point, direction), drawnCoordinates, graphics);
	}

	/*
	 * calculates the center-point of the hexagon to the <dir> of the point 
	 */
	private Point getAnchorPoint(Point point, Direction dir) {
		int seperator = 0;
		Point nextPoint = (Point)point.clone();
		switch(dir) {
			case N:
				nextPoint.translate(0, (-1 * GUIConstants.heightOfHexagon) - seperator);
				break;
			case NE:
				nextPoint.translate(GUIConstants.widthOfHexagon + seperator, (int) (((-.5) * GUIConstants.heightOfHexagon) - seperator));
				break;
			case SE:
				nextPoint.translate((int)(GUIConstants.widthOfHexagon) + seperator, (int)(.5 * GUIConstants.heightOfHexagon) + seperator);
				break;
			case S:
				nextPoint.translate(0, 1 * GUIConstants.heightOfHexagon + seperator);
				break;
			case SW:
				nextPoint.translate((int)(-1 * GUIConstants.widthOfHexagon) - seperator, (int)(.5 * GUIConstants.heightOfHexagon) + seperator);
				break;
			case NW:
				nextPoint.translate((int)-1 * GUIConstants.widthOfHexagon - seperator, (int)(-.5 * GUIConstants.heightOfHexagon) - seperator);
				break;
		}
		return nextPoint;
	}
}
