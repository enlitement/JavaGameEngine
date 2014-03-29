package engine.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import engine.core.Screen;
import engine.utilities.TextUtilities;

/**
 * Singleton class that uses page flipping to render the game.
 * 
 * @author Helson
 * 
 */
public class GraphicsManager {

	private static GraphicsManager graphicsManager;
	private static BufferStrategy strategy;
	// private boolean fullScreen;
	public static Graphics2D graphics2D;

	private GraphicsManager() {

	}

	public static synchronized GraphicsManager get() {
		if (graphicsManager == null) {
			graphicsManager = new GraphicsManager();
		}
		return graphicsManager;
	}

	public void initialize(Screen screen) {
		strategy = screen.getGameBufferStrategy();
		graphics2D = (Graphics2D) strategy.getDrawGraphics();
		new TextUtilities();
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public static void CLEAR_SCREEN() {
		graphics2D = (Graphics2D) strategy.getDrawGraphics();
		graphics2D.clearRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
	}

	public static void BLIT_SURFACE() {
		graphics2D.dispose();
		strategy.show();
	}

	/**
	 * Fills a ratio between two integers. Draws a rectangular outline.
	 * 
	 * @param g
	 *            Graphics2D object
	 * @param xpos
	 * @param ypos
	 * @param currentInt
	 *            Numerator of ratio
	 * @param maxInt
	 *            Denominator or ratio
	 * @param width
	 *            Width of rectangle
	 * @param height
	 *            Thickness of bar
	 */
	public void drawStatusBar(Graphics2D g, int xpos, int ypos, int currentInt,
			int maxInt, int width, int height) {
		double length = (double) (currentInt) / maxInt * width;
		g.fillRect(xpos, ypos, (int) (length), (int) height);
		g.drawRect(xpos, ypos, width, height);
	}

	/**
	 * Draws a rectangular outline with specified stroke size.
	 * 
	 * @param g
	 * @param xpos
	 * @param ypos
	 * @param width
	 * @param height
	 * @param stroke_size
	 */
	public void drawRectOutline(Graphics2D g, int xpos, int ypos, int width,
			int height, int stroke_size) {

		int one_side = (stroke_size - 1) / 2;

		for (int i = -one_side; i < one_side; i++) {
			g.drawRect(xpos + i, ypos + i, width - 2 * i, height - 2 * i);
		}
	}
}
