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
		for (int i = getRoomList().size() - 1; i >= 0; i--) 
			getRoomList().get(i).paint(g);
	}

	@Override
	public void run() {
		updateCurrentRoom();
	}

	public static void main(String[] args) {
		AsteriodsGame ast = new AsteriodsGame();
		ast.setTitle("Asteriods");
		ast.runEngine(ast);
	}
}
