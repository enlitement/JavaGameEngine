package unused;

import java.awt.Graphics;
import java.util.Stack;

import test.core.Game;
import test.gameInterfaces.Paintable;
import test.gameStates.PlayState;
import test.gameStates.State;
import test.gameStates.TransitionState;

/*
 * The StateManager's purpose is to provide a way to handle State objects in a
 * FSM. The Stack states can contain states such as MenuState, PlayState, and PauseState.
 * It also controls the frame rate for the game (60 fps).
 */
public class StateManager extends AbstractStateManager implements Runnable, Paintable {

	public StateManager(Game panel) {
		super(panel);
		states = new Stack<State>();
		load = false;
		addTransitionState(new PlayState(this));
	}
	
	/**
	 * This loop is the update game loop.
	 */
	@Override
	public void run() {
		if(!load)
			updateStates();
		if(load) {
			loadingState();
		}
		sleep(1);
	}

	@Override
	public void paint(Graphics g) {
		for(State state: states)
			state.paint(g);
	}
}
