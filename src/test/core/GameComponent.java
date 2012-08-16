package test.core;

import test.core.graphics.GraphicsManager;
import test.core.input.KeyboardInput;
import test.core.input.MouseInput;
import test.core.resources.ResourceManager;

public abstract class GameComponent extends SuperGameComponent{

	public GameComponent() {
		super();
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

	public abstract void run();
}