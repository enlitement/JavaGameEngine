package engine.events;

import engine.utilities.Utilities;

public class JActionEvent extends ClickEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JActionEvent(Object source) {
		super(source);
		
	}

	public boolean contains(int x, int y, int width, int height) {
		return Utilities.withinBounds(x, y, width, height, getX(), getY());
	}
}
