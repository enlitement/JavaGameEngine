package test.gameInterfaces;

import test.asteriods.Bullet;

public interface Shootable {
	public int MAX_BULLETS = 5;

	/**
	 * Make a new bullet in the Bullet ArrayList along the trajectory of the
	 * player.
	 */
	public void shoot();

	/***
	 * Checks the ArrayList<Bullet> for each bullet to see if it has been fired.
	 * If it has, it adds one to the counter.
	 * 
	 * @return The number of bullets fired.
	 */
	public int checkBulletsFired();

	/**
	 * Create the ArrayList of Bullets.
	 */
	public void createBullets();

	/**
	 * Make sure that all bullets are within the screen. Move them; If they
	 * aren't within the screen, remove them from the ArrayList.
	 * 
	 * @param bullets
	 */
	public void updateBullets(java.util.ArrayList<Bullet> bullets);

}
