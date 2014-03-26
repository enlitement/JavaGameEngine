package engine.asteriods;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import engine.core.Game;
import engine.core.Sandbox;
import engine.core.input.KeyboardInput;
import engine.interfaces.Collidable;
import engine.interfaces.Movable;
import engine.interfaces.Shootable;
import engine.objects.GameObject;

public class Ship extends GameObject implements Movable, Shootable, Collidable {

	// Geometric integers
	public final int SIDELENGTH = 20, HALF_LENGTH = SIDELENGTH / 2,
			RADIUS = (int) ((SIDELENGTH * Math.sqrt(2)) / 2),
			MIN_FIRE_TIME = 20;

	public final double SPEEDCHANGE = .1, MIN_SPEED = .001, MAX_SPEED = .15,
			sixtyDegrees = Math.PI / 3, SPEED_REDUCER = 2.4, correction = -1.5;

	public double angle, ROTATESPEED = .05;

	// Shooting
	public int[] xPoints, yPoints;
	public int counter, lives, vpx, vpy;
	public boolean fire, accelerate, brake, turnRight, turnLeft;
	public ArrayList<Bullet> bullets;

	public Play play;

	public boolean invincible;
	public int invincibleCounter;

	public Ship(Sandbox sandbox, Play play, int xpos, int ypos) {
		super(sandbox);
		name = "Ship";
		this.xpos = xpos;
		this.ypos = ypos;
		xPoints = new int[4];
		yPoints = new int[4];

		xPoints[0] = xpos;
		xPoints[1] = xpos + HALF_LENGTH;
		xPoints[2] = xpos;
		xPoints[3] = xpos - HALF_LENGTH;

		yPoints[0] = ypos + HALF_LENGTH;
		yPoints[1] = ypos - HALF_LENGTH;
		yPoints[2] = ypos;
		yPoints[3] = ypos - HALF_LENGTH;

		this.play = play;

		fire = accelerate = brake = false;
		turnRight = turnLeft = false;
		lives = 3;
		angle = counter = 0;
		dx = dy = 0;
		createBullets();
		invincible = true;
		invincibleCounter = 0;
		rec = new Rectangle((int) xpos, (int) ypos, SIDELENGTH, SIDELENGTH);
	}

	@Override
	public void move() {
		// Determine the angle of the player
		if (turnLeft)
			angle += ROTATESPEED;

		if (turnRight)
			angle -= ROTATESPEED;

		// Keep angle within bounds of 0 to 2*PI
		if (angle > (2 * Math.PI))
			angle -= (2 * Math.PI);
		else if (angle < 0)
			angle += (2 * Math.PI);

		// For a triangle ABC with a side AB that is the radius of a circle
		// and point C on the edge of the circle, point C can be found
		// by using the equation:
		// xPoint = radius * Math.sin(m<CAB + 90)
		// yPoint = radius * Math.cos(m<CAB + 90)
		// Dividing by SPEED_REDUCER just reduces the speed of the player.

		if (accelerate) {
			dx = (int) (RADIUS * Math.sin(angle + correction) / SPEED_REDUCER);
			dy = (int) (RADIUS * Math.cos(angle + correction) / SPEED_REDUCER);
		}

		// If the no force is being applied,
		if (!accelerate && !brake) {
			// Set the new dx and dy to 97% of the old dx and dy
			// "Coast"
			dx *= .97;
			dy *= .97;
		}
		if (brake) {
			dx *= .95;
			dy *= .95;
		}
		xpos += dx;
		ypos += dy;
	}

	@Override
	public void shoot() {
		if (checkBulletsFired() < 5) {
			// Make a new bullet along the trajectory of the player
			double xTrajectory = RADIUS * Math.sin(angle + correction - .05);
			double yTrajectory = RADIUS * Math.cos(angle + correction - .05);

			Bullet bullet = new Bullet(getSandbox(), (int) (xpos - vpx),
					(int) (ypos - vpy), xTrajectory, yTrajectory);
			bullets.add(bullet);
			play.addObject(bullet);
			fire = true;
		}
	}

