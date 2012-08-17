package test.asteriods;

import java.awt.Graphics2D;

import test.core.Sandbox;
import test.extras.AButton;

public class AsteriodsGame extends Sandbox {

	public AButton button;

	public Menu menu;

	public AsteriodsGame() {
		super();
	}

	public void init() {
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
		if (getRoomList().size()>0)
			getRoomList().get(currentRoom).paint(g);
	}

	@Override
	public void run() {
		if (getRoomList().size()>0) {
			getRoomList().get(0).update();
			getCollisionManager().updateSprites();
		}
	}

	public static void main(String[] args) {
		AsteriodsGame ast = new AsteriodsGame();
		ast.createEngine(ast);
		ast.init();
	}
}
