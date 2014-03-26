package engine.test;

import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.gui.Observer;
import engine.gui.StateEvent;
import engine.gui.StateListener;

public class StateObserver extends Observer {

	public static ActionEvent event;

	public StateObserver(ActionEvent event, StateListener listener) {
		super(event, listener);
		StateObserver.event = event;
		this.listener = listener;
	}

	@Override
	public void eventHandler(ActionEvent event) {
		super.eventHandler((StateEvent) event);
	}

	public static StateEvent getStateEvent() {
		return (StateEvent) event;
	}
}
