package engine.input;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import engine.events.EventDispatcher;
import engine.events.JActionEvent;
import engine.events.MouseMoveEvent;

/**
 * Mouse input listener. Mouse button 1 = Left click, 2 = Middle click, 3 =
 * Right click.
 * 
 * @author Helson
 * 
 */
public class MouseInput implements MouseListener, MouseMotionListener {
	
	/*
	 * Singleton class
	 */
	private static MouseInput mouseInput;
	
	/*
	 * Number of buttons on a mouse
	 */
	private static final int BUTTON_COUNT = 3;
	/*
	 *  Is this relative or absolute polled position of the mouse cursor
	 */
	private Point mousePos = null;
	/*
	 *  Current position of the mouse cursor
	 */
	private Point currentPos = null;
	/*
	 *  Current state of mouse buttons
	 */
	private boolean[] state = null;
	/*
	 *  Called mouse buttons
	 */
	private MouseState[] poll = null;
	
	/**
	 * Describe different mouse buttons.
	 */
	public static final int LEFT = 1, MIDDLE = 2, RIGHT = 3;
	/**
	 * Describe different mouse states.
	 */
	public static enum MouseState {
		NULL, // Not down
		RELEASED, // Just released
		PRESSED, // Down, but not the first time
		ONCE // Down for the first time
	}
	
	public MouseInput() {
	
	}
	
	/**
	 * Initializes singleton class with specified Java component.
	 * @param component
	 */
	public void initialize(Component component) {
		// Create default mouse positions
		mousePos = new Point(0, 0);
		currentPos = new Point(0, 0);
		// Setup initial button states
		state = new boolean[BUTTON_COUNT];
		poll = new MouseState[BUTTON_COUNT];
		for (int i = 0; i < BUTTON_COUNT; ++i) 
			poll[i] = MouseState.RELEASED;
		
	}

	/*
	 *  For lazy initialization
	 */
	public static synchronized MouseInput get() {
		if (mouseInput == null) {
			mouseInput = new MouseInput();
		}
		return mouseInput;
	}
	
	/**
	 * Updates the state of the mouse.
	 */
	public synchronized void poll() {
		// If relative, return only the delta movements,
		// otherwise return the current position...
		mousePos = new Point(currentPos);

		// Check each mouse button
		for (int i = 0; i < BUTTON_COUNT; ++i) {
			// If the button is down for the first
			// time, it is ONCE, otherwise it is
			// PRESSED.
			// If it was pressed
			if (state[i]) {
				// If it was released
				if (poll[i] == MouseState.RELEASED)
					poll[i] = MouseState.ONCE;
				else
					// If it is still being held
					poll[i] = MouseState.PRESSED;
				// If it is not being pressed
			} else {
				// Button is not down
				if(poll[i] != MouseState.RELEASED && poll[i]!=MouseState.NULL)
					poll[i] = MouseState.RELEASED;
				else
					poll[i] = MouseState.NULL;
			}
		}
	}
	
	/**
	 * Returns the position of the mouse relative to the component.
	 * @return
	 */
	public Point getPosition() {
		return mousePos;
	}
	
	/**
	 * Returns the button state of the mouse.
	 * @param button The specified button, LEFT = 1, MIDDLE = 2, RIGHT = 3
	 * @return A MouseState. Possibilities: NULL, RELEASED, ONCE, PRESSED
	 */
	public MouseState getButtonState(int button) {
		return poll[button - 1];
	}
	/**
	 * Returns the button state of the mouse.
	 * @param button The specified button, LEFT = 1, MIDDLE = 2, RIGHT = 3
	 * @return A MouseState. Possibilities: NULL, RELEASED, ONCE, PRESSED
	 */
	public boolean buttonDownOnce(int button) {
		return poll[button - 1] == MouseState.ONCE;
	}

	/**
	 * Returns the button state of the mouse.
	 * @param button The specified button, LEFT = 1, MIDDLE = 2, RIGHT = 3
	 * @return A MouseState. Possibilities: NULL, RELEASED, ONCE, PRESSED
	 */
	public boolean buttonDown(int button) {
		return poll[button - 1] == MouseState.ONCE
				|| poll[button - 1] == MouseState.PRESSED;
	}
	/**
	 * Shoots an event to the EventDisptacher. Updates the mouse state.
	 */
	public synchronized void mousePressed(MouseEvent e) {
		EventDispatcher.eventArrived(new MouseMoveEvent(e)); 
		EventDispatcher.eventArrived(new JActionEvent(e));
		state[e.getButton() - 1] = true;
	}
	/**
	 * Shoots an event to the EventDisptacher. Updates the mouse state.
	 */
	public synchronized void mouseReleased(MouseEvent e) {
		EventDispatcher.eventArrived(new MouseMoveEvent(e));
		EventDispatcher.eventArrived(new JActionEvent(e)); 
		state[e.getButton() - 1] = false;
	}
	
	/**
	 * Calls the mouseMoved method.
	 */
	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}
	/**
	 * Calls the mouseMoved method.
	 */
	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}
	/**
	 * Calls the mouseMoved method.
	 */
	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}
	/**
	 * Shoots an event to the EventDisptacher. Updates the mouse state.
	 */
	public synchronized void mouseMoved(MouseEvent e) {
		EventDispatcher.eventArrived(new MouseMoveEvent(e));
		currentPos = e.getPoint();
	}
	
	/**
	 * Empty.
	 */
	public void mouseClicked(MouseEvent e) {
		// Not needed
	}
}