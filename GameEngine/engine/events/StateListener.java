package engine.events;

import engine.interfaces.ListenerTag;

public interface StateListener extends ListenerTag{
	
	public void onChange(StateEvent event);
	
}
