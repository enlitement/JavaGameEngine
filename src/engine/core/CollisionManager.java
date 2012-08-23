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
		collidables = sandbox.getCurrentRoomCollidables();
	}

	public void checkCollisions() {
		// System.out.println("Checking collision");
		for (int i = 0; i < collidables.size(); i++) {
			for (int a = i + 1; a < collidables.size(); a++) {
				if (collidables.get(i).rec.intersects(collidables.get(a).rec)) {
					collision((Collidable) collidables.get(i),
							(Collidable) collidables.get(a));
				}
			}
		}

	}

	public void collision(Collidable obj1, Collidable obj2) {
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
	}
}