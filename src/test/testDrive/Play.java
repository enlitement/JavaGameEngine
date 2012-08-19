package test.testDrive;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import test.core.Sandbox;
import test.objects.GameObject;
import test.rooms.Room;

public class Play extends Room {

	public Player player;

	public Wall[] walls;
	public int wallCounter;
	public Play(Sandbox sandbox) {
		super(sandbox);

		player = new Player(sandbox);
		addObject(player);
		wallCounter = 0;
		walls = new Wall[20];
		for(int x = 5; x < 15; x ++) {
			walls[wallCounter] = new Wall(sandbox, x*32,0);
			addObject(walls[wallCounter]);
			wallCounter++;
		}
		for(int x = 5; x < 15; x ++) {
			walls[wallCounter] = new Wall(sandbox, 0,x*32);
			addObject(walls[wallCounter]);
			wallCounter++;
		}
	}

	@Override
	public void paint(Graphics2D g) {
		player.paint(g);
		for(Wall wall:walls)
			wall.paint(g);
	}

	@Override
	public void update() {
		if(getSandbox().getKeyBoard().keyDown(KeyEvent.VK_SPACE)) {
			for(GameObject obj: getCollidables())
				System.out.println(obj.name);
		}
		player.update();
	}

}
