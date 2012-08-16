package test.testDrive;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import test.core.Sandbox;
import test.gameInterfaces.Collidable;
import test.gameInterfaces.Movable;
import test.gameInterfaces.Paintable;
import test.objects.GameObject;

public class Player extends GameObject implements Paintable, Movable,
		Collidable {

	public boolean collision;
	
	public Player(Sandbox sandbox) {
		super(sandbox);
		name = "Player";
		xpos = 40;
		ypos = 100;
		dx = 5;
		dy = 5;
		rec = new Rectangle((int) xpos, (int) ypos, 32, 32);
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawRect((int) xpos, (int) ypos, 32, 32);
	}

	@Override
	public void move() {
		if (getKeyBoard().keyDown(KeyEvent.VK_W)) {
			System.out.println("W");
			ypos -= dy;
		}
		if (getKeyBoard().keyDown(KeyEvent.VK_S)) {
			ypos += dy;
		}
		if (getKeyBoard().keyDown(KeyEvent.VK_A)) {
			xpos -= dx;
		}
		if (getKeyBoard().keyDown(KeyEvent.VK_D)) {
			xpos += dx;
		}
	}

	@Override
	public void moveFree(double direction, double speed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveToward(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHorizontalSpeed(double speed) {
		dx = speed;
	}

	@Override
	public void setVerticalSpeed(double speed) {
		dy = speed;
	}

	@Override
	public void setFriction(double friction) {
		dx -= friction;
		dy -= friction;
	}

	@Override
	public void update() {
		move();
		rec = new Rectangle((int) xpos, (int) ypos, 32, 32);
	}

	@Override
	public void onCollision(GameObject obj2) {
		if (obj2.name == "Wall") {
			Wall wall = (Wall) obj2;
			if (xpos < wall.xpos) {
				xpos -= dx;
			}
			else if (xpos > wall.xpos) {
				xpos += dx;
			}
			if (ypos < wall.ypos) {
				ypos -= dy;
			}
			else if (ypos > wall.ypos) {
				ypos += dy;
			}
		}
	}
}