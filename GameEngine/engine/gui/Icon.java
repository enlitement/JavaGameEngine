package engine.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import engine.events.MouseMoveEvent;

public class Icon extends AbstractButton {

	public Font font;
	private Color current_color, norm_color, hover_color, on_press_color;
	@SuppressWarnings("unused")
	private int stroke;
	public static final int DEF_ICON_WID = 32, DEF_ICON_H = 32;

	/**
	 * Create a black and white Button.
	 * 
	 * @param text
	 *            The text of the button.
	 */
	public Icon(String text) {
		this(text, Color.white, Color.LIGHT_GRAY, Color.GRAY);
		System.out.println("Height:" + getHeight());
	}

	/**
	 * Create a button with the specified colors.
	 * 
	 * @param text
	 *            The text of the button.
	 */
	public Icon(String text, Color body_color, Color hover_color, Color on_press) {
		super();
		setWidth(DEF_ICON_WID);
		setHeight(DEF_ICON_H);
		setName(text);
		this.current_color = norm_color = body_color;
		this.hover_color = hover_color;
		this.on_press_color = on_press;
		stroke = 2;
		font = new Font("Dialog", Font.PLAIN, 12);
	}

	@Override
	public void customInput() {

	}

	@Override
	public void onHover(MouseMoveEvent e) {
		current_color = hover_color;
	}

	@Override
	public void onPress(MouseMoveEvent e) {
		current_color = on_press_color;
	}

	@Override
	public void onRelease(MouseMoveEvent e) {
		current_color = norm_color;
	}

	public void setNormColor(Color color) {
		this.norm_color = color;
	}

	public void setOnPressColor(Color color) {
		this.on_press_color = color;
	}

	public void setHoverColor(Color color) {
		this.hover_color = color;
	}

	public Color getHoverColor() {
		return hover_color;
	}

	public Color getNormColor() {
		return norm_color;
	}

	public Color getOnPressColor() {
		return on_press_color;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(current_color);
		g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
	}

	public Color getCurrentColor() {
		return current_color;
	}

	public void setCurrentColor(Color current_color) {
		this.current_color = current_color;
	}
}
