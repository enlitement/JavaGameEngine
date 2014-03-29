package engine.test;

import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.gui.EventDispatcher;

public class StateManager {
	
	public ArrayList<State> state_list;
	private Main main;
	
	public StateManager(Main main) {
		this.main = main;
		state_list = new ArrayList<State>();
		state_list.add(new MenuState(this));
	}
	
	public void paint(Graphics2D g) {
		for(int i = state_list.size() - 1; i >= 0; i --)
			state_list.get(i).paint(g);
	}
	
	public void update() {
		for(int i = state_list.size() - 1; i >= 0; i --)
			state_list.get(i).update();
	}
	
	public void addState(State state) {
		state_list.add(state);
	}
	
	public void removeState(State state) {
		state_list.remove(state);
	}
}
