package engine.utilities;

public class Utilities {
	private static Utilities utilities = null;

	private Utilities() {

	}

	public static synchronized Utilities get() {
		if (utilities == null) {
			utilities = new Utilities();
		}
		return utilities;
	}

	/**
	 * Move in a free direction, specified by the radians of the direction and
	 * the speed of the movement.
	 * 
	 * @param direction
	 *            In radians.
	 * @param speed
	 *            A double.
	 */
	public void moveFree(double direction, double speed) {
	}

	/**
	 * Move towards point (x,y);
	 * 
	 * @param x
	 *            X position
	 * @param y
	 *            Y position
	 */
	public void moveToward(int x, int y) {
	}

	/**
	 * Set dx, or horizontal speed.
	 * 
	 * @param speed
	 */
	public void setHorizontalSpeed(double speed) {
	}

	/**
	 * Set dy, or vertical speed.
	 * 
	 * @param speed
	 */
	public void setVerticalSpeed(double speed) {
	}

	/**
	 * Sets the friction for speed reduction.
	 * 
	 * @param friction
	 */
	public void setFriction(double friction) {
	}

}
