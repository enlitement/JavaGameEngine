package engine.events;

import engine.gui.DragListener;
import engine.interfaces.ListenerTag;
import engine.objects.GameObject;
import engine.gui.AbstractButton;

class Observer {
	private ListenerTag listener;
	private EEvent event;
	private GameObject object;

	public Observer(EEvent event, ListenerTag listener) {
		this.setListener(listener);
		this.setEvent(event);
	}

	public Observer(GameObject object, EEvent event, ListenerTag listener) {
		this.setListener(listener);
		this.setEvent(event);
		this.setObject(object);
	}

	public void eventHandler(EEvent event) {

		if (event instanceof MouseMoveEvent) {
			((JMouseListener) getListener()).mouseMoved((MouseMoveEvent) event);
			((JMouseListener) getListener())
					.mousePressed((MouseMoveEvent) event);
			((JMouseListener) getListener())
					.mouseReleased((MouseMoveEvent) event);
		} else if (event instanceof JActionEvent) {
			if (((AbstractButton) object).contains(
					((JActionEvent) event).getX(),
					((JActionEvent) event).getY()))
				((JActionListener) getListener())
						.actionPerformed((JActionEvent) event);
		} else if (event instanceof JKeyEvent) {
			JKeyEvent evt = (JKeyEvent) event;
			if (evt.getJavaKeyEvent().getID() == java.awt.event.KeyEvent.KEY_PRESSED)
				((JKeyListener) getListener()).keyPressed((JKeyEvent) event);
			else if (evt.getJavaKeyEvent().getID() == java.awt.event.KeyEvent.KEY_RELEASED)
				((JKeyListener) getListener()).keyReleased((JKeyEvent) event);
		} else if (event instanceof DragEvent) {
			((DragListener) getListener()).onDrag((DragEvent) event);
		} else if (event instanceof DropEvent) {
			((DropListenerChecker) getListener()).checkDrop((DropEvent) event);
		} else if (event instanceof StateEvent) {
			System.out.println("State event!!!!");
			((StateListener) getListener())
					.onChange((StateEvent) event);
		} else if (event instanceof FullscreenEvent) {
			((FullscreenListener) getListener()).onToggle();
		}
	}

	public ListenerTag getListener() {
		return listener;
	}

	public void setListener(ListenerTag listener) {
		this.listener = listener;
	}

	public EEvent getEvent() {
		return event;
	}

	public void setEvent(EEvent event) {
		this.event = event;
	}

	public GameObject getObject() {
		return object;
	}

	public void setObject(GameObject object2) {
		this.object = object2;
	}

	@Override
	public String toString() {
		return "Observer Event class:" + event.getClass() + ". Listener class:"
				+ listener.getClass() + ". GameObject name:" + object.getName()
				+ ", class:" + object.getClass();
	}
}
