package test.core.graphics;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import test.core.Game;
import test.core.GameComponent;

public class GraphicsManager extends GameComponent {

	public int WIDTH = test.core.Game.WIDTH;
	public int HEIGHT = test.core.Game.HEIGHT;

	private BufferStrategy strategy;

	private boolean fullScreen;

	public Graphics2D g;
	
	public TextManager textManager;
	
	public GraphicsManager(Game game) {
		super();
		this.game = game;
		strategy = game.getGameBufferStrategy();
		g = (Graphics2D) strategy.getDrawGraphics();
		textManager = new TextManager(this);
	}

	public void checkFullScreenTriggered() {
		if (getKeyBoard().keyDown(KeyEvent.VK_ESCAPE))
			if (fullScreen) {
				fullScreen = false;
			} else {
				fullScreen = true;
			}
		if (getKeyBoard().keyDown(KeyEvent.VK_F11))
			if (fullScreen) {
				fullScreen = false;
			} else {
				fullScreen = true;
			}
	}

	@Override
	public void run() {
		paint();
		checkFullScreenTriggered();
	}

	/**
	 * Get hold of a graphics context for the accelerated surface and blank it
	 * out.
	 */
	private void paint() {
		g = (Graphics2D) strategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		getSandbox().paint(g);
		g.dispose();
		strategy.show();
	}

	public void setFullScreen(boolean fs) {
		this.fullScreen = fs;
	}

	public boolean getFullScreen(boolean fs) {
		return fullScreen;
	}
}
