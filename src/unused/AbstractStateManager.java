package unused;

import java.awt.Graphics;
import java.util.Stack;

import test.core.Game;
import test.core.GameComponent;
import test.gameInterfaces.Paintable;
import test.gameStates.State;
import test.gameStates.TransitionState;

/*
 * The StateManager's purpose is to provide a way to handle State objects in a
 * FSM. The Stack states can contain states such as MenuState, PlayState, and PauseState.
 */
public abstract class AbstractStateManager extends GameComponent implements Runnable, Paintable {

	// The stack of states such as Menu, In-game, etc.
	public Stack<State> states;
	public Game canvas;
	
	// For loading a new state
	public TransitionState transitionState;
	public boolean load;
		
	public AbstractStateManager(Game canvas) {
		super(canvas);
		states = new Stack<State>();
		load = false;
	}
	
	/**
	 * Adds a transition state to the state manager.
	 * @param state
	 */
	public void addTransitionState(State state) {
		transitionState = new TransitionState((StateManager)this, state);
		states.add(transitionState);
	}

	public TransitionState getTransitionState() {
		return transitionState;
	}
	
	public void loadingState() {
		getTransitionState().update();
		if(getTransitionState().checkLoad()) {
			transitionState = null;
			System.out.println("Done loading");
		}
			
	}
	
	public void updateStates() {
		for (State state : states)
			state.update();
	}
	
	@Override
	public abstract void run();
	@Override
	public abstract void paint(Graphics g);
}
