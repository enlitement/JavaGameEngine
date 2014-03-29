package engine.events;

import engine.interfaces.ListenerTag;

public interface JKeyListener extends ListenerTag {

	public void keyPressed(JKeyEvent event);
	public void keyReleased(JKeyEvent event);

}
