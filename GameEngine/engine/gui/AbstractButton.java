package engine.gui;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import engine.events.JMouseListener;
import engine.events.MouseMoveEvent;
import engine.input.MouseInput;
import engine.objects.GameObject;
import engine.utilities.Utilities;

public abstract class AbstractButton extends GameObject implements
		JMouseListener {

	private boolean pressed, clicked, hover;
	private final static int DEF_WIDTH = 100, DEF_HEIGHT = 40;
	
	public AbstractButton() {
		this(0, 0, DEF_WIDTH, DEF_HEIGHT);
	}
	
	public AbstractButton(String text) {
		this();
		setName(text);
	}
	
	public AbstractButton(int xpos, int ypos, int width, int height) {
		super();
		setRec(new Rectangle((int) xpos, (int) ypos, width, height));
		addMouseListener(this);
	}

	abstract public void customInput();

	abstract public void onPress(MouseMoveEvent event);

	abstract public void onHover(MouseMoveEvent event);

	abstract public void onRelease(MouseMoveEvent event);

	abstract public void paint(Graphics2D g);

	@Override
	public void mouseMoved(MouseMoveEvent e) {
		if (hoverCondition(e)) {
			setHover(true);
			onHover(e);
		} else {
			setHover(false);
			onRelease(e);
			pressed = false;
		}
	}

	public boolean hoverCondition(MouseMoveEvent e) {
		if (Utilities.withinBounds(getX(), getY(), getWidth(), getHeight(),
				e.getXpos(), e.getYpos()))
			return true;
		return false;
	}

	@Override
	public void mousePressed(MouseMoveEvent e) {
		if ((isHover() && e.getButton() == MouseEvent.BUTTON1 && e.getID() == MouseEvent.MOUSE_PRESSED)
				|| (isHover() && e.getID() == MouseEvent.MOUSE_DRAGGED)) {
			pressed = true;
			System.out.println("Pressed = "+pressed);
			onPress(e);
		}
		if (clicked)
			clicked = false;
	}
	
	@Override
	public void mouseReleased(MouseMoveEvent e) {
		if (e.getID() == MouseEvent.MOUSE_RELEASED)
			if (pressed && isHover()) {
				clicked = true;
				// This may present problems in the future:
				onRelease(e);
				pressed = false;
				System.out.println("Pressed = " + pressed);
				onHover(e);
			}
	}

	/**
	 * Called from within a layoutManager to make sure the rectangle is set up
	 * for click detection.
	 * 
	 * @param xpos
	 * @param ypos
	 */
	public void setPosition(int xpos, int ypos) {
		this.setX(xpos);
		this.setY(ypos);
		getRec().setLocation((int) getX(), (int) getY());
	}

	/**
	 * Checks to see if the mouse click was in the bounds of the button.
	 * 
	 * @param e
	 *            MouseInput
	 * @return
	 */
	public boolean mouseWithinBounds(MouseInput e) {
		if (getRec().contains(e.getPosition()))
			return true;
		return false;
	}

	public boolean isHover() {
		return hover;
	}

	public boolean isPressed() {
		return pressed;
	}

	public boolean isClicked() {
		return clicked;
	}

	protected void setHover(boolean hover) {
		this.hover = hover;
	}
}