package engine.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import engine.core.Sandbox;
import engine.core.graphics.GraphicsManager;
import engine.core.input.MouseInput;
import engine.interfaces.Paintable;
import engine.objects.GameObject;

public abstract class Button extends GameObject implements Paintable {

	public int height, width, textHeight, textWidth;
	public boolean pressed, clicked;
	public String text;
	public Color gradient1, gradient2, light, dark, outline;
	public Font menuFont;

	private List<ActionListener> _listeners = new ArrayList<ActionListener>();

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
		outline = Color.black;
		// Set up nice text for button
		GraphicsManager.get().textManager.setSmoothText(true);
		menuFont = new Font("Dialog", Font.BOLD, 13);
		FontMetrics metrics = GraphicsManager.get().textManager
				.getFontMetrics(menuFont);

		textHeight = metrics.getHeight();
		textWidth = metrics.stringWidth(text);

		height = 40;
		width = 100;

		//if (textWidth > width) 
		//	fontIsLegal();

		pressed = false;

	}

	public void fontIsLegal() {
		boolean isLegal = false;
		while (!isLegal) {
			if (textWidth > width) {
				menuFont = menuFont.deriveFont(menuFont.getSize() - 2f);
				FontMetrics metrics = GraphicsManager.get().textManager
						.getFontMetrics(menuFont);

				textHeight = metrics.getHeight();
				textWidth = metrics.stringWidth(text);
			}
			else
				isLegal = true;
			fontIsLegal();
		}
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
		outline = Color.black;
		pressed = false;

		// Set up nice text for button
		GraphicsManager.get().textManager.setSmoothText(true);
		menuFont = new Font("Dialog", Font.BOLD, 13);
		FontMetrics metrics = GraphicsManager.get().textManager
				.getFontMetrics(menuFont);

		textHeight = metrics.getHeight();
		textWidth = metrics.stringWidth(text);

		// System.out.println("Text height" + textHeight);
		// System.out.println("Text width" + textWidth);

		height = 40;
		width = 100;

		rec = new Rectangle(x, y, width, height);

	}

	/**
	 * Checks if the button is pressed, or buttonDownOnce(1), or if the button
	 * has been clicked (if it is pressed and then released within button
	 * bounds).
	 */
	public void processInput() {
		if (mouseWithinBounds(MouseInput.getInstance())
				&& MouseInput.getInstance().buttonDownOnce(1))
			pressed = true;

		if (!MouseInput.getInstance().buttonDown(1)) {
			if (pressed && mouseWithinBounds(MouseInput.getInstance())) {
				clicked = true;
				clicked();
			}
			pressed = false;
		}
		if (clicked && !mouseWithinBounds(MouseInput.getInstance()))
			clicked = false;

	}

	/**
	 * If pressed, enables the click effect. If not, reverts to default.
	 */
	public void clickEffects() {
		if (pressed)
			enableClickEffect();
		if (!pressed)
			revertClickEffect();
	}

	public void changeOutlineColor(Color color) {
		this.outline = color;
	}
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

	/**
	 * Change this to change the primary button color. The secondary color is
	 * white by default.
	 * 
	 * @param color
	 *            The main button color.
	 */
	public void setDisplayColor(Color color) {
		setGradient1(color);
	}

	public void setGradient1(Color color) {
		this.gradient1 = color;
		this.dark = color;
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
		g.setColor(outline);
		g.drawRoundRect((int) xpos, (int) ypos, width, height, 3, 3);

		// Draw text
		g.setColor(Color.black);
		GraphicsManager.get().textManager.drawCenteredText(menuFont, text,
				xpos, ypos, width, height);

	}
	
	public abstract void update();

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

	// Action Listener methods
	public synchronized void clicked() {
		fireEvent();
	}

	public synchronized void addActionListener(ActionListener listener) {
		_listeners.add(listener);
	}

	public synchronized void removeActionListener(ActionListener listener) {
		_listeners.remove(listener);
	}

	private synchronized void fireEvent() {
		ActionEvent evt = new ActionEvent(this);
		Iterator<ActionListener> listeners = _listeners.iterator();
		while (listeners.hasNext()) {
			((ActionListener) listeners.next()).actionPerformed(evt);
		}
	}
}