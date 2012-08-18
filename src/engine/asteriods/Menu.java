package engine.asteriods;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.*;
import engine.rooms.Room;

public class Menu extends Room {

	public MenuButton play, help, another, exit;
	public MenuLayoutManager layoutManager;

	public Menu(Sandbox sandbox) {
		super(sandbox);
		
		// Set up MenuLayoutManager
		layoutManager = new MenuLayoutManager(this);

		// Set up buttons
		play = new MenuButton(sandbox, "Play");

		// Register for Action Events
		play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				addRoom(new Play(getSandbox()));
				removeMe();
				System.out.println("banger!");
			}
		});

		help = new MenuButton(sandbox, "Instructions");
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				addRoom(new Help(getSandbox()));
				removeMe();
			}
		});
		help.setDisplayColor(Color.pink);

		another = new MenuButton(sandbox, "Highscores");
		another.setDisplayColor(Color.gray);

		exit = new MenuButton(sandbox, "Exit");
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				getSandbox().exitGame();
			}
			
		});
		exit.setDisplayColor(Color.green);

		// Add buttons
		layoutManager.addButton(play);
		layoutManager.addButton(help);
		layoutManager.addButton(another);
		layoutManager.addButton(exit);

		// Set a title image for the menu screen
		// layoutManager.setTitleImage("asteriodBackground.png");
		layoutManager.setTitleString("Asteriods!");
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
