package test.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

public class GraphicsManager extends GameComponent implements Runnable {

	final int WIDTH = test.core.Game.WIDTH;
	final int HEIGHT = test.core.Game.HEIGHT;

	public BufferStrategy strategy;

	public Graphics2D g;

	public GraphicsManager(Game game) {
		super(game);
		g = null;
		strategy = game.strategy;
	}

	@Override
	public void run() {
		paint();
		try {
			Thread.sleep(1);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/**
	 * Paints using the StateManager.paint method;
	 */
	public void paint() {
		// Get hold of a graphics context for the accelerated
		// surface and blank it out
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		getStateMan().paint(g);
		g.dispose();
		strategy.show();
	}
}
