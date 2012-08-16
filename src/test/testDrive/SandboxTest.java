package test.testDrive;

import java.awt.Graphics2D;

import test.core.Sandbox;
import test.extras.AButton;

public class SandboxTest extends Sandbox {
	
	public Player player;
	public AButton button;
	
	public Menu menu;
	
	public SandboxTest() {
		super();
		game.setSandbox(this);
		System.out.println("Sandbox set from Test");
		createRooms();
		System.out.println("Menu created");
		getCollisionManager().setUpCollidables();
		initializing = false;
	}
	
	public void createRooms() {
		menu = new Menu(this);
		addRoom(menu);
	}
	
	@Override
	public void paint(Graphics2D g) {
		getRoomList().get(currentRoom).paint(g);
	}

	@Override
	public void run() {
		getRoomList().get(0).update();
		getCollisionManager().updateSprites();
	}

	public static void main(String[] args) {
		new SandboxTest();
	}
}
