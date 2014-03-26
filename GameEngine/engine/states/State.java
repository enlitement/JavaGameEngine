package engine.states;

import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.events.EventDispatcher;
import engine.events.StateEvent;
import engine.events.StateListener;
import engine.objects.GameObject;
import engine.rooms.Room;

public abstract class State {

	public ArrayList<Room> _list;
	public StateManager _manager;
	public boolean focused;

	public State(StateManager manager) {
		_manager = manager;
		_list = new ArrayList<Room>();

	}

	public State() {
		this(null);
	}

	public void paint(Graphics2D g) {
		for (int i = _list.size() - 1; i >= 0; i--) {
			_list.get(i).paint(g);
		}
	}

	public void update() {
		for (int i = _list.size() - 1; i >= 0; i--)
			_list.get(i).update();
	}

	public void addRoom(Room room) {
		_list.add(room);
		room.setParent(this);
	}

	public void addRoom(int index, Room room) {
		_list.add(index, room);
		room.setParent(this);
	}

	/**
	 * A safe way to remove a room by ensuring that all listeners are removed
	 * from the Event Dispatcher.
	 * 
	 * @param room
	 *            The room to remove.
	 */
	public void removeRoom(Room room) {
		for (GameObject obj : room.roomObjects) {
			// Make sure that the ED knows that it should not send
			// events to this GameObject
			System.out.println("Removing object: "+obj.getName());
			obj.removeAllListeners();
			obj = null;
		}
		_list.remove(room);
	}

	public void addNewState(State state) {
		_manager.state_list.add(state);
		//focused = false;
		//updateFocus();
	}

	public void updateFocus() {
		for (Room room : _list) {
			for (int i = 0; i < _list.get(i).roomObjects.size(); i++) {
				if (!focused) {
					GameObject obj = _list.get(i).roomObjects.get(i);
					obj.setID(-1);
				} else {
					//EventDispatcher.
				}
			}
		}
	}
	
	public void removeAllRooms() {
		for(Room r: _list) {
			r.removeAllListeners();
			r.removeAllObjects();
		}
		
	}
	public void popAndAdd(State state) { 
		removeAllRooms();
		state.addNewState(state);
		
		
		
		
		
		
		
		
		
	}
	public void replaceState(final State state) {

		final State thisState = this;

		EventDispatcher.addObserver(_manager.getObserver(),
				new StateEvent(this), new StateListener() {
					@Override
					public void onChange(StateEvent event) {
						_manager.removeState(thisState);
						_manager.state_list.add(state);
					}
				});

		EventDispatcher.eventArrived(new StateEvent(this));
	}

	public void replaceRoom(final Room room1, final Room room2) {
		EventDispatcher.stop();
		EventDispatcher.addObserver(_manager.getObserver(), new StateEvent(
				_manager.getObserver()), new StateListener() {
			@Override
			public void onChange(StateEvent event) {
				removeRoom(room1);
				addRoom(room2);

				EventDispatcher.start();
			}
		});
		EventDispatcher.eventArrived(new StateEvent(_manager.getObserver()));

	}

	public void printRooms() {
		for (Room room : _list)
			System.out.println("Class: " + room.getClass() + " Name: "
					+ room.name);
	}

	public void addStateAndRemoveThis(State state, StateListener performed) {
		_manager.state_list.add(state);
		EventDispatcher.addObserver(_manager.getObserver(),
				new StateEvent(this), performed);
		EventDispatcher.eventArrived(new StateEvent(this));
	}

	public void addState(State state) {
		_manager.state_list.add(state);
	}

	public StateManager getStateManager() {
		return _manager;
	}
}
