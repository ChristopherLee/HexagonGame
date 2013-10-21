package ingenious.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import ingenious.board.Color;
import ingenious.board.Score;

/**
 *  GUI class to handle drawing one player's score
 */
public class ScoreGUI extends JPanel{
	private static int width = 200;
	private static int height = 300;
	private static int startX = 30;
	private static int xSeperator = 2;
	private final Score score;
	private static final Font font = new Font("Times New Roman", Font.BOLD, 20);
	
	public ScoreGUI(Score score) {
		super();
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(java.awt.Color.WHITE);
		this.score = score;
		
		this.setVisible(true);
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		for (int i = 0; i < Color.values().length; i++) {
			Color color = Color.values()[i];			
			HexagonGUI hexagon = new HexagonGUI(new Point(startX, (i+1)*height/6), null, color);
			hexagon.draw(graphics);
			graphics.setColor(java.awt.Color.BLACK);
			Font origFont = graphics.getFont();
			graphics.setFont(font);
			graphics.drawString("" + score.get(color), startX + GUIConstants.widthOfHexagon + xSeperator, (i+1)*height/6 + 4);
			graphics.setFont(origFont);
		}
	}
}
