package engine.interfaces;

import engine.core.input.KeyboardInput;

public interface KeyboardAccessible {
	
	public abstract void keyPressed(KeyboardInput keyboard);
	public abstract void keyReleased(KeyboardInput keyboard);
	
}
