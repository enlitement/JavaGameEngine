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
		collidables = sandbox.getCurrentRoom().getCollidables();
	}
	
	public void checkCollisions() {
		for (GameObject obj1 : collidables)
			for (GameObject obj2 : collidables)
				if (obj1 != obj2 && obj1.rec.intersects(obj2.rec))
					collision((Collidable)obj1,(Collidable)obj2);

	}

	public void collision(Collidable obj1, Collidable obj2) {
		obj1.onCollision((GameObject) obj2);
		obj2.onCollision((GameObject) obj1);
	}

	public void addToRemoveQueue(GameObject obj) {
		removeQueue.add(obj);
	}

	/**
	 * Get the collidables from the room to make sure that all objects remain the same.
	 */
	public void updateCollidables() {
		collidables = sandbox.getCurrentRoom().getCollidables();
	}

	/**
	 * Removes all items in the removeQueue and updates the collidables list,
	 * along with the sanbox list and the removeQueue.
	 */
	public void updateSprites() {
		checkCollisions();
		synchronized (collidables) {
			if (!removeQueue.isEmpty())
				for (GameObject obj : removeQueue) {
					collidables.remove(obj);
					sandbox.removeObject(obj);
					removeQueue.remove(obj);
				}
			updateCollidables();
		}
	}
}