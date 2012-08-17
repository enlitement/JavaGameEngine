package test.core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import test.objects.GameObject;
import test.rooms.Room;

public abstract class Sandbox extends GameComponent {

	private List<GameObject> objectList;
	private Stack<Room> roomList;
	public int currentRoom;
	private CollisionManager collisionManager;

	
	public Sandbox() {
		super();
		objectList = new ArrayList<GameObject>();
		setRoomList(new Stack<Room>());
		currentRoom = 0;
		setCollisionManager(new CollisionManager(this));
	}

	
	public abstract void paint(Graphics2D g);

	/**
	 * Update method
	 */
	@Override
	public abstract void run();

	
	public void createEngine(Sandbox sandbox) {
		System.out.println("Trying to create engine...");
		super.createEngine(sandbox);
	}
	
	public void addImage(String imageName) {
		getResources().addImage(imageName);
	}

	public Image getImage(String imageName) {
		return getResources().getImage(imageName);
	}

	/**
	 * Currently unsupported.
	 */
	public void addSound() {

	}

	public void addRoom(Room room) {
		getRoomList().add(room);
	}

	public void removeRoom(Room room) {
		getRoomList().remove(room);
	}

	public void addObject(GameObject gameObject) {
		objectList.add(gameObject);
	}

	public void removeObject(GameObject gameObject) {
		objectList.remove(gameObject);
	}

	public Room getCurrentRoom() {
		return getRoomList().get(currentRoom);
	}

	public void createNewRoom(Room room) {
		getRoomList().add(room);
	}

	public void nextRoom() {
		int nextRoom = currentRoom + 1;
		if (nextRoom < getRoomList().size() && getRoomList().get(nextRoom) != null) {
			currentRoom++;
		}
	}

	public void previousRoom() {
		int prevRoom = currentRoom--;
		if (prevRoom >= 0 && getRoomList().get(prevRoom) != null)
			currentRoom--;
	}

	public void goToRoom(int room) {
		if (room >= 0 && room < getRoomList().size() && getRoomList().get(room) != null)
			currentRoom = room;
	}

	public Stack<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(Stack<Room> roomList) {
		this.roomList = roomList;
	}

	public CollisionManager getCollisionManager() {
		return collisionManager;
	}

	public void setCollisionManager(CollisionManager collisionManager) {
		this.collisionManager = collisionManager;
	}
	
	public void runEngine(Sandbox sandbox) {
		
	}
}
