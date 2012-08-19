package engine.interfaces;

import engine.asteriods.Bullet;

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
	 * @param vpx Viewport xposition
	 * @param vpy Viewport yposition
	 */
	public void updateBullets(java.util.ArrayList<Bullet> bullets, int vpx, int vpy);

}
