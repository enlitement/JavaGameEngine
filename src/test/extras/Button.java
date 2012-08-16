package test.extras;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import test.core.Sandbox;
import test.core.input.MouseInput;
import test.gameInterfaces.Paintable;
import test.objects.GameObject;

public abstract class Button extends GameObject implements Paintable {

	public String text;
	public int height, width;
	public boolean pressed;
	public Color mainColor, displayColor;
	public static final Color DARK_BLUE = new Color(0x00, 0x00, 0xC0);

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
		height = 50;
		width = 200;
		mainColor = Color.blue;
		displayColor = mainColor;
		pressed = false;
		rec = new Rectangle(x, y, width, height);
	}

	/**
	 * Creates new round rectangle with specified parameters. Color is blue.
	 * 
	 * @param text
	 *            String to print at button
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 * @param width
	 *            Width of button
	 * @param height
	 *            Height of button
	 */
	public Button(Sandbox sandbox, String text, int x, int y, int width,
			int height) {
		super(sandbox, text + " Button");
		this.text = text;
		this.xpos = x;
		this.ypos = y;
		this.height = height;
		this.width = width;
		mainColor = Color.blue;
		displayColor = mainColor;
		pressed = false;
		rec = new Rectangle(x, y, width, height);
	}

	/**
	 * Creates new round rectangle with specified parameters.
	 * 
	 * @param text
	 *            String to print at button
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 * @param width
	 *            Width of button
	 * @param height
	 *            Height of button
	 * @param color
	 *            Color of button
	 */
	public Button(Sandbox sandbox, String text, int x, int y, int width,
			int height, Color color) {
		super(sandbox, text + " Button");
		this.text = text;
		this.xpos = x;
		this.ypos = y;
		this.height = height;
		this.width = width;
		this.mainColor = color;
		displayColor = mainColor;
		pressed = false;
		rec = new Rectangle(x, y, width, height);
	}

	protected abstract void processInput();

	public void setDisplayColor(Color color) {
		this.mainColor = color;
	}

	public void mouseClickEffect(Color color) {
		setDisplayColor(color);
	}

	/**
	 * Makes the button have the effect of a button being pressed by making the
	 * color of the button dark blue.
	 */
	public void enableClickEffect() {
		displayColor = DARK_BLUE;
	}

	public void revertClickEffect() {
		displayColor = mainColor;
	}

	@Override
	public void paint(Graphics2D g) {
		paintButton(g);
	}

	public void paintButton(Graphics2D g) {
		g.setColor(displayColor);
		g.fillRoundRect((int) xpos, (int) ypos, width, height, 16, 12);
		g.setColor(Color.black);
		g.drawString(text, (int) (xpos + width / 8), (int) (ypos + height / 2));
	}

	/**
	 * Checks to see if the mouse click was in the bounds of the button.
	 * 
	 * @param e
	 * @return
	 */
	public boolean mouseWithinBounds(MouseInput e) {
		if (rec.contains(e.getPosition()))
			return true;
		return false;
	}
}
