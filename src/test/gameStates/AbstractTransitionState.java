package test.gameStates;

import java.awt.Graphics;

import test.core.AbstractStateManager;
import test.core.StateManager;

public abstract class AbstractTransitionState extends State {
	public State transState;
	public AbstractLoadingBar loadingBar;
	
	public AbstractTransitionState(AbstractStateManager stateManager, State state) {
		super(stateManager);
		this.stateManager = stateManager;
		transState = state;
		loadingBar = new AbstractLoadingBar(this,100,30,java.awt.Color.BLUE);
	}
	
	public double getPercent() {
		return stateManager.getResources().getPercentComplete();
	}
	
	public boolean isDone() {
		if(getPercent()==1)
			return true;
		return false;
	}
	
	@Override
	public void addNewState(State state, boolean longLoad) {
		super.addNewState(transState, false);
	}

	public boolean loadComplete() {
		if(isDone()) {
			addNewState(transState,false);
			return true;
		}
		return false;
	}
	
	public void paint(Graphics g) {
		
	}
	public abstract void update();
	
}
