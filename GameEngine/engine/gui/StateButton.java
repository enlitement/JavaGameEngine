package engine.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import engine.events.EventDispatcher;
import engine.events.MouseMoveEvent;
import engine.events.StateEvent;
import engine.events.StateListener;
import engine.states.State;
import engine.states.StateManager;

public class StateButton extends Button {

	public StateManager manager;
	public State nextState;
	private StateListenerContainer stateListenerContainer;

	public StateButton(String text, final StateManager manager,
			final State nextState) {
		super(text);
		this.manager = manager;
		this.nextState = nextState;
		stateListenerContainer = new StateListenerContainer(this);
		addStateListener(new StateListener() {

			@Override
			public void onChange(StateEvent event) {
				System.out.println("On change");
				EventDispatcher.stop();
				State current = manager.getCurrentState();
				manager.removeState(current);
				manager.addState(nextState);
				EventDispatcher.removeObserver(this);
				EventDispatcher.start();
			}
		});
	}

	@Override
	public void customInput() {
		super.customInput();
	}

	@Override
	public void onPress(MouseMoveEvent event) {
		super.onPress(event);
		EventDispatcher
				.eventArrived(new StateEvent(manager.getStateObserver()));
	}

	@Override
	public void onHover(MouseMoveEvent event) {
		super.onHover(event);
	}

	@Override
	public void onRelease(MouseMoveEvent event) {
		super.onRelease(event);
	}

	@Override
	public void paint(Graphics2D g) {
		super.paint(g);
	}

	private void addStateListener(StateListener listener) {
		stateListenerContainer.addStateListener(listener);
	}

	private void removeStateListener(StateListener listener) {
		stateListenerContainer.removeStateListener(listener);
	}

	private class StateListenerContainer extends AbstractListenerContainer {

		private List<StateListener> stateList;

		public StateListenerContainer(StateButton button) {
			super(button);
			button.getListeners().customList = new CustomListenerRemoval() {

				@Override
				public void removeCustomListeners() {
					if (stateList != null)
						for (int i = 0; i < stateList.size(); i++) {
							removeStateListener(stateList.get(i));
						}
				}

			};
		}

		public void addStateListener(StateListener stateListener) {
			if (stateList == null)
				stateList = new ArrayList<StateListener>();
			stateList.add(stateListener);
			EventDispatcher.addObserver(getObject(),
					new StateEvent(manager.getStateObserver()), stateListener);
		}

		public void removeStateListener(StateListener stateListener) {
			if (stateList != null) {
				EventDispatcher.removeObserver(stateListener);
			} else
				throw new Error(
						"JGE.Events: Attempted to remove a state listener "
								+ "that does not exist in this object.");
		}

		public List<StateListener> getStateList() {
			return stateList;
		}
	}
}
