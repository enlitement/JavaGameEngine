package engine.rooms;

import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.MenuLayoutManager;


public class Menu extends Room {

	public MenuLayoutManager layoutManager;

	public Menu(Sandbox sandbox) {
		super(sandbox);
		
		// Set up MenuLayoutManager
		layoutManager = new MenuLayoutManager(this);
		
		// Add buttons to layoutManager
		/*layoutManager.addButton(play);
		layoutManager.addButton(help);
		layoutManager.addButton(another);
		layoutManager.addButton(banger);*/

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
