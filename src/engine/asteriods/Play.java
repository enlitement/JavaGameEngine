package engine.asteriods;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.core.Game;
import engine.core.Sandbox;
import engine.core.input.KeyboardInput;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.gui.MenuButton;
import engine.rooms.Room;

public class Play extends Room {

	public AsteroidCreator creator;
	public MenuButton menu;
	public Ship ship;

	public int score, vpx = 0, vpy = 0;
	public final int scoreIncrement = 10, gameWidth = Game.WIDTH,
			gameHeight = Game.HEIGHT;

	public Play(Sandbox sandbox) {
		super(sandbox);
		name = "Play";
		menu = new MenuButton(getSandbox(), "Menu", (Game.WIDTH / 2) - 50,
				Game.HEIGHT - 40);
		menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				addRoom(new Menu(getSandbox()));
				removeMe();
			}
		});
		menu.changeOutlineColor(Color.cyan);
		addObject(menu);

		ship = new Ship(sandbox, this, 150, 150);
		addObject(ship);

		vpx = (int) (ship.xpos - gameWidth / 2);
		vpy = (int) (ship.ypos - gameHeight / 2);

		creator = new AsteroidCreator(sandbox, this, vpx, vpy);
		addObject(creator);
	}

	@Override
	public void paint(Graphics2D g) {
		// g.setColor(Color.black);
		// g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		ship.paint(g, vpx, vpy);
		creator.paint(g, vpx, vpy);
		menu.paint(g);
		drawFPS();
		g.drawString("Score:" + score, 10, 30);
	}

	@Override
	public void update() {
		vpx = (int) (ship.xpos - gameWidth / 2);
		vpy = (int) (ship.ypos - gameHeight / 2);
		creator.update(vpx, vpy);
		ship.update(vpx, vpy);
		menu.update();
		if (KeyboardInput.get().keyDownOnce(java.awt.event.KeyEvent.VK_X)) {
			System.out.println("_______ LIST : _______");
			for (int i = 0; i < getCollidables().size(); i++) {
				System.out.println(getCollidables().get(i).name + " ("
						+ (int) getCollidables().get(i).xpos + ","
						+ (int) getCollidables().get(i).ypos + ")");
			}
			System.out.println("_____________________");
		}
	}

}
