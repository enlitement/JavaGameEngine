package engine.interfaces;

import engine.objects.GameObject;

public interface Collidable {
	
	/**
	 * On a collision with a specific object.
	 * @param obj The object specified.
	 */
	public abstract void onCollision(GameObject obj2);
}