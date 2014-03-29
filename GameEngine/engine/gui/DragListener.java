package engine.gui;

import engine.events.DragEvent;
import engine.interfaces.ListenerTag;

public interface DragListener extends ListenerTag{
	
	public void onDrag(DragEvent evt);
	
}
