package engine.asteriods;

import java.awt.event.KeyEvent;

import engine.core.Game;
import engine.core.input.KeyboardInput;

public class UniversalKeys {

	private static UniversalKeys universalKeys = null;

	private UniversalKeys() {

	}

	public synchronized static UniversalKeys get() {
		if (universalKeys == null)
			universalKeys = new UniversalKeys();
		return universalKeys;
	}

	public void update() {
		if (KeyboardInput.get().keyDownOnce(KeyEvent.VK_ESCAPE)) {
			Game.exit();
		}
	}
}