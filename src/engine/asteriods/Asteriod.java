package engine.asteriods;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;
import engine.interfaces.Collidable;
import engine.interfaces.Movable;
import engine.interfaces.Paintable;
import engine.objects.GameObject;


public class Asteriod extends GameObject implements Paintable, Collidable,
		Movable {

	public Asteriod(Sandbox sandbox, int xpos, int ypos, int dx, int dy,
			int width, int height) {
		super(sandbox);
		name = "Asteriod";
		this.xpos = xpos;
		this.ypos = ypos;
		this.dx = dx;
		this.dy = dy;
		rec = new Rectangle(xpos, ypos, width, height);
		image = ResourceManager.getInstance().images.getImage("asteriod1.png");
	}

	@Override
	public void move() {
		xpos += dx;
		ypos += dy;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setVerticalSpeed(double speed) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFriction(double friction) {
		// TODO Auto-generated method stub

	}

	public void blowUp() {

	}

	@Override
	public void onCollision(GameObject obj2) {

	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(image, (int) (xpos), (int) (ypos), null);
	}

	@Override
	public void update() {
		move();
	}
}
