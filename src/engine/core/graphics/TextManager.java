package engine.core.graphics;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * http://docs.oracle.com/javase/tutorial/2d/text/renderinghints.html
 * 
 * @author Helson
 * 
 */
public class TextManager {

	public FontMetrics metrics;

	public GraphicsManager graphicsManager;
	public Graphics2D g2d;

	public TextManager(GraphicsManager graphicsManager) {
		this.graphicsManager = graphicsManager;
		g2d = graphicsManager.g;
	}

	/**
	 * Sets antialias either on or off.
	 * @param smoothText True = Antialias.
	 */
	public void setSmoothText(boolean smoothText) {
		if (smoothText)
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		else
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}

	public FontMetrics getFontMetrics(Font font) {
		return g2d.getFontMetrics();
	}
	

	/**
	 * Automatically draws the text onto the screen by centering it according to
	 * the parameters;
	 * 
	 * @param font
	 *            The font you would like to use
	 * @param str
	 *            The string of text
	 * @param minXpos
	 *            The leftmost x position of the area
	 * @param minYpos
	 *            The topmost y position of the area
	 * @param areaWidth
	 *            The width of the area
	 * @param areaHeight
	 *            The height of the area
	 */
	public void drawCenteredText(Font font, String str, double minXpos, double minYpos,
			int areaWidth, int areaHeight) {

		FontMetrics metrics = GraphicsManager.get().textManager
				.getFontMetrics(font);

		int textWidth = metrics.stringWidth(str);

		GraphicsManager.get().g.drawString(str, (int)(minXpos
				+ (areaWidth / 2) - (textWidth / 2)), (int)(minYpos + (areaHeight / 2)
				));
	}
	
	public int getFontWidth(Font font, String str) {
		FontMetrics metrics = getFontMetrics(font);
		System.out.println("Size from metrics:"+metrics.stringWidth(str));
		return metrics.stringWidth(str);
	}
	
	public int getFontHeight(Font font) {
		FontMetrics metrics = getFontMetrics(font);
		return metrics.getHeight();
	}
}
