package engine.events;

import java.awt.event.MouseEvent;

public class ClickEvent extends EEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MouseEvent mouse;

	public ClickEvent(Object arg0) {
		super(arg0);
		if (arg0 instanceof java.awt.event.MouseEvent)
			this.mouse = (java.awt.event.MouseEvent) arg0;

	}

	public int getX() {
		return mouse.getX();
	}

	public int getY() {
		return mouse.getY();
	}
}
