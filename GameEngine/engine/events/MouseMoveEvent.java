package engine.events;

import java.awt.event.MouseEvent;

public class MouseMoveEvent extends EEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private java.awt.event.MouseEvent java_event;
	private int xpos, ypos, button, ID;

	public MouseMoveEvent(Object arg0) {
		super(arg0);
		if (arg0 instanceof java.awt.event.MouseEvent) {
			java_event = (MouseEvent) arg0;
			xpos = (java_event.getX());
			ypos = (java_event.getY());
			ID = (java_event.getID());
			button = (java_event.getButton());
		}
	}

	public java.awt.event.MouseEvent getJava_event() {
		return java_event;
	}

	public int getXpos() {
		return xpos;
	}

	public int getYpos() {
		return ypos;
	}

	public int getButton() {
		return button;
	}

	public int getID() {
		return ID;
	}
}
