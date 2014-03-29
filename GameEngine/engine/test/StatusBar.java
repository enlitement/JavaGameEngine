package engine.test;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.core.graphics.GraphicsManager;
import engine.gui.AbstractGUI;
import engine.gui.GameGUI;

public class StatusBar extends AbstractGUI {

	public Color color;
	public int currentInt, maxInt;
	public GameGUI gui;

	/**
	 * 
	 * @param gui
	 *            Room of GUI
	 * @param color
	 *            Color of bar
	 * @param currentInt
	 *            Current integer of health, mana, etc. (numerator of fraction)
	 * @param maxInt
	 *            Max integer of health, mana. (denominator of fraction)
	 * @param xpos
	 *            Position
	 * @param ypos
	 *            Position
	 * @param height
	 *            Thickness of bar
	 */
	public StatusBar(GameGUI gui, Color color, int xpos, int ypos,
			int currentInt, int maxInt, int width, int height) {
		super(gui);
		this.gui = gui;
		this.color = color;
		this.width = width;
		this.height = height;
		this.currentInt = currentInt;
		this.maxInt = maxInt;
		setPosition(xpos, ypos);
	}

	@Override
	public void enableClickEffect() {

	}

	@Override
	public void revertClickEffect() {

	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(color);
		GraphicsManager.get().drawStatusBar(g, (int) xpos, (int) ypos,
				currentInt, maxInt, width, height);
	}

	public void update(int currentInt) {
		this.currentInt = currentInt;
	}

	@Override
	public void customInput() {
	}
}