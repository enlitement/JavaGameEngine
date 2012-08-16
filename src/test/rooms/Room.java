package test.rooms;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import test.core.Sandbox;
import test.gameInterfaces.Collidable;
import test.gameInterfaces.Paintable;
import test.objects.GameObject;

public abstract class Room implements Paintable {
	public String name;
	private Sandbox sandbox;
	public ArrayList<GameObject> roomObjects;

	public Room(Sandbox sandbox) {
		this.sandbox = sandbox;
		roomObjects = new ArrayList<GameObject>();
	}
	
	@Override
	public abstract void paint(Graphics2D g);
	public abstract void update();

	
	public void setBackground(String imageName) {

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
		for (GameObject obj : roomObjects)
			if ((obj instanceof Collidable))
				tempList.add(obj);
		return tempList;
	}
	
	public Sandbox getSandbox() {
		return sandbox;
	}
}
