package test.sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Rectangle;

import test.gameInterfaces.Movable;
import test.gameInterfaces.Paintable;
import test.gameInterfaces.Shootable;

public class Player extends Sprite implements Paintable, Movable, Shootable {

	public int counter, lives;
	public static final int SIDELENGTH = 5, RADIUS = (int) (SIDELENGTH * Math
			.sqrt(2.0)), MIN_FIRE_TIME = 20;

	public double angle;
	public final double ROTATESPEED = .05, SPEED_REDUCER = 2.4;

	public boolean fire, accelerate, deccelerate, turnRight, turnLeft;

	public ArrayList<Bullet> bullets;

	public Player(int x, int y) {
		super(x, y);
		fire = accelerate = deccelerate = false;
		angle = counter = 0;
		dx = dy = 0;
		createBullets();
		rec = new Rectangle((int) xpos, (int) ypos, SIDELENGTH, SIDELENGTH);
		lives = 3;
		turnRight = turnLeft = false;
	}

	@Override
	public void move() {
		// TODO: Make rotating square class

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
			dx = (int) (RADIUS * Math.sin(angle + 90) / SPEED_REDUCER);
			dy = (int) (RADIUS * Math.cos(angle + 90) / SPEED_REDUCER);
		}

		// If the no force is being applied,
		if (!accelerate) {
			// Set the new dx and dy to 97% of the old dx and dy
			dx *= .97;
			dy *= .97;
		}
		if (deccelerate) {
			dx *= .987;
			dy *= .987;
		}
		xpos += dx;
		ypos += dy;
	}

	@Override
	public void shoot() {
		if (checkBulletsFired() < 5) {
			// Make a new bullet along the trajectory of the player
			double xTrajectory = RADIUS * Math.sin(angle + 90);
			double yTrajectory = RADIUS * Math.cos(angle + 90);

			Bullet bullet = new Bullet((int) (xpos + xTrajectory),
					(int) (ypos + yTrajectory), xTrajectory, yTrajectory);
			bullets.add(bullet);
			fire = true;
		}
	}

	@Override
	public int checkBulletsFired() {
		int bulletsFired = 0;
		for (Bullet bullet : bullets)
			if (bullet != null && bullet.fired)
				bulletsFired++;
		return bulletsFired;
	}

	@Override
	public void paint(Graphics g) {
		// System.out.println("Player paint");
		g.setColor(Color.blue);
		g.drawRect((int) xpos, (int) ypos, SIDELENGTH, SIDELENGTH);
	}

	@Override
	public void createBullets() {
		bullets = new ArrayList<Bullet>(5);
	}

	public void setLeft(boolean value) {
		turnLeft = value;
	}

	@Override
	public void updateBullets(ArrayList<Bullet> bullets) {
		for (Bullet bull : bullets) {
			if (bull != null)
				if (bull.ypos < 0 || bull.ypos > unused.Main.GHeight
						|| bull.xpos < 0 || bull.xpos > unused.Main.GWidth) {
					bullets.remove(bull);
					System.out.println("Bullet removed");
					break;
				} else
					bull.update();
		}
	}

	public void update() {
		// System.out.println("Player update");
		System.out.println("Turn left in player=" + turnLeft);
		counter++;
		/*
		 * if (fire && counter > MIN_FIRE_TIME) { shoot(); counter = 0; }
		 */
		move();
		rec = new Rectangle((int) xpos, (int) ypos, SIDELENGTH, SIDELENGTH);
		// updateBullets(bullets);
	}

	public void reset() {
		xpos = 250;
		ypos = 250;
		bullets.removeAll(bullets);
		angle = 0;
		dx = dy = 0;
	}
}
