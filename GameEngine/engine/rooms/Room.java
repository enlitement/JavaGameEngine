package engine.rooms;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import engine.core.JGE;
import engine.events.EventDispatcher;
import engine.events.JActionListener;
import engine.events.JKeyListener;
import engine.events.JMouseListener;
import engine.events.StateEvent;
import engine.events.StateListener;
import engine.gui.ListenerContainer;
import engine.objects.GameObject;
import engine.states.State;
import engine.states.StateManager;

public abstract class Room {
	public String name;
	public ArrayList<GameObject> roomObjects;
	public Image background;
	private State parent;
	private ListenerContainer listeners;
	private RoomListener room_listener;

	/**
	 * Creates a room with capabilities to add event listeners.
	 */
	public Room() {
		roomObjects = new ArrayList<GameObject>();
		room_listener = new RoomListener();
		listeners = new ListenerContainer(room_listener);
	}

	private class RoomListener extends GameObject {

		@Override
		public void paint(Graphics2D g) {

		}
	}

	public State getParent() {
		return parent;
	}

	public void setParent(State parent) {
		this.parent = parent;
	}

	public StateManager getStateManager() {
		return parent._manager;
	}
	
	public abstract void paint(Graphics2D g);

	public abstract void update();

	public void setBackground(String imageName) throws IOException {
		background = JGE.LOAD_IMAGE(imageName);
	}

	public void addObject(Collection<? extends GameObject> obj) {
		roomObjects.addAll(obj);
	}

	public void addObject(GameObject obj) {
		roomObjects.add(obj);
	}

	public void removeObject(GameObject obj) {
		obj.removeAllListeners();
		roomObjects.remove(obj);
	}

	public void replaceThisRoom(Room room) {
		final Room thisRoom = this;
		EventDispatcher.addObserver(parent.getStateManager().getObserver(),
				new StateEvent(this), new StateListener() {
					@Override
					public void onChange(StateEvent event) {
						parent.removeRoom(thisRoom);
					}
				});

		EventDispatcher.eventArrived(new StateEvent(parent
				.getStateManager().getObserver()));
	}

	public void removeAllObjects() {
		for (int i = 0; i < roomObjects.size(); i++)
			removeObject(roomObjects.get(i));
	}

	public List<GameObject> getObjectList() {
		return roomObjects;
	}

	protected ListenerContainer getListeners() {
		return listeners;
	}

	void setListeners(ListenerContainer listeners) {
		this.listeners = listeners;
	}

	public void addActionListener(JActionListener actionListener) {
		getListeners().addActionListener(actionListener);
	}

	public void removeActionListener(JActionListener actionListener) {
		getListeners().removeActionListener(actionListener);
	}

	public void addMouseListener(JMouseListener mouseListener) {
		getListeners().addMouseListener(mouseListener);
	}

	public void removeMouseListener(JMouseListener mouseListener) {
		getListeners().removeMouseListener(mouseListener);
	}

	public void addKeyListener(JKeyListener keyListener) {
		getListeners().addKeyListener(keyListener);
	}

	public void removeKeyListener(JKeyListener keyListener) {
		getListeners().removeKeyListener(keyListener);
	}
	
	/**
	 * Removes the listeners of the actual room, not any objects
	 * in the room.
	 */
	public void removeAllListeners() {
		getListeners().removeAllListeners();
	}
}
