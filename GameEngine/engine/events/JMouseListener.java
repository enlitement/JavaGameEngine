package engine.events;

import engine.interfaces.ListenerTag;

public interface JMouseListener extends ListenerTag{
	abstract void mouseMoved(MouseMoveEvent e);
	abstract void mousePressed(MouseMoveEvent e);
	abstract void mouseReleased(MouseMoveEvent e);
}
