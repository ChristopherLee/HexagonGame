package ingenious.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

import ingenious.board.Tile;

/**
 * GUI class to handle drawing list of the tiles in a player's hand
 */
public class HandGUI extends JPanel{
	private static int width = 120;
	private static int height = 80;
	private final List<Tile> hand;
	
	public HandGUI(List<Tile> hand) {
		super();
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(java.awt.Color.WHITE);
		this.hand = hand;
		
		this.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		int startingX = 20;
		int startingY = 20;
		int verticalDistanceSeperation = 2*GUIConstants.sideOfHexagonLength;
		int horizontalDistanceSeperation = 3*GUIConstants.sideOfHexagonLength;
		int seperatorBetweenHexagons = 1;
		for (int i = 0; i < hand.size(); i++) {
			if (!hand.get(i).isNull()) {
				HexagonGUI hexagon1 = new HexagonGUI(new Point(startingX + (i*horizontalDistanceSeperation), startingY), null, hand.get(i).getColor0());
				hexagon1.draw(graphics);
				HexagonGUI hexagon2 = new HexagonGUI(new Point(startingX + (i*horizontalDistanceSeperation), startingY + verticalDistanceSeperation + seperatorBetweenHexagons), null, hand.get(i).getColor1());
				hexagon2.draw(graphics);
			}
		}
	}
	
}
