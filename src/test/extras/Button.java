package test.extras;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import test.core.Sandbox;
import test.core.graphics.GraphicsManager;
import test.core.input.MouseInput;
import test.gameInterfaces.Paintable;
import test.objects.GameObject;

public abstract class Button extends GameObject implements Paintable {

	public String text;
	public int height, width;
	public boolean pressed, clicked;
	public Color gradient1, gradient2;
	public Color light, dark;
	public Font menuFont;
	public int textHeight, textWidth;

	/**
	 * Draw a blue button a position with specified text.
	 * 
	 * @param sandbox
	 *            The sanbox you want to attribute the button to.
	 * @param text
	 *            The text to display
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 */
	public Button(Sandbox sandbox, String text) {
		super(sandbox, text + " Button");
		this.text = text;

		gradient1 = Color.blue;
		gradient2 = Color.white;
		dark = gradient1;
		light = gradient2;

		System.out.println("Button name:" + text);
		// Set up nice text for button
		GraphicsManager.getInstance().textManager.setSmoothText(true);
		menuFont = new Font("Dialog", Font.BOLD, 16);
		FontMetrics metrics = GraphicsManager.getInstance().textManager.getFontMetrics(menuFont);

		textHeight = metrics.getHeight();
		textWidth = metrics.stringWidth(text);

		System.out.println("Text height" + textHeight);
		System.out.println("Text width" + textWidth);

		height = 40;
		width = 100;

		pressed = false;

	}

	public boolean fontIsLegal(Font font, int width, int height) {

		return true;
	}

	/**
	 * Draw a blue button a position with specified text.
	 * 
	 * @param sandbox
	 *            The sanbox you want to attribute the button to.
	 * @param text
	 *            The text to display
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 */
	public Button(Sandbox sandbox, String text, int x, int y) {
		super(sandbox, text + " Button");
		this.text = text;
		this.xpos = x;
		this.ypos = y;
		gradient1 = Color.blue;
		gradient2 = Color.white;
		dark = gradient1;
		light = gradient2;
		pressed = false;

		// Set up nice text for button
		menuFont = new Font("Dialog", Font.BOLD, 18);
		FontMetrics metrics = GraphicsManager.getInstance().textManager.getFontMetrics(menuFont);
		GraphicsManager.getInstance().textManager.setSmoothText(true);

		textHeight = metrics.getHeight();
		textWidth = metrics.stringWidth(text);

		height = 40;
		width = 100;

		rec = new Rectangle(x, y, width, height);

	}

	protected abstract void processInput();

	/**
	 * Called from within a layoutManager to make sure the rectangle is set up
	 * for click detection.
	 * 
	 * @param xpos
	 * @param ypos
	 */
	public void setPosition(int xpos, int ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
		rec = new Rectangle(xpos, ypos, width, height);
	}

	public void setDisplayColor(Color color) {
		setGradient1(color);
	}

	public void setGradient1(Color color) {
		this.gradient1 = color;
	}

	/**
	 * Makes the button have the effect of a button being pressed by making the
	 * color of the button dark blue.
	 */
	public void enableClickEffect() {
		gradient1 = light;
		gradient2 = dark;
	}

	/**
	 * Makes colors go back to their original spots
	 */
	public void revertClickEffect() {
		gradient1 = dark;
		gradient2 = light;
	}

	@Override
	public void paint(Graphics2D g) {
		paintButton(g);
	}

	private void paintButton(Graphics2D g) {
		g.setFont(menuFont);

		GradientPaint gp1 = new GradientPaint((int) (xpos + width / 2),
				(int) ypos, gradient1, (int) (xpos + width / 2), (int) ypos
						+ height + 20, gradient2, true);

		// Draw gradient
		g.setPaint(gp1);
		g.fillRoundRect((int) xpos, (int) ypos, width, height, 3, 3);

		// Draw outline
		g.setColor(Color.black);
		g.drawRoundRect((int) xpos, (int) ypos, width, height, 3, 3);

		// Draw text
		g.setColor(Color.black);

		g.drawString(text, (int) (xpos + (width / 2) - (textWidth / 2)),
				(int) (ypos + height / 2));

		// Draw underline
		g.drawLine((int) (xpos + width / 2 - textWidth / 2),
				(int) (ypos + height / 2),
				(int) (xpos + width / 2 + textWidth), (int) (ypos + height / 2));

		g.drawLine((int) (xpos + width / 2), (int) (ypos),
				(int) (xpos + width / 2), (int) (ypos + height));

	}

	/**
	 * Checks to see if the mouse click was in the bounds of the button.
	 * 
	 * @param e
	 *            MouseInput
	 * @return
	 */
	public boolean mouseWithinBounds(MouseInput e) {
		if (rec.contains(e.getPosition()))
			return true;
		return false;
	}

	public void addAction(ButtonAction action) {
		
	}

}
