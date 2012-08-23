package engine.rooms;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import engine.core.Sandbox;
import engine.interfaces.Collidable;
import engine.interfaces.Paintable;
import engine.objects.GameObject;

public abstract class Room implements Paintable {
	public String name;
	private Sandbox sandbox;
	public ArrayList<GameObject> roomObjects;
	public Image background;

	public Room(Sandbox sandbox) {
		this.sandbox = sandbox;
		roomObjects = new ArrayList<GameObject>();
	}

	@Override
	public abstract void paint(Graphics2D g);

	public abstract void update();

	public void drawFPS() {
		getSandbox().drawFPS();
	}

	public void setBackground(String imageName) {
		background = engine.core.resources.ResourceManager.get().images
				.getImage(imageName);
	}

	public void addAllObjects(Collection<? extends GameObject> obj) {
		roomObjects.addAll(obj);
	}

	public void addObject(GameObject obj) {
		roomObjects.add(obj);
	}

	public void removeObject(GameObject obj) {
		roomObjects.remove(obj);
	}

	public void removeMe() {
		roomObjects = null;
		sandbox.removeRoom(this);
	}

	public List<GameObject> getObjectList() {
		return roomObjects;
	}

	public List<GameObject> getCollidables() {
		List<GameObject> tempList = new ArrayList<GameObject>(15);
		for (GameObject obj : roomObjects) {
			if (obj instanceof Collidable)
				tempList.add(obj);

		}
		return tempList;
	}

	public Sandbox getSandbox() {
		return sandbox;
	}
	
	public void moveRoomBack() {
		getSandbox().getRoomList().addLast(this);
	}

	public void addRoom(Room room) {
		getSandbox().addRoom(room);
	}
}
