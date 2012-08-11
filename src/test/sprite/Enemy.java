package test.sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import test.gameInterfaces.Movable;
import test.gameInterfaces.Paintable;

public class Enemy extends Sprite implements Movable, Paintable{

	public Enemy(int x, int y) {
		super(x, y);
		rec = new Rectangle((int)xpos,(int)ypos, 32, 32);
	}

	@Override
	public void move() {
		xpos += dx;
		ypos += dy;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.drawRect((int)xpos, (int)ypos, 32, 32);
	}

}
