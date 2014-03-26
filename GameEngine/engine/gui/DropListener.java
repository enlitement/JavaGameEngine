package engine.gui;

import engine.events.DropEvent;
import engine.interfaces.ListenerTag;

public interface DropListener extends ListenerTag {
	public void onDrop(DropEvent evt);
}
