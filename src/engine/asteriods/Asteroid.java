package engine.asteriods;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;
import engine.interfaces.Collidable;
import engine.interfaces.Movable;
import engine.interfaces.Paintable;
import engine.objects.GameObject;

public class Asteroid extends GameObject implements Paintable, Collidable,
		Movable {
	int vpx, vpy;
	public boolean isAlive;
	public int width, height;
	public int health, maxHealth;

	public Asteroid(Sandbox sandbox, int xpos, int ypos, int dx, int dy,
			int width, int height) {
		super(sandbox);
		name = "Asteroid";
		this.xpos = xpos;
		this.ypos = ypos;
		this.dx = dx;
		this.dy = dy;
		this.width = width;
		this.height = height;
		isAlive = true;
		maxHealth = health = 3;
		rec = new Rectangle(xpos, ypos, width, height);
		image = ResourceManager.getInstance().images.getImage("asteriod1.png");
	}

	@Override
	public void move() {
		xpos += dx;
		ypos += dy;
	}

	public void blowUp() {

	}

	@Override
	public void onCollision(GameObject obj2) {
		if (obj2.name == "Bullet") {
			health--;
			System.out.println("Health:" + health);
		}
	}

	@Override
	public void paint(Graphics2D g) {
		if (isAlive) {
			g.drawImage(image, (int) (xpos - vpx), (int) (ypos - vpy), null);
			g.setColor(Color.GREEN);
			g.drawRect((int) (xpos - vpx), (int) (ypos - vpy), width, height);
		}

	}

	public void update(int vpx, int vpy) {
		this.vpx = vpx;
		this.vpy = vpy;
		rec = new Rectangle((int) (xpos - vpx), (int) (ypos - vpy), width,
				height);
		move();
	}
}
