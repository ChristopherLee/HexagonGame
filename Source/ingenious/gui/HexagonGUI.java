package ingenious.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import com.google.java.contract.Requires;

import ingenious.board.Color;
import ingenious.board.Coordinate;
import ingenious.board.test.CoordTest;
import ingenious.board.util.Constants;
/**
 * Provides a way to store the coordinates for the polygon to be drawn, the coordinate(distance, angle) on the board, and the color
 * The main function is draw, which allows you to draw a polygon with the specified information on a given graphics
 */
public class HexagonGUI {
	private Polygon polygon;
	/**
	 * coordinate on the hexagon board (distance, angle)
	 */
	private Coordinate coordinate;
	
	private Color color;
	
	public HexagonGUI(Point centerPoint, Coordinate coordinate, Color color) {
		this.polygon = getPolygon(centerPoint, GUIConstants.sideOfHexagonLength);
		this.coordinate = coordinate;
		this.color = color;
	}
	
	/**
	 * return the polygon object for a Hexagon at a certain point with a given size for one side
	 */
	private static Polygon getPolygon(Point point, int sideLength) {
		double[] xDeltas = {-.5, .5, 1, .5, -.5, -1};
		double[] yDeltas = {-1, -1, 0, 1, 1, 0};
		int[] xCoordinates = new int[Constants.NUMBER_OF_SIDES];
		int[] yCoordinates = new int[Constants.NUMBER_OF_SIDES];
		for (int i = 0; i < xDeltas.length; i++) {
			xCoordinates[i] = (int) (point.getX() + (xDeltas[i]*(double)sideLength));
			yCoordinates[i] = (int) (point.getY() + (yDeltas[i]*(double)sideLength));
		}
		
		return new Polygon(xCoordinates, yCoordinates, Constants.NUMBER_OF_SIDES);
	}
	
	/**
	 * draw a Hexagon onto the graphics at the given point, and fill with a specific color.  If null, fill with no color
	 */
	@Requires({
		"graphics != null"
	})
	public void draw(Graphics graphics) {
		graphics.setColor(java.awt.Color.BLACK);
		graphics.drawPolygon(polygon);
		Point centerPoint = getCenterPoint(polygon);
		
		
		if (color != null) {
			
			switch (color) {
				case BLUE:
					drawStar(graphics, java.awt.Color.BLUE, centerPoint);
					break;
				case ORANGE:
					graphics.setColor(java.awt.Color.ORANGE);
					Polygon innerPolygon = 
						getPolygon(new Point((int)centerPoint.getX() + 1, (int)centerPoint.getY() + 1), 
									GUIConstants.sideOfHexagonLength - 4);
					graphics.fillPolygon(innerPolygon);					
					break;
				case YELLOW:
					graphics.setColor(java.awt.Color.YELLOW);
					graphics.fillOval((int)(centerPoint.getX() - (.5 * GUIConstants.sideOfHexagonLength)), 
							  (int)(centerPoint.getY() - (.5 * GUIConstants.sideOfHexagonLength)), 
							  GUIConstants.sideOfHexagonLength+2, 
							  GUIConstants.sideOfHexagonLength+2);
					break;
				case PURPLE:
					graphics.setColor(new java.awt.Color(255, 0, 255));
					graphics.fillOval((int)(centerPoint.getX() - (.5 * GUIConstants.sideOfHexagonLength)), 
							  (int)(centerPoint.getY() - (.5 * GUIConstants.sideOfHexagonLength)), 
							  GUIConstants.sideOfHexagonLength+3, 
							  GUIConstants.sideOfHexagonLength+3);
					graphics.setColor(java.awt.Color.WHITE);
					graphics.fillOval((int)(centerPoint.getX() - (.25 * GUIConstants.sideOfHexagonLength)), 
							  (int)(centerPoint.getY() - (.25 * GUIConstants.sideOfHexagonLength)), 
							  GUIConstants.sideOfHexagonLength-5, 
							  GUIConstants.sideOfHexagonLength-5);
					break;								
				case RED:
					graphics.setColor(java.awt.Color.RED);
					graphics.fillPolygon(polygon);
					graphics.setColor(java.awt.Color.BLACK);
					graphics.drawString("R", (int)centerPoint.getX(), (int)centerPoint.getY());
					break;
				case GREEN:
					graphics.setColor(java.awt.Color.GREEN);
					graphics.fillOval((int)(centerPoint.getX() - (.5 * GUIConstants.sideOfHexagonLength)), 
									  (int)(centerPoint.getY() - (.5 * GUIConstants.sideOfHexagonLength)), 
									  GUIConstants.sideOfHexagonLength+2, 
									  GUIConstants.sideOfHexagonLength+2);
					break;			
			}
		}
		else{
			if (coordinate != null)
				graphics.drawString(coordinate.toString(), centerPoint.x - GUIConstants.widthOfHexagon / 2, centerPoint.y + 5);
		}
	}
	
