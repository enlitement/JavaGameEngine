package test.core.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import test.core.Game;
import test.core.input.KeyboardInput;

public class GraphicsManager {

	private Game game;
	private static GraphicsManager graphicsManager;

	public int WIDTH = test.core.Game.WIDTH;
	public int HEIGHT = test.core.Game.HEIGHT;

	private BufferStrategy strategy;

	private boolean fullScreen;

	public Graphics2D g;

	public TextManager textManager;

	private GraphicsManager() {

	}

	public static synchronized GraphicsManager getInstance() {
		if (graphicsManager == null) {
			graphicsManager = new GraphicsManager();
		}
		return graphicsManager;
	}

	public void initialize(Game game) {
		this.game = game;
		strategy = game.getGameBufferStrategy();
		g = (Graphics2D) strategy.getDrawGraphics();
		textManager = new TextManager(this);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void checkFullScreenTriggered() {
		if (KeyboardInput.getInstance().keyDown(KeyEvent.VK_ESCAPE))
			if (fullScreen) {
				fullScreen = false;
			} else {
				fullScreen = true;
			}
		if (KeyboardInput.getInstance().keyDown(KeyEvent.VK_F11))
			if (fullScreen) {
				fullScreen = false;
			} else {
				fullScreen = true;
			}
	}

	public void run() {
		paint();
		checkFullScreenTriggered();
	}

	/**
	 * Get hold of a graphics context for the accelerated surface and blank it
	 * out.
	 */
	private void paint() {
		try {
			g = (Graphics2D) strategy.getDrawGraphics();
			game.getSandbox().paint(g);
		} finally {
			g.dispose();
		}
		strategy.show();

	}

	public void setFullScreen(boolean fs) {
		this.fullScreen = fs;
	}

	public boolean getFullScreen(boolean fs) {
		return fullScreen;
	}
}
