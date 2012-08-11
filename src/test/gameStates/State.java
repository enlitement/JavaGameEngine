package test.gameStates;

import test.core.AbstractStateManager;
import test.core.KeyboardInput;
import test.gameInterfaces.Paintable;

public abstract class State implements Paintable {

	public AbstractStateManager stateManager;
	public String name;

	public State(AbstractStateManager stateManager) {
		this.stateManager = stateManager;
	}

	public KeyboardInput getKeyboard() {
		return stateManager.getKeyBoard();
	}
	public void removeMe() {
		stateManager.states.remove(this);
	}
	public void addNewState(State state, boolean longLoad) {
		if (!longLoad)
			stateManager.states.push(state);
		else
			stateManager.addTransitionState(state);
	}
	
	public abstract void update();
	public abstract void paint(java.awt.Graphics g);
}
