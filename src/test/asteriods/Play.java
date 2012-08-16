package test.asteriods;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import test.core.Sandbox;
import test.objects.GameObject;
import test.rooms.Room;

public class Play extends Room {

	public Ship ship;

	public int wallCounter;
	public Play(Sandbox sandbox) {
		super(sandbox);

		ship = new Ship(sandbox,150,150);
		addObject(ship);
	}

	@Override
	public void paint(Graphics2D g) {
		ship.paint(g);
	}

	@Override
	public void update() {
		ship.update();
		if(getSandbox().getKeyBoard().keyDown(KeyEvent.VK_SPACE)) {
			for(GameObject obj: getCollidables())
			System.out.println(obj.name);
		}
	}

}
