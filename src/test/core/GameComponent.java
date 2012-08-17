package test.core;

import test.core.graphics.GraphicsManager;
import test.core.graphics.TextManager;
import test.core.input.KeyboardInput;
import test.core.input.MouseInput;
import test.core.resources.ResourceManager;

public abstract class GameComponent{

	public Game game;
	
	public GameComponent() {
		
	}
	
	public ResourceManager getResources() {
		return game.getResourceManager();
	}

	public GraphicsManager getGraphics() {
		return game.getGraphicsManager();
	}

	public Sandbox getSandbox() {
		return game.getSandbox();
	}

	public KeyboardInput getKeyBoard() {
		return game.getKeyboard();
	}
	
	public MouseInput getMouse() {
		return game.getMouse();
	}
	
	public TextManager getText() {
		return getGraphics().textManager;
	}

	public Game getGame() {
		return game;
	}
	public abstract void run();
	
	public void createEngine(Sandbox sandbox) {
		this.game = new Game(sandbox);
		game.startGameComponents();
		game.startLoop();
	}
}