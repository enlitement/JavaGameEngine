package engine.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import engine.core.graphics.GraphicsManager;
import engine.core.resources.ResourceManager;
import engine.objects.GameObject;
import engine.rooms.Room;

public abstract class Sandbox {

	private Game game;
	private List<GameObject> objectList;
	private LinkedList<Room> roomList;
	public int currentRoom;
	private CollisionManager collisionManager;

	public Sandbox() {
		super();
		objectList = new ArrayList<GameObject>();
		roomList = new LinkedList<Room>();
		createEngine();
	}

	public void createEngine() {
		this.game = new Game();
		game.startGameComponents();
	}

	public abstract void paint(Graphics2D g);

	public abstract void run();

	public void addImage(String imageName) {
		ResourceManager.get().addImage(imageName);
	}

	public Image getImage(String imageName) {
		return ResourceManager.get().getImage(imageName);
	}

	/**
	 * Currently unsupported.
	 */
	public void addSound() {

	}

	public void addRoom(Room room) {
		getRoomList().addFirst(room);
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
		return getRoomList().peekFirst();
	}
	
	public List<GameObject> getCurrentRoomCollidables() {
		return getCurrentRoom().getCollidables();
	}
	
	public void updateCollisionManager() {
		collisionManager.updateSprites();
	}
	
	public void updateAllRooms() {
		for (int i = getRoomList().size() - 1; i >= 0; i--) 
			roomList.get(i).update();
	}
	
	public void updateCurrentRoom() {
		roomList.peek().update();
	}
	
	public void createNewRoom(Room room) {
		getRoomList().push(room);
	}

/*	public void nextRoom() {
		int nextRoom = currentRoom + 1;
		if (nextRoom < getRoomList().size()
				&& getRoomList().get(nextRoom) != null) {
			currentRoom++;
		}
	}

	public void previousRoom() {
		int prevRoom = currentRoom--;
		if (prevRoom >= 0 && getRoomList().get(prevRoom) != null)
			currentRoom--;
	}

	public void goToRoom(int room) {
		if (room >= 0 && room < getRoomList().size()
				&& getRoomList().get(room) != null)
			currentRoom = room;
	}*/

	public LinkedList<Room> getRoomList() {
		return roomList;
	}

	public CollisionManager getCollisionManager() {
		return collisionManager;
	}

	public void setCollisionManager(CollisionManager collisionManager) {
		this.collisionManager = collisionManager;
	}

	public void runEngine(Sandbox sandbox) {
		game.runEngine(sandbox);
	}

	public int getWidth() {
		return Game.WIDTH;
	}

	public int getHeight() {
		return Game.HEIGHT;
	}

	public void exitGame() {
		Game.exit();
	}

	public void setTitle(String title) {
		game.getFrame().setTitle(title);
	}
	
	public void drawFPS() {
		GraphicsManager.get().g.setColor(Color.green);
		GraphicsManager.get().g.drawString("FPS:"+game.getFPS(), 10, 20);
	}
}