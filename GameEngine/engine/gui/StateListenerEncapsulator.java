package engine.gui;

import engine.events.StateListener;
import engine.interfaces.ListenerTag;

public interface StateListenerEncapsulator extends ListenerTag {

	public void onChange(StateListener listener);
}
