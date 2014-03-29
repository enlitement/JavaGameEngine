package engine.test;

import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.rooms.Room;

public abstract class State {

	public ArrayList<Room> _list;
	public StateManager _manager;

	public State(StateManager manager) {
		_manager = manager;
		_list = new ArrayList<Room>();
	}

	public void paint(Graphics2D g) {
		for (int i = _list.size() - 1; i >= 0; i--)
			_list.get(i).paint(g);
	}

	public void update() {
		for (int i = _list.size() - 1; i >= 0; i--)
			_list.get(i).update();
	}

	public void addRoom(Room room) {
		_list.add(room);
	}

	public void removeRoom(Room room) {
		_list.remove(room);
	}

	public void removeThis() {
		_manager.state_list.remove(this);
	}

	public void addState(State state) {
		_manager.state_list.add(state);
	}

	public StateManager getStateManager() {
		return _manager;
	}
}
