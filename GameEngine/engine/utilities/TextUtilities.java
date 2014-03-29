package engine.utilities;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import engine.graphics.GraphicsManager;

/**
 * http://docs.oracle.com/javase/tutorial/2d/text/renderinghints.html Use the
 * constructor TextUtilities() before using this class. Otherwise, it will throw
 * a null pointer exception.
 * 
 * @author Helson
 * 
 */
public class TextUtilities {

	private static Graphics2D g2d;

	public TextUtilities() {
		g2d = GraphicsManager.graphics2D;
	}

	/**
	 * Sets antialias either on or off.
	 * 
	 * @param smoothText
	 *            True = Antialias.
	 */
	public static void setSmoothText(boolean smoothText) {
		if (smoothText) {
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		} else
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}

	public static FontMetrics getFontMetrics(Font font) {
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
	public static void drawCenteredText(Font font, String str, double minXpos,
			double minYpos, int areaWidth, int areaHeight) {

		FontMetrics metrics = getFontMetrics(font);

		int textWidth = metrics.stringWidth(str);
		int textHeight = metrics.getHeight();

		GraphicsManager.graphics2D.drawString(str, (int) (minXpos
				+ (areaWidth / 2) - (textWidth / 2)), (int) (minYpos
				+ (areaHeight / 2) + (textHeight / 3)));
	}

	/**
	 * The width of a specified text with the font.
	 * 
	 * @param font
	 *            The font used to create the text
	 * @param str
	 *            The string itself
	 * @return An integer value.
	 */
	public static int getFontWidth(Font font, String str) {
		FontMetrics metrics = getFontMetrics(font);
		return metrics.stringWidth(str);
	}

	/**
	 * The height of a font.
	 * 
	 * @param font
	 *            Specified font.
	 * @return
	 */
	public static int getFontHeight(Font font) {
		FontMetrics metrics = getFontMetrics(font);
		return metrics.getHeight();
	}

	public static Font getDebugFont() {
		return new Font("Dialog", Font.PLAIN, 12);
	}
}
