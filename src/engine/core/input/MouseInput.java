package engine.core.input;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Mouse input listener. Mouse button 1 = Left click, 2 = Middle click, 3 =
 * Right click.
 * 
 * @author Helson
 * 
 */
public class MouseInput implements MouseListener, MouseMotionListener {

	private static MouseInput mouseInput;

	private static final int BUTTON_COUNT = 3;
	// Is this relative or absolute
	// Polled position of the mouse cursor
	private Point mousePos = null;
	// Current position of the mouse cursor
	private Point currentPos = null;
	// Current state of mouse buttons
	private boolean[] state = null;
	// Called mouse buttons
	private MouseState[] poll = null;

	private enum MouseState {
		RELEASED, // Not down
		PRESSED, // Down, but not the first time
		ONCE // Down for the first time
	}

	public MouseInput() {

	}

	public void initialize(Component component) {
		// Create default mouse positions
		mousePos = new Point(0, 0);
		currentPos = new Point(0, 0);
		// Setup initial button states
		state = new boolean[BUTTON_COUNT];
		poll = new MouseState[BUTTON_COUNT];
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			poll[i] = MouseState.RELEASED;
		}
	}

	// For lazy initialization
	public static synchronized MouseInput getInstance() {
		if (mouseInput == null) {
			mouseInput = new MouseInput();
		}
		return mouseInput;
	}

	public synchronized void poll() {
		// If relative, return only the delta movements,
		// otherwise return the current position...
		mousePos = new Point(currentPos);

		// Check each mouse button
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			// If the button is down for the first
			// time, it is ONCE, otherwise it is
			// PRESSED.
			if (state[i]) {
				if (poll[i] == MouseState.RELEASED)
					poll[i] = MouseState.ONCE;
				else
					poll[i] = MouseState.PRESSED;
			} else {
				// Button is not down
				poll[i] = MouseState.RELEASED;
			}
		}
	}

	public Point getPosition() {
		return mousePos;
	}

	public boolean buttonDownOnce(int button) {
		return poll[button - 1] == MouseState.ONCE;
	}

	public boolean buttonDown(int button) {
		return poll[button - 1] == MouseState.ONCE
				|| poll[button - 1] == MouseState.PRESSED;
	}

	public synchronized void mousePressed(MouseEvent e) {
		state[e.getButton() - 1] = true;
	}

	public synchronized void mouseReleased(MouseEvent e) {
		state[e.getButton() - 1] = false;
	}

	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	public synchronized void mouseMoved(MouseEvent e) {
		currentPos = e.getPoint();
	}

	public void mouseClicked(MouseEvent e) {
		// Not needed
	}
}