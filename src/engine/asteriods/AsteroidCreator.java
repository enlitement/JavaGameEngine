package engine.asteriods;

import java.awt.Graphics2D;

import engine.core.Game;
import engine.core.Sandbox;
import engine.objects.GameObject;

public class AsteroidCreator extends GameObject {

	public Asteroid[] asteroids;
	public Play play;
	public int totalAsteroids = 5;
	public int currAsteroids = totalAsteroids;
	public int minSpeed;
	public final int screenCheck = 100;
	public final int gameWidth = Game.WIDTH, gameHeight = Game.HEIGHT;

	public AsteroidCreator(Sandbox sandbox, Play play, int vpx, int vpy) {
		super(sandbox);
		name = "AsteriodCreator";
		asteroids = new Asteroid[totalAsteroids];
		this.play = play;
		minSpeed = 1;
		for (int i = 0; i < asteroids.length; i++) {
			asteroids[i] = new Asteroid(sandbox,
					(int) (Math.random() * gameWidth),
					(int) (Math.random() * gameHeight), (int) newSpeed(
							minSpeed, minSpeed + 3), newSpeed(minSpeed,
							minSpeed + 3), 40, 40);
			play.addObject(asteroids[i]);
		}
	}

	public void paint(Graphics2D g, int vpx, int vpy) {
		for (int i = 0; i < asteroids.length; i++)
			if (asteroids[i] != null)
				asteroids[i].paint(g);

	}

	public Asteroid[] createAstArray(int minSpeed, int maxSpeed) {
		Asteroid[] asteroidArray = new Asteroid[totalAsteroids];
		for (int i = 0; i < asteroidArray.length; i++) {
			asteroidArray[i] = new Asteroid(getSandbox(),
					(int) (Math.random() * gameWidth),
					(int) (Math.random() * gameHeight), (int) newSpeed(
							minSpeed, maxSpeed), newSpeed(minSpeed, maxSpeed),
					40, 40);
			play.addObject(asteroidArray[i]);
		}
		return asteroidArray;

	}

	public synchronized void update(int vpx, int vpy) {
		if (currAsteroids == 0) {
			System.out.println("currAsteroids = 0");
			totalAsteroids += 3;
			currAsteroids = totalAsteroids;
			minSpeed++;
			asteroids = createAstArray(minSpeed, minSpeed + 4);
		}
		for (int i = 0; i < asteroids.length; i++)
			if (asteroids[i] != null) {
				if (outOfScreen(asteroids[i], vpx, vpy)) {
					if (asteroids[i].xpos - vpx > gameWidth
							+ asteroids[i].width)
						randomPosAndSpeed(asteroids[i], vpx, vpy);

					if (asteroids[i].xpos - vpx < -asteroids[i].width)
						randomPosAndSpeed(asteroids[i], vpx, vpy);

					if (asteroids[i].ypos - vpy < -asteroids[i].height)
						randomPosAndSpeed(asteroids[i], vpx, vpy);

					if (asteroids[i].ypos - vpy > gameHeight
							+ asteroids[i].height)
						randomPosAndSpeed(asteroids[i], vpx, vpy);

					asteroids[i].dx = newSpeed(2, 4);
					asteroids[i].dy = newSpeed(2, 4);
				}
				if (asteroids[i].health > 0 && asteroids[i].isAlive)
					asteroids[i].update(vpx, vpy);
				if (asteroids[i].health <= 0) {
					play.removeObject(asteroids[i]);
					play.score += play.scoreIncrement;
					currAsteroids--;
					asteroids[i] = null;
				}
			}
	}

	public void randomPosAndSpeed(Asteroid ast, int vpx, int vpy) {
		double a = Math.random();
		if (a < .25)
			ast.xpos = vpx - 40;
		if (a >= .25 && a < 5)
			ast.xpos = vpx + gameWidth + 40;
		if (a >= .5 && a < .75)
			ast.ypos = vpy + gameHeight + 40;
		if (a > .75 && a <= .99)
			ast.ypos = vpy - 40;
		ast.dx = newSpeed(minSpeed, minSpeed + 3);
		ast.dy = newSpeed(minSpeed, minSpeed + 3);
	}

	/**
	 * Checks whether the asteroid is out of the screen.
	 * 
	 * @param ast
	 *            The asteroid
	 * @param vpx
	 *            The viewport xposition
	 * @param vpy
	 *            Viewport yposition
	 * @return boolean
	 */
	public boolean outOfScreen(Asteroid ast, int vpx, int vpy) {
		if (ast.xpos - vpx > gameWidth + ast.width + screenCheck
				|| ast.xpos - vpx < -ast.width - screenCheck
				|| ast.ypos - vpy < -ast.height - screenCheck
				|| ast.ypos - vpy > gameHeight + ast.height + screenCheck)
			return true;
		return false;
	}

	/**
	 * Creates a new random speed
	 * 
	 * @param min
	 *            The min speed
	 * @param max
	 *            The max speed
	 * @return an Integer
	 */
	public int newSpeed(int min, int max) {
		double a = Math.random();
		if (a > .5)
			a = -1;
		else
			a = 1;
		return (int) (a * Math.random() * (max - min) + min);
	}
}
