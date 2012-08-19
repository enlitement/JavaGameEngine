package engine.core;

import java.util.ArrayList;
import java.util.List;

import engine.interfaces.Collidable;
import engine.objects.GameObject;

public class CollisionManager {

	public Sandbox sandbox;
	public List<GameObject> collidables;
	public List<GameObject> removeQueue;

	public CollisionManager(Sandbox sandbox) {
		this.sandbox = sandbox;
		removeQueue = new ArrayList<GameObject>();
	}

	public void setUpCollidables() {
		System.out.println("Line 22, CollisionManager");
		collidables = sandbox.getCurrentRoomCollidables();
	}

	public void checkCollisions() {
		// System.out.println("Checking collision");
		for (GameObject obj1 : collidables)
			for (GameObject obj2 : collidables)
				if (obj1 != obj2 && obj1.rec.intersects(obj2.rec)) {
					if(obj1.name!=obj2.name)
						System.out.println("Collision:" + obj1.name + " and "
								+ obj2.name);
					collision((Collidable) obj1, (Collidable) obj2);

				}

	}

	public void collision(Collidable obj1, Collidable obj2) {
		// System.out.println("Collision");
		obj1.onCollision((GameObject) obj2);
		obj2.onCollision((GameObject) obj1);
	}

	public void addToRemoveQueue(GameObject obj) {
		removeQueue.add(obj);
	}

	/**
	 * Get the collidables from the room to make sure that all objects remain
	 * the same.
	 */
	public void updateCollidables() {
		collidables = sandbox.getCurrentRoomCollidables();
	}

	/**
	 * Removes all items in the removeQueue and updates the collidables list,
	 * along with the sandbox list and the removeQueue.
	 */
	public void updateSprites() {
		updateCollidables();
		checkCollisions();
		synchronized (collidables) {
			if (!removeQueue.isEmpty())
				for (GameObject obj : removeQueue) {
					collidables.remove(obj);
					sandbox.removeObject(obj);
					removeQueue.remove(obj);
				}

		}
		//updateCollidables();
	}
}