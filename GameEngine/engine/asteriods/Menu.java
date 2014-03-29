package engine.asteriods;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.gui.MenuButton;
import engine.gui.MenuLayoutManager;
import engine.rooms.Room;

public class Menu extends Room {

	public MenuButton play, help, highscores, exit;
	public MenuLayoutManager layoutManager;

	public Menu(Sandbox sandbox) {
		super(sandbox);

		// Set up MenuLayoutManager
		layoutManager = new MenuLayoutManager(this);

		// Set up buttons
		play = new MenuButton(sandbox, "Play");
		play.setOutlineColor(Color.white);
		// Register for Action Events
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				addRoom(new Play(getSandbox()));
				removeMe();
			}
		});
		play.setDisplayColor(Color.red);

		help = new MenuButton(sandbox, "Instructions");
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				addRoom(new Help(getSandbox()));
				removeMe();
			}
		});
		help.setOutlineColor(Color.white);
		help.setDisplayColor(Color.red);

		highscores = new MenuButton(sandbox, "Highscores");
		highscores.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				addRoom(new Highscore(getSandbox()));
				removeMe();
			}

		});
		highscores.setDisplayColor(Color.red);
		highscores.setOutlineColor(Color.white);

		exit = new MenuButton(sandbox, "Exit");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				getSandbox().exitGame();
			}

		});
		exit.setOutlineColor(Color.white);
		exit.setDisplayColor(Color.red);;

		// Add buttons to layout manager
		layoutManager.addButton(play);
		layoutManager.addButton(help);
		layoutManager.addButton(highscores);
		layoutManager.addButton(exit);

		// Add buttons to object list
		addObject(play);
		addObject(help);
		addObject(highscores);
		addObject(exit);

		// Set a title image for the menu screen
		layoutManager.setTitleImage("asteroidTitle2.png");

		// Or, if an image is unavailable:
		layoutManager.setTitleString("Asteroids!");

		// Set the background for the menu
		layoutManager.setBackground("asteroidBackground.png");

		// Layout the components
		layoutManager.validate();
	}

	@Override
	public void paint(Graphics2D g) {
		layoutManager.paint(g);
	}

	@Override
	public void update() {
		layoutManager.update();
	}
}
