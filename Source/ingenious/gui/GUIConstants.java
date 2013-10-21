package ingenious.gui;

import java.awt.Font;

public class GUIConstants {
	static int sideOfHexagonLength = 20;
	static int heightOfHexagon = sideOfHexagonLength * 2;
	static int widthOfHexagon = (int)Math.floor(sideOfHexagonLength * Math.sqrt(3)) - 2;
	static Font coordFont = new Font("TimesRoman", Font.PLAIN, (int)(sideOfHexagonLength *.75));
}
