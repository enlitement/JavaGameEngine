package engine.sprites;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import engine.animation.Animation;
import engine.core.input.KeyboardInput;
import engine.core.resources.ResourceManager;
import engine.editor.Inventory;
import engine.map.Tile;

public class Player extends Entity {

	private int inventory_size = 5, display_inv_size = 6, frame_length = 3;

	public Player(String name, Tile tiles) {
		super(name, tiles);
		inventory = new Inventory(this, inventory_size, inventory_size,
				display_inv_size);
		setImage("char.png");
		classType = Player.class;
		animation = new Animation(this, frame_length, WIDTH, HEIGHT);
	}

	public Player() {
		name = "Player";
		inventory = new Inventory(this, inventory_size, inventory_size,
				display_inv_size);
		setImage("char.png");
		classType = Player.class;
		animation = new Animation(this, frame_length, WIDTH, HEIGHT);
	}

	public void paint(Graphics2D g) {
		animation.paint(g);
	}

	int tempDir1;
	int frames;

	public void update() {
		direction = processInput();

		moveTimer();
		getItem();
	}

	int counter = 0;

	public int processInput() {
		counter++;
		if (KeyboardInput.get().keyDownOnce(KeyEvent.VK_1) && counter > 300) {
			//ResourceManager.get().playContinuousTrack("intro.wav");
			counter = 0;
			System.out.println("played");
		}

		if (KeyboardInput.get().keyDownOnce(KeyEvent.VK_2)) {
			ResourceManager.get().stopContinuousTrack();
		}
		if (KeyboardInput.get().keyDownOnce(KeyEvent.VK_3)) {
			System.out.println("BLOOP");
			ResourceManager.get().addClip("bloop.wav");
			ResourceManager.get().playClip("bloop.wav", 1);
		}
		if (KeyboardInput.get().keyDown(KeyEvent.VK_W)) {
			return UP;
		}
		if (KeyboardInput.get().keyDown(KeyEvent.VK_S)) {
			return DOWN;
		}
		if (KeyboardInput.get().keyDown(KeyEvent.VK_A)) {
			return LEFT;
		}
		if (KeyboardInput.get().keyDown(KeyEvent.VK_D)) {
			return RIGHT;
		}
		return STILL;
	}
}