	/*private double circleY(int numSides, int side) {
		double coefficient = (double) side / (double)numSides;
		return Math.sin(2 *coefficient * Math.PI - halfPI);
	}
	
	private double circleX(int numSides, int side) {
		double coefficient = (double) side / (double)numSides;
		return Math.cos(2 *coefficient * Math.PI - halfPI);
	}
	
	private void drawStar(Graphics graphics, java.awt.Color color, Point point, int numSides, int size) {
		graphics.setColor(color);
		for (int i = 0; i < numSides; i++) {
			int x1 = (int)(circleX(numSides, i) * (double)size) + (int)point.getX();
			int y1 = (int)(circleY(numSides, i) * (double)size) + (int)point.getX();
			int x2 = (int)(circleX(numSides, (i+2)%numSides) * (double)size) + (int)point.getX();
			int y2 = (int)(circleY(numSides, (i+2)%numSides) * (double)size) + (int)point.getX();
			graphics.drawLine(x1, y1, x2, y2);
		}
	}*/
	
	private void drawStar(Graphics graphics, java.awt.Color color, Point point) {
		graphics.setColor(color);
		Polygon polygon = new Polygon();
		polygon.addPoint((int)point.getX() - 2, (int)point.getY() - 2);
		polygon.addPoint((int)point.getX(),     (int)point.getY() - 8);
		polygon.addPoint((int)point.getX() + 2, (int)point.getY() - 2);
		polygon.addPoint((int)point.getX() + 8, (int)point.getY() - 2);
		polygon.addPoint((int)point.getX() + 3, (int)point.getY() + 3);
		polygon.addPoint((int)point.getX() + 6, (int)point.getY() + 8);
		polygon.addPoint((int)point.getX(),     (int)point.getY() + 6);
		polygon.addPoint((int)point.getX() - 6, (int)point.getY() + 8);
		polygon.addPoint((int)point.getX() - 3, (int)point.getY() + 3);
		polygon.addPoint((int)point.getX() - 8, (int)point.getY() + 2);
		
		graphics.fillPolygon(polygon);
	}
	
	/**
	 * given a polygon, determine the center point of the polygon
	 */
	private static Point getCenterPoint(Polygon polygon) {
		int centerPointX = polygon.xpoints[0] + (int)(.5*GUIConstants.sideOfHexagonLength);
		int centerPointY = polygon.ypoints[0] + GUIConstants.sideOfHexagonLength;
		return new Point(centerPointX, centerPointY);
	}
	
	/**
	 * provides an equals method for 2 polygons, for testing purposes
	 */
	public static boolean polygonEquals(Polygon polygon1, Polygon polygon2) {
		for (int i = 0; i < polygon1.xpoints.length; i++) {
			if (polygon1.xpoints[i] == polygon2.xpoints[i] &&
				polygon1.ypoints[i] == polygon2.ypoints[i])
				continue;
			else
				return false;
		}
		return true;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}
}
