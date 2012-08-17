package test.asteriods;

import java.awt.Graphics2D;

import test.core.Sandbox;

public class AsteriodsGame extends Sandbox {

	public Menu menu;

	public AsteriodsGame() {
		super();
		System.out.println("Sandbox set from Test");
		createRooms();
		System.out.println("Menu created");
		getCollisionManager().setUpCollidables();
		System.out.println("Init complete");
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
		AsteriodsGame ast = new AsteriodsGame();
		ast.setTitle("Asteriods, made with JGameEngine");
		ast.runEngine(ast);
	}
}
