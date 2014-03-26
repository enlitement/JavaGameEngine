package engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import engine.events.EventDispatcher;

/**
 * Keyboard input class that uses a Java KeyListener to receive events.
 * @author Helson
 *
 */
public class KeyboardInput implements KeyListener {

	private static KeyboardInput keyboardInput;
	
	/*
	 * Key count
	 */
	private static final int KEY_COUNT = 256;
	
	/*
	 * Possible Key states
	 */
	private enum KeyState {
		RELEASED, // Not down
		PRESSED, // Down, but not the first time
		ONCE // Down for the first time
	}

	/*
	 *  Current state of the keyboard
	 */
	private boolean[] currentKeys = null;

	/*
	 *  Polled keyboard state
	 */
	private KeyState[] keys = null;
	
	/*
	 * Create the keyboard input.
	 */
	public KeyboardInput() {
		currentKeys = new boolean[KEY_COUNT];
		keys = new KeyState[KEY_COUNT];
		for (int i = 0; i < KEY_COUNT; ++i) {
			keys[i] = KeyState.RELEASED;
		}
	}

	/*
	 *  For singleton initialization
	 */
	public static synchronized KeyboardInput get() {
		if (keyboardInput == null) {
			keyboardInput = new KeyboardInput();
		}
		return keyboardInput;
	}
	
//	/**
//	 * See which keys are being pressed and which aren't. 
//	 */
//	public synchronized void poll() {
//		for (int i = 0; i < KEY_COUNT; ++i) {
//			// Set the key state
//			if (currentKeys[i]) {
//				// If the key is down now, but was not
//				// down last frame, set it to ONCE,
//				// otherwise, set it to PRESSED
//				if (keys[i] == KeyState.RELEASED)
//					keys[i] = KeyState.ONCE;
//				else
//					keys[i] = KeyState.PRESSED;
//			} else {
//				keys[i] = KeyState.RELEASED;
//			}
//		}
//	}
//	
//	/**
//	 * Determines the keyDown status of a key.
//	 * @param keyCode The Java keycode.
//	 * @return
//	 */
//	public boolean keyDown(int keyCode) {
//		return keys[keyCode] == KeyState.ONCE
//				|| keys[keyCode] == KeyState.PRESSED;
//	}
//	/**
//	 * Determines the keyDownOnce status of a key.
//	 * @param keyCode The Java keycode.
//	 * @return
//	 */
//	public boolean keyDownOnce(int keyCode) {
//		return keys[keyCode] == KeyState.ONCE;
//	}
	
	@Override
	/**
	 * Shoots a KeyEvent to the EventDispatcher. Updates the 
	 * currentKeys pressed array.
	 */
	public synchronized void keyPressed(KeyEvent e) {
		EventDispatcher.eventArrived(new engine.events.JKeyEvent(e));
		int keyCode = e.getKeyCode();
		if (keyCode >= 0 && keyCode < KEY_COUNT) {
			currentKeys[keyCode] = true;
		}
	}

	@Override
	/**
	 * Shoots a KeyEvent to the EventDispatcher. Updates the 
	 * currentKeys pressed array.
	 */
	public synchronized void keyReleased(KeyEvent e) {
		EventDispatcher.eventArrived(new engine.events.JKeyEvent(e));
		int keyCode = e.getKeyCode();
		if (keyCode >= 0 && keyCode < KEY_COUNT) {
			currentKeys[keyCode] = false;
		}
	}

	@Override
	/*
	 * Empty
	 */
	public void keyTyped(KeyEvent e) {
		
	}
}