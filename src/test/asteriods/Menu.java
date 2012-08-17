package test.asteriods;

import java.awt.Color;
import java.awt.Graphics2D;

import test.core.Sandbox;
import test.extras.AButton;
import test.extras.ButtonAction;
import test.extras.MenuLayoutManager;
import test.rooms.Room;

public class Menu extends Room {
	
	public AButton play;
	public AButton help, another,banger;
	public MenuLayoutManager layoutManager;
	public boolean passiveRendering = true;
	
	public Menu(Sandbox sandbox) {
		super(sandbox);
		
		// Set up MenuLayoutManager
		layoutManager = new MenuLayoutManager(this);
		
		// Set up buttons
		play = new AButton(sandbox,"Play");
		
		play.addAction(new ButtonAction() {
			@Override
			public void actionPerformed() {
				System.out.println("Hit play");
				addRoom(new Play(getSandbox()));
				removeMe();
			}
		});
		
		help = new AButton(sandbox,"Instructions");
		help.addAction(new ButtonAction() {
			@Override
			public void actionPerformed() {
				addRoom(new Help(getSandbox()));
				removeMe();
			}
		});
		help.setDisplayColor(Color.pink);
		
		another = new AButton(sandbox,"Highscores");
		another.setDisplayColor(Color.gray);
		
		banger = new AButton(sandbox, "Exit");
		banger.setDisplayColor(Color.green);
		
		// Add buttons
		layoutManager.addButton(play);
		layoutManager.addButton(help);
		layoutManager.addButton(another);
		layoutManager.addButton(banger);
		
		// Set a title image for the menu screen
		layoutManager.setTitleImage("asteriodBackground.png");
		
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
