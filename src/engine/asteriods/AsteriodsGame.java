package engine.asteriods;

import java.awt.Graphics2D;

import engine.core.Sandbox;


public class AsteriodsGame extends Sandbox {

	public Menu menu;

	public AsteriodsGame() {
		super();
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
		getCurrentRoom().update();
		getCollisionManager().updateSprites();
	}

	public static void main(String[] args) {
		AsteriodsGame ast = new AsteriodsGame();
		ast.setTitle("Asteriods, made with JGameEngine");
		ast.runEngine(ast);
	}
}
