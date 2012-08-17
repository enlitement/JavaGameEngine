package test.core.graphics;

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
}
