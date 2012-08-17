package test.asteriods;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import test.core.Sandbox;
import test.objects.GameObject;
import test.rooms.Room;

public class Play extends Room {

	public AsteriodCreator creator;

	public Ship ship;
	public int wallCounter;
	public int vpx = 0, vpy = 0;

	public final int gameWidth = getSandbox().getGraphics().WIDTH,
			gameHeight = getSandbox().getGraphics().HEIGHT;

	public Play(Sandbox sandbox) {
		super(sandbox);
		
		ship = new Ship(sandbox, 150, 150);
		addObject(ship);
		
		vpx = (int) (ship.xpos - gameWidth / 2);
		vpy = (int) (ship.ypos - gameHeight / 2);
		
		creator = new AsteriodCreator(sandbox,vpx,vpy);
		addObject(creator);
		
		//System.out.println("gameWidth"+gameWidth+" height"+gameHeight);
	}

	@Override
	public void paint(Graphics2D g) {
		ship.paint(g, vpx, vpy);
		creator.paint(g, vpx, vpy);
	}

	@Override
	public void update() {
		vpx = (int) (ship.xpos - gameWidth / 2);
		vpy = (int) (ship.ypos - gameHeight / 2);
		creator.update(vpx,vpy);
		ship.update(vpx,vpy);
	}

}
