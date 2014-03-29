package engine.utilities;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JDialog;

public class Utilities {
	private static Utilities utilities = null;

	private Utilities() {

	}

	public static synchronized Utilities get() {
		if (utilities == null) {
			utilities = new Utilities();
		}
		return utilities;
	}

	/**
	 * Checks if the x and y parameters are within the rectangle created by
	 * xpos, ypos, width, and height.
	 * 
	 * @param xpos
	 * @param ypos
	 * @param width
	 * @param height
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean withinBounds(double xpos, double ypos, double width,
			double height, int x, int y) {
		return x > xpos && x < xpos + width && y > ypos && y < ypos + height;
	}

	/**
	 * Draws a rectangle with two different colors, like the ones you see in GUI
	 * applications.
	 * 
	 * @param g
	 *            The Graphics2D object
	 * @param x
	 *            The x position of the rectangle
	 * @param y
	 *            The y position of the rectangle
	 * @param width
	 *            The width of the rectangle
	 * @param height
	 *            The height of the rectangle
	 * @param color1
	 *            The color of the left and top sides of the rectangle
	 * @param color2
	 *            The color of the bottom and right sides of the rectangle
	 */
	public static void drawTwoColorBox(Graphics2D g, int x, int y, int width,
			int height, Color color1, Color color2) {
		g.setColor(color1);
		// Line top
		g.drawLine(x, y, x + width, y);
		// Line left
		g.drawLine(x, y, x, y + height);

		g.setColor(color2);
		// Line bottom
		g.drawLine(x + width, y + height, x, y + height);
		// Line right
		g.drawLine(x + width, y + height, x + width, y);
	}
	
	/**
	 * A quick way to make a JOptionPane for error warnings. 
	 * @param title The title of the window.
	 * @param message The message it will present.
	 */
	public static void showErrorDialog(String title, String message) {
		javax.swing.JOptionPane.showMessageDialog(null, message, title,
				javax.swing.JOptionPane.ERROR_MESSAGE);
	}
}
