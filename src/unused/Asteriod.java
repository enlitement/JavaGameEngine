package unused;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import test.gameInterfaces.Movable;
import test.gameInterfaces.Paintable;

public class Asteriod extends Sprite implements Paintable, Movable {

	public int health;
	public final int WIDTH = 20 + (int) (Math.random() * 20),
			HEIGHT = 20 + (int) (Math.random() * 20);
	
	public Asteriod(int xpos, int ypos) {
		super(xpos, ypos);
		rec = new Rectangle((int) this.xpos, (int) this.ypos, WIDTH, HEIGHT);
		assignSlopes();
		health = 5;
	}

	public void assignSlopes() {
		dx = Math.random() * 10;
		dy = Math.random() * 10;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int) xpos, (int) ypos, 20, 20);
	}

	@Override
	public void move() {
		xpos += dx;
		ypos += dy;
		System.out.println("Xpos:" + xpos + " Ypos:" + ypos);
	}

	public void update() {
		rec = new Rectangle((int) xpos, (int) ypos, WIDTH, HEIGHT);
	}

	@Override
	public void moveFree(double direction, double speed) {
		
	}

	@Override
	public void moveToward(int x, int y) {
		
	}

	@Override
	public void setHorizontalSpeed(double speed) {
		dx  = speed;
		
	}

	@Override
	public void setVerticalSpeed(double speed) {
		dy = speed;
	}

	@Override
	public void setFriction(double friction) {
	}
}
