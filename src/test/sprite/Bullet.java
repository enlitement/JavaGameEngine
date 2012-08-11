package test.sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import test.gameInterfaces.Movable;
import test.gameInterfaces.Paintable;

public class Bullet extends Sprite implements Paintable, Movable {

	public boolean fired;
	public double angle;
	public final double SPEED_REDUCER = 2.1;
	public final int SIDE = 3;
	
	public Bullet(int x, int y, double dx, double dy) {
		super(x, y);
		fired = true;
		
		rec = new Rectangle((int)xpos,(int)ypos,SIDE,SIDE);
		
		// To keep the same trajectory as the player, we need to also pretend
		// that we are calculating the path of the bullet as though the radius
		// of the square is also equal to Player.RADIUS. In other words, we need
		// to keep the slope of the path the same. However, we need to divide by 
		// a slightly smaller SPEED_REDUCER, which in turn will mean a faster 
		// velocity (small S_R means less reduction).
		this.dx = dx/SPEED_REDUCER;
		this.dy = dy/SPEED_REDUCER;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.drawRect((int)xpos, (int)ypos, SIDE, SIDE);
	}

	@Override
	public void move() {
		if (fired) {
			ypos += dy;
			xpos += dx;
		}
	}

	public void update() {
		move();
		rec = new Rectangle((int)xpos,(int)ypos,SIDE,SIDE);
	}
}
