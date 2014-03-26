package engine.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Stack;

import engine.core.Screen;
import engine.events.JKeyEvent;
import engine.events.JKeyListener;
import engine.objects.GameObject;

public class StateManager {

	public Stack<State> state_list;
	private GameObject observer;
	private GameObject state_observer;

	public StateManager() {
		state_list = new Stack<State>();
		observer = new Observer();
		state_observer = new Observer();
		state_observer.setName("State Manager State Observer.");
		new UniversalKeys();
	}

	public void paint(Graphics2D g) {
		for (int i = 0; i < state_list.size(); i++)
			state_list.get(i).paint(g);
	}

	public void update() {
		for (int i = 0; i < state_list.size(); i++)
			state_list.get(i).update();
	}

	public void addState(State state) {
		state_list.add(state);
		state._manager = this;
	}

	/**
	 * A safe way to remove the state from the state manager. Why is it safe?
	 * Because if you have any listeners in a room in the state, they are not
	 * magically removed.
	 * 
	 * @param state
	 */
	public void removeState(State state) {
		for (int i = 0; i < state._list.size(); i++) {
			// Remove the room
			System.out.println("Removing " + state._list.get(i).name);
			state.removeRoom(state._list.get(i));
		}
		remove(state);
	}

	private void remove(State state) {
		state_list.remove(state);
	}

	public GameObject getObserver() {
		return observer;
	}
	
	public GameObject getStateObserver() {
		return state_observer;
	}

	public State getCurrentState() {
		return state_list.peek();
	}

	class UniversalKeys extends GameObject implements JKeyListener {

		public UniversalKeys() {
			addKeyListener(this);
			System.out.println("Adding universal keys");
		}

		@Override
		public void paint(Graphics2D g) {

		}

		@Override
		public void keyPressed(JKeyEvent event) {
			if (event.getJavaKeyEvent().getKeyCode() == KeyEvent.VK_F11)
				Screen.toggleFullscreen();
		}

		@Override
		public void keyReleased(JKeyEvent event) {

		}

	}

	class Observer extends GameObject {

		public Observer() {
			setName("State Manager Observer.");
		}

		@Override
		public void paint(Graphics2D g) {

		}

	}

}