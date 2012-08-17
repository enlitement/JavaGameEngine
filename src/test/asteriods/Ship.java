package test.asteriods;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import test.core.Game;
import test.core.Sandbox;
import test.gameInterfaces.Movable;
import test.gameInterfaces.Shootable;
import test.objects.GameObject;

public class Ship extends GameObject implements Movable, Shootable {

	public int[] xPoints, yPoints;

	// Geometric integers
	public final int SIDELENGTH = 20, HALF_LENGTH = SIDELENGTH / 2,
			RADIUS = (int) ((SIDELENGTH * Math.sqrt(2)) / 2),
			MIN_FIRE_TIME = 20;

	public final double SPEEDCHANGE = .1, MIN_SPEED = .001, MAX_SPEED = .15,
			sixtyDegrees = Math.PI / 3, SPEED_REDUCER = 2.4, correction = -1.5;

	public double angle, ROTATESPEED = .05;

	// Shooting
	public int counter, lives;
	public int vpx, vpy;
	
	public boolean fire, accelerate, brake, turnRight, turnLeft;

	public ArrayList<Bullet> bullets;

	public Ship(Sandbox sandbox, int xpos, int ypos) {
		super(sandbox);

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
		
		/**
		 * 
		 * 	3.xpos-HALF,			1.xpos+HALF,
		 * 	ypos+HALF				ypos-HALF
		 * 
		 * 			2.xpos,ypos
		 * 
		 * 
		 * 			0.xpos,
		 * 			ypos+half
		 */
		fire = accelerate = brake = false;
		turnRight = turnLeft = false;
		lives = 3;
		angle = counter = 0;
		dx = dy = 0;
		createBullets();
		rec = new Rectangle((int) xpos, (int) ypos, SIDELENGTH, SIDELENGTH);
	}

	@Override
	public void move() {
		// Determine the angle of the player
		if (turnLeft) {
			angle += ROTATESPEED;
		}
		if (turnRight) {
			angle -= ROTATESPEED;
		}

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
			dx = (int) (RADIUS * Math.sin(angle+correction) / SPEED_REDUCER);
			dy = (int) (RADIUS * Math.cos(angle+correction) / SPEED_REDUCER);
			//System.out.println("ADx:" + dx);
			//System.out.println("ADy:" + dy);
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
		// System.out.println("Xpos:" + xpos);
		// System.out.println("Ypos:" + ypos);
		// System.out.println("Dx:" + dx);
		// System.out.println("Dy:" + dy);

	}

	@Override
	public void shoot() {
		if (checkBulletsFired() < 5) {
			// Make a new bullet along the trajectory of the player
			double xTrajectory = RADIUS * Math.sin(angle + correction);
			double yTrajectory = RADIUS * Math.cos(angle + correction);

			Bullet bullet = new Bullet(getSandbox(),
					(int) (xpos - vpx + xTrajectory), (int) (ypos - vpy + yTrajectory),
					xTrajectory, yTrajectory);
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

	public void paint(Graphics2D g, int vpx, int vpy) {
		
		g.drawRect(0-vpx,0-vpy,600,400);
		g.drawRect((int)(150-vpx),(int)150-vpy,32,32);
		
		g.drawString("Vpx"+vpx+"Vpy"+vpy,0,0+10);
		g.drawString("Vpx+Width"+(vpx+600),520,10);
		g.drawString("Vpy+Height"+(vpy+400),0,390);
		g.drawString("Vpx+W"+(vpx+600),520,380);
		g.drawString("Vpy+H"+(vpy+400),520,390);
		g.setColor(java.awt.Color.red);
		g.drawString(""+(int)(xpos)+","+(int)(ypos),(int)xpos-vpx,(int)ypos-vpy);
		for (int i = 0; i < xPoints.length; i++) {
			if (i == 0) {
				xPoints[i] = (int) (xpos + RADIUS * Math.cos(Math.PI-angle)) - vpx;
				yPoints[i] = (int) (ypos + RADIUS * Math.sin(Math.PI-angle)) - vpy;
			}
			if (i == 1) {
				xPoints[i] = (int) (xpos + RADIUS * Math.cos(Math.PI-angle + 2.14)) - vpx;
				yPoints[i] = (int) (ypos + RADIUS * Math.sin(Math.PI-angle + 2.14)) - vpy;
			}
			if (i == 2) {
				xPoints[i] = (int) (xpos) - vpx;
				yPoints[i] = (int) (ypos) - vpy;
			}
			if (i == 3) {
				xPoints[i] = (int) (xpos + RADIUS * Math.cos(Math.PI-angle - 2.14)) - vpx;
				yPoints[i] = (int) (ypos + RADIUS * Math.sin(Math.PI-angle - 2.14)) - vpy;
			}
		}
		Polygon p = new Polygon(xPoints, yPoints, 4);
		g.drawPolygon(p);
		
		for(Bullet bull: bullets)
			bull.paint(g);
	}

	@Override
	public void createBullets() {
		bullets = new ArrayList<Bullet>(5);
	}

	@Override
	public void updateBullets(ArrayList<Bullet> bullets) {
		for (Bullet bull : bullets) {
			if (bull != null)
				if (bull.ypos < 0
						|| bull.ypos >  Game.HEIGHT*2
						|| bull.xpos < 0
						|| bull.xpos > Game.WIDTH*2) {
					bullets.remove(bull);
					System.out.println("Bullet removed");
					break;
				} else
					bull.update();
		}
	}

	public void update(int vpx, int vpy) {
		counter++;
		
		if(xpos > Game.WIDTH)
			xpos = 0;
		
		if(xpos < 0)
			xpos = Game.WIDTH;
		
		if(ypos > Game.HEIGHT)
			ypos = 0;
		
		if(ypos < 0)
			ypos = Game.HEIGHT;
		
		this.vpx = vpx;
		this.vpy = vpy;
		processInput();
		//System.out.println("Accelerate:"+accelerate);
		if (fire && counter > MIN_FIRE_TIME) {
			shoot();
			counter = 0;
		}

		move();
		rec = new Rectangle((int) xpos, (int) ypos, SIDELENGTH, SIDELENGTH);
		updateBullets(bullets);
	}

	public void reset() {
		xpos = 250;
		ypos = 250;
		bullets.removeAll(bullets);
		angle = 0;
		dx = dy = 0;
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

	public void setAccelerate(boolean accelerate) {
		this.accelerate = accelerate;
	}
	
	public void setTurnLeft(boolean turnLeft) {
		this.turnLeft = turnLeft;
	}
	public void processInput() {
		if (getKeyBoard().keyDown(KeyEvent.VK_W)) {
			accelerate = true;
		}
		if (getKeyBoard().keyDown(KeyEvent.VK_S)) {
			brake = true;
		}
		if (getKeyBoard().keyDown(KeyEvent.VK_A)) {
			turnLeft = true;
		}
		if (getKeyBoard().keyDown(KeyEvent.VK_D)) {
			turnRight = true;
		}
		if (getKeyBoard().keyDown(KeyEvent.VK_SPACE)) {
			fire = true;
		}

		if (!getKeyBoard().keyDown(KeyEvent.VK_W)) {
			accelerate = false;
		}
		if (!getKeyBoard().keyDown(KeyEvent.VK_S)) {
			brake = false;
		}
		if (!getKeyBoard().keyDown(KeyEvent.VK_A)) {
			turnLeft = false;
		}
		if (!getKeyBoard().keyDown(KeyEvent.VK_D)) {
			turnRight = false;
		}
		if (!getKeyBoard().keyDown(KeyEvent.VK_SPACE)) {
			fire = false;
		}

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
