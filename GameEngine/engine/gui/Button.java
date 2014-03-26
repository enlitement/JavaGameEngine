package engine.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import engine.events.MouseMoveEvent;
import engine.graphics.GraphicsManager;
import engine.utilities.TextUtilities;

public class Button extends AbstractButton {

	public Font font;
	private Color body_color, regular_color, text_color, hover_color, on_press;
	private int stroke;

	/**
	 * Create a black and white Button.
	 * 
	 * @param text
	 *            The text of the button.
	 */
	public Button(String text) {
		this(text, Color.white, Color.black, Color.LIGHT_GRAY, Color.GRAY);
	}

	/**
	 * Create a button with the specified colors.
	 * 
	 * @param text
	 *            The text of the button.
	 */
	public Button(String text, Color body_color, Color text_color,
			Color hover_color, Color on_press) {
		super();
		setName(text);
		this.text_color = text_color;
		this.body_color = regular_color = body_color;
		this.hover_color = hover_color;
		this.on_press = on_press;
		stroke = 2;
		font = new Font("Dialog", Font.PLAIN, 12);
	}

	@Override
	public void customInput() {

	}

	@Override
	public void onHover(MouseMoveEvent event) {
		body_color = hover_color;
	}

	@Override
	public void onPress(MouseMoveEvent event) {
		body_color = on_press;
	}

	@Override
	public void onRelease(MouseMoveEvent event) {
		body_color = regular_color;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(text_color);
		GraphicsManager.get().drawRectOutline(g, (int) getX(), (int) getY(),
				getWidth(), getHeight(), stroke);
		g.setColor(body_color);
		g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
		g.setColor(text_color);
		TextUtilities.drawCenteredText(font, getName(),
				(int) getX(), (int) getY(), getWidth(), getHeight());
		g.setColor(Color.red);
	}
}