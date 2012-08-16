package test.gameInterfaces;

public interface Movable {

	/**
	 * Move however you want to move.
	 */
	public abstract void move();

	/**
	 * Move in a free direction, specified by the radians of the direction and
	 * the speed of the movement.
	 * 
	 * @param direction
	 *            In radians.
	 * @param speed
	 *            A double.
	 */
	public abstract void moveFree(double direction, double speed);

	/**
	 * Move towards point (x,y);
	 * 
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 */
	public abstract void moveToward(int x, int y);

	/**
	 * Set dx, or horizontal speed.
	 * 
	 * @param speed
	 */
	public abstract void setHorizontalSpeed(double speed);

	/**
	 * Set dy, or vertical speed.
	 * 
	 * @param speed
	 */
	public abstract void setVerticalSpeed(double speed);

	/**
	 * Sets the friction for speed reduction.
	 * 
	 * @param friction
	 */
	public abstract void setFriction(double friction);
}
