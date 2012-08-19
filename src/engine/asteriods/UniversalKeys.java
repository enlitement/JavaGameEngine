package engine.asteriods;

import java.awt.event.KeyEvent;

import engine.core.Sandbox;
import engine.core.input.KeyboardInput;

public class UniversalKeys {

	private static UniversalKeys universalKeys = null;

	public Sandbox sandbox;

	private UniversalKeys(Sandbox sandbox) {
		this.sandbox = sandbox;
	}

	public synchronized static UniversalKeys get(Sandbox sandbox) {
		if (universalKeys == null)
			universalKeys = new UniversalKeys(sandbox);
		return universalKeys;
	}

	public void update() {
		if (KeyboardInput.get().keyDownOnce(KeyEvent.VK_ESCAPE)) {
			sandbox.exitGame();
		}
	}
}