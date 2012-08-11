package test.core;

public abstract class GameComponent implements Runnable {
	public Game game;

	public GameComponent(Game game) {
		this.game = game;
	}

	public ResourceManager getResources() {
		return game.resourceManager;
	}

	public GraphicsManager getGraphics() {
		return game.graphicsManager;
	}

	public StateManager getStateMan() {
		return game.stateManager;
	}

	public KeyboardInput getKeyBoard() {
		return game.keyboard;
	}
	public abstract void run();

	public void sleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}
}
