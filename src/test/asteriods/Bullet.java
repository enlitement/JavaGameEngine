package test.asteriods;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import test.core.Sandbox;
import test.gameInterfaces.Movable;
import test.gameInterfaces.Paintable;
import test.objects.GameObject;

public class Bullet extends GameObject implements Paintable, Movable {

	public boolean fired;
	public double angle;
	public final double SPEED_REDUCER = 2.1;
	public final int SIDE = 3;
	
	public Bullet(Sandbox sandbox, int x, int y, double dx, double dy) {
		super(sandbox);
		name = "Bullet";
		xpos = x;
		ypos = y;
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
	public void paint(Graphics2D g) {
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
}