	@Override
	public int checkBulletsFired() {
		int bulletsFired = 0;
		for (Bullet bullet : bullets)
			if (bullet != null && bullet.fired && !bullet.dead)
				bulletsFired++;
		return bulletsFired;
	}

	public void paint(Graphics2D g, int vpx, int vpy) {
		if (!invincible)
			g.setColor(Color.red);
		else
			g.setColor(Color.white);
		xPoints[0] = (int) (xpos + RADIUS * Math.cos(Math.PI - angle)) - vpx;
		yPoints[0] = (int) (ypos + RADIUS * Math.sin(Math.PI - angle)) - vpy;

		xPoints[1] = (int) (xpos + RADIUS * Math.cos(Math.PI - angle + 2.14))
				- vpx;
		yPoints[1] = (int) (ypos + RADIUS * Math.sin(Math.PI - angle + 2.14))
				- vpy;

		xPoints[2] = (int) (xpos) - vpx;
		yPoints[2] = (int) (ypos) - vpy;

		xPoints[3] = (int) (xpos + RADIUS * Math.cos(Math.PI - angle - 2.14))
				- vpx;
		yPoints[3] = (int) (ypos + RADIUS * Math.sin(Math.PI - angle - 2.14))
				- vpy;

		Polygon p = new Polygon(xPoints, yPoints, 4);
		g.fillPolygon(p);

		for (Bullet bull : bullets)
			bull.paint(g);
	}

	@Override
	public void createBullets() {
		bullets = new ArrayList<Bullet>(5);
	}

	@Override
	public void updateBullets(ArrayList<Bullet> bullets, int vpx, int vpy) {
		for (Bullet bull : bullets) {
			if (bull != null) {
				if (bull.dead || bull.ypos < 0 || bull.ypos > Game.HEIGHT
						|| bull.xpos < 0 || bull.xpos > Game.WIDTH) {
					bullets.remove(bull);
					play.removeObject(bull);
					break;
				} else
					bull.update(vpx, vpy);
			}
		}
	}

	public void update(int vpx, int vpy) {
		counter++;
		if (invincibleCounter < 250) {
			invincible = true;
			invincibleCounter++;
		} else
			invincible = false;
		this.vpx = vpx;
		this.vpy = vpy;
		processInput();
		if (fire && counter > MIN_FIRE_TIME) {
			shoot();
			counter = 0;
		}
		move();
		rec = new Rectangle((int) (xpos - vpx), (int) (ypos - vpy), SIDELENGTH,
				SIDELENGTH);
		updateBullets(bullets, vpx, vpy);
	}

	public void reset() {
		xpos = 250;
		ypos = 250;
		for (int i = 0; i < bullets.size(); i++)
			play.removeObject(bullets.get(i));

		bullets.clear();
		angle = 0;
		dx = dy = 0;
		invincible = true;
		invincibleCounter = 0;
	}

	public void processInput() {
		if (KeyboardInput.get().keyDown(KeyEvent.VK_W)) {
			accelerate = true;
		}
		if (KeyboardInput.get().keyDown(KeyEvent.VK_S)) {
			brake = true;
		}
		if (KeyboardInput.get().keyDown(KeyEvent.VK_A)) {
			turnLeft = true;
		}
		if (KeyboardInput.get().keyDown(KeyEvent.VK_D)) {
			turnRight = true;
		}
		if (KeyboardInput.get().keyDown(KeyEvent.VK_SPACE)) {
			fire = true;
		}

		if (!KeyboardInput.get().keyDown(KeyEvent.VK_W)) {
			accelerate = false;
		}
		if (!KeyboardInput.get().keyDown(KeyEvent.VK_S)) {
			brake = false;
		}
		if (!KeyboardInput.get().keyDown(KeyEvent.VK_A)) {
			turnLeft = false;
		}
		if (!KeyboardInput.get().keyDown(KeyEvent.VK_D)) {
			turnRight = false;
		}
		if (!KeyboardInput.get().keyDown(KeyEvent.VK_SPACE)) {
			fire = false;
		}
	}

	@Override
	public void onCollision(GameObject obj2) {
		if (!invincible)
			if (obj2.name == "Asteroid") {
				reset();
				play.lives--;
			}
	}
}
