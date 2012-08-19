package engine.asteriods;

import java.awt.Graphics2D;

import engine.core.CollisionManager;
import engine.core.Sandbox;

public class AsteriodsGame extends Sandbox {

	public Menu menu;

	public AsteriodsGame() {
		super();
		setCollisionManager(new CollisionManager(this));
		createRooms();
		getCollisionManager().setUpCollidables();
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
		UniversalKeys.get(this).update();
		getCurrentRoom().update();
		getCollisionManager().updateSprites();
	}

	public static void main(String[] args) {
		AsteriodsGame ast = new AsteriodsGame();
		ast.setTitle("Asteriods! :^D");
		ast.runEngine(ast);
	}
}
