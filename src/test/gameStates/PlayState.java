package test.gameStates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import test.core.KeyboardInput;
import test.core.StateManager;
import test.sprite.Enemy;
import test.sprite.Player;
import test.sprite.Sprite;

public class PlayState extends AbstractPlayState {

	public ArrayList<Sprite> sprites;
	// public ArrayList<Asteriod> asteriods;
	public Player player;
	public Enemy enemy;

	// public CollisionManager collisionManager;

	public PlayState(StateManager stateManager) {
		super(stateManager);

		//initSwing();
		initEntities();
		initResources();

		// collisionManager = new CollisionManager(this);

	}
	
	@Override
	public void update() {
		processInput();
		player.update();
		//if(!pause) 
		//	player.update();
		// collisionManager.updateSprites();
	}

	@Override
	public void paint(Graphics g) {
		player.paint(g);
		//g.setColor(Color.blue);
		//g.drawRect(0, 0, 40,40);
		//for (Bullet bull : player.bullets)
		//	if (bull != null)
			//	bull.paint(g);
		// for (Asteriod ast : asteriods) {
		// ast.paint(g);
		// }
		enemy.paint(g);
	}
	
	protected void processInput() {
		// Key Pressed
		
		if (keyboard.keyDown(KeyEvent.VK_A)) {
			//System.out.println("A");
			//player.turnLeft = true;
			boolean banger = true;
			player.setLeft(banger);
		}
		if (keyboard.keyDown(KeyEvent.VK_D)) {
			player.turnRight = true;
		}
		if (keyboard.keyDown(KeyEvent.VK_W)) {
			player.accelerate = true;
			player.deccelerate = false;
		}
		if (keyboard.keyDown(KeyEvent.VK_S)) {
			player.deccelerate = true;
		}
		if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
			player.fire = true;
		}

		// Key Released
		if (keyboard.keyReleased(KeyEvent.VK_W)) {
			player.accelerate = false;
		}
		if (keyboard.keyReleased(KeyEvent.VK_S)) {
			player.deccelerate = true;
			player.accelerate = false;
		}
		if (keyboard.keyReleased(KeyEvent.VK_A)) {
			player.turnLeft = false;
		}
		if (keyboard.keyReleased(KeyEvent.VK_D)) {
			player.turnRight = false;
		}
		if (keyboard.keyReleased(KeyEvent.VK_SPACE)) {
			player.fire = false;
		}
	}

	@Override
	public void initEntities() {

		sprites = new ArrayList<Sprite>(15);

		player = new Player(200, 200);
		enemy = new Enemy(324, 324);

		sprites.add(player);
		sprites.add(enemy);
		sprites.addAll(player.bullets);

		/*
		 * asteriods = new ArrayList<Asteriod>(10); for (int i = 0; i <
		 * asteriods.size(); i++) { Asteriod ast = new Asteriod(70, 70);
		 * //asteriods.add(i, ast); asteriods.add(ast);
		 * System.out.println("Xpos" + asteriods.get(i).xpos);
		 * sprites.add(asteriods.get(i)); }
		 */

	}
	
	private boolean load = false;
	
	public void load() {
		stateManager.getResources().loading.set(true);
	}

	@Override
	public void initResources() {
		stateManager.getResources().addResource("playerImage.png", player);
		stateManager.getResources().addResource("block.png", player);
		stateManager.getResources().addResource("grassSandLeft.png", player);
		stateManager.getResources().addResource("Heart.png", player);
		//stateManager.getResources().addResource("House.png", player);
		//stateManager.getResources().addResource("House.png", player);
		stateManager.getResources().addResource("House.png", player);
		stateManager.getResources().addResource("Item.png", player);
		stateManager.getResources().addResource("Limestone.png", player);
		stateManager.getResources().addResource("Grass.png", player);
		stateManager.getResources().addResource("HPpot.png", player);
		stateManager.getResources().addResource("Sand.png", player);
		stateManager.getResources().addResource("Water.png", player);
		stateManager.getResources().addResource("Trees-Finish.png", player);
		stateManager.getResources().addResource("image.png", player);
		stateManager.getResources().addResource("bigAssFile.png", player);
		stateManager.getResources().addResource("Brick.png", player);
		stateManager.getResources().addResource("HPpot.png", player);
		load();
		
	}

}