package test.gameStates;

import java.awt.Graphics;

import test.core.StateManager;

public abstract class AbstractTransitionState extends State {
	public State transState;
	public LoadingBar loadingBar;
	
	public AbstractTransitionState(StateManager stateManager, State state) {
		super(stateManager);
		this.stateManager = stateManager;
		transState = state;
		loadingBar = new LoadingBar(this,100,30,java.awt.Color.BLUE);
	}
	
	public double getPercent() {
		return stateManager.getResources().getPercentComplete();
	}
	
	public boolean isDone() {
		if(getPercent()==1) {
			System.out.println("Done loading");
			return true;
		}
		return false;
	}
	
	@Override
	public void addNewState(State state, boolean longLoad) {
		super.addNewState(transState, longLoad);
	}

	public boolean checkLoad() {
		if(stateManager.getResources().loadQueue.isEmpty()) {
			stateManager.states.pop();
			addNewState(transState,false);
			return true;
		}
		else {
			stateManager.getResources().run();
			return false;
		}
	}
	
	public void paint(Graphics g) {
		
	}
	public abstract void update();
	
}
