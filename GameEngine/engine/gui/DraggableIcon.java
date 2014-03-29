package engine.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import engine.events.DragEvent;
import engine.events.DropEvent;
import engine.events.DropListenerChecker;
import engine.events.EventDispatcher;
import engine.events.MouseMoveEvent;
import engine.utilities.Utilities;

public class DraggableIcon extends AbstractButton implements
		DropListenerChecker {

	private DnDContainer dndContainer;
	public Font font;
	public int DRAG_STATE;
	public static final int STILL = 0, DRAG = 1, DROP = 2;
	public boolean drag, drop;
	private Color body_color, regular_color, hover_color, on_press;
	@SuppressWarnings("unused")
	private int stroke;
	private static final int DEF_ICON_WID = 32, DEF_ICON_H = 32;
	private int EXTRA_WIDTH = 50, EXTRA_HEIGHT = 50;

	/**
	 * Create a black and white Button.
	 * 
	 * @param text
	 *            The text of the button.
	 */
	public DraggableIcon(String text) {
		this(text, Color.white, Color.LIGHT_GRAY, Color.GRAY);
	}

	/**
	 * Create a button with the specified colors.
	 * 
	 * @param text
	 *            The text of the button.
	 */
	public DraggableIcon(String text, Color body_color, Color hover_color,
			Color on_press) {
		super();
		dndContainer = new DnDContainer(this);
		setWidth(DEF_ICON_WID);
		setHeight(DEF_ICON_H);
		setName(text);
		this.body_color = regular_color = body_color;
		this.hover_color = hover_color;
		this.on_press = on_press;
		stroke = 2;
		font = new Font("Dialog", Font.PLAIN, 12);
		drag = drop = false;
		addMouseListener(this);
	}

	@Override
	public void mouseMoved(MouseMoveEvent e) {
		if (Utilities.withinBounds(getX() - EXTRA_WIDTH / 2, getY()
				- EXTRA_WIDTH / 2, getWidth() + EXTRA_WIDTH / 2, getHeight()
				+ EXTRA_HEIGHT / 2, e.getXpos(), e.getYpos())) {
			setHover(true);
			onHover(e);
		} else {
			setHover(false);
			onRelease(e);
		}
	}

	@Override
	public void customInput() {

	}

	@Override
	public void onHover(MouseMoveEvent e) {
		body_color = hover_color;
	}

	@Override
	public void onPress(MouseMoveEvent e) {
		body_color = on_press;
		DRAG_STATE = DRAG;
		setX(e.getXpos() - getWidth() / 2);
		setY(e.getYpos() - getHeight() / 2);
		EventDispatcher.eventArrived(new DragEvent(this));
	}

	@Override
	public void onRelease(MouseMoveEvent e) {
		body_color = regular_color;
		if (DRAG_STATE != DROP) {
			DRAG_STATE = DROP;
			EventDispatcher.eventArrived(new DropEvent(this));
		}
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(body_color);
		g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
	}

	public void addDragListener(DragListener dragListener) {
		dndContainer.addDragListener(dragListener);
	}

	public void removeDragListener(DragListener dragListener) {
		dndContainer.removeDragListener(dragListener);
	}

	public void addDropListener(DropListener dropListener) {
		dndContainer.addDropListener(dropListener);
	}

	public void removeDropListener(DropListener dropListener) {
		dndContainer.removeDropListener(dropListener);
	}

	@Override
	public void checkDrop(DropEvent drop) {
		if (isHover())
			for (DropListener listener : dndContainer.getDropList())
				listener.onDrop(drop);
	}
}
