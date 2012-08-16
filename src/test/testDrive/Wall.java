package test.testDrive;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import test.core.Sandbox;
import test.gameInterfaces.Collidable;
import test.gameInterfaces.Paintable;
import test.objects.GameObject;

public class Wall extends GameObject implements Paintable, Collidable {

	public int width, height;

	public Wall(Sandbox sandbox, int xpos, int ypos) {
		super(sandbox);
		name = "Wall";
		this.xpos = xpos;
		this.ypos = ypos;
		width = 32;
		height = 32;
		rec = new Rectangle(xpos,ypos,width,height);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(java.awt.Color.red);
		g.fillRect((int) xpos, (int) ypos, width, height);
		g.setColor(java.awt.Color.black);
		g.drawRect((int) xpos, (int) ypos, width, height);
	}

	@Override
	public void onCollision(GameObject obj2) {
		// TODO Auto-generated method stub
		
	}

}
