package engine.asteriods;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import engine.core.Sandbox;
import engine.gui.*;
import engine.rooms.Room;

public class Pause extends Room {

	public MenuButton play, settings, menu;
	public MenuLayoutManager layoutManager;

	public Pause(Sandbox sandbox) {
		super(sandbox);
		name = "Pause";
		// Set up MenuLayoutManager
		layoutManager = new MenuLayoutManager(this);

		// Set up buttons
		play = new MenuButton(sandbox, "Play");
		play.setOutlineColor(Color.white);
		// Register for Action Events
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				removeMe();
			}
		});
		play.setDisplayColor(Color.red);

		settings = new MenuButton(sandbox, "Settings");
		settings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//addRoom(new Settings(getSandbox()));
				//removeMe();
			}
		});
		settings.setOutlineColor(Color.white);
		settings.setDisplayColor(Color.red);;

		menu = new MenuButton(sandbox, "Menu");
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					Play.saveHighscore();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addRoom(new Menu(getSandbox()));
				removeMe();
			}
		});
		menu.setOutlineColor(Color.white);
		menu.setDisplayColor(Color.red);

		// Add buttons to layout manager
		layoutManager.addButton(play);
		layoutManager.addButton(settings);
		layoutManager.addButton(menu);
		
		// Add buttons to object list
		addObject(play);
		addObject(settings);
		addObject(menu);
		
		// Set a title image for the menu screen
		layoutManager.setTitleImage("asteroidTitle2.png");
		
		// Layout the components
		layoutManager.validate();
	}

	@Override
	public void paint(Graphics2D g) {
		layoutManager.paint(g);
		g.setColor(Color.white);
		g.drawRect(10, 10, 70, 70);
	}

	@Override
	public void update() {
		layoutManager.update();
	}
}
