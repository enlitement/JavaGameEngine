package brooklynbridge;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.gui.MenuButton;
import engine.gui.MenuLayoutManager;
import engine.rooms.Room;

/**
 * I want to have buttons made from bricks for the menu. Select it, and
 * the button pushes in. Also, just to make the button seem more realistic,
 * when you click on the button but your mouse is outside of the box, the button's
 * click effect is reverted. If you are still holding the click and hover back 
 * inside the box, then the button is reclicked.
 * 
 * @author helson.taveras
 *
 */
public class BridgeMenu extends Room {

	public MenuButton start, exit;
	public MenuLayoutManager layoutManager;

	public BridgeMenu(Sandbox sandbox) {
		super(sandbox);

		// Set up MenuLayoutManager
		layoutManager = new MenuLayoutManager(this);

		// Set up buttons
		start = new MenuButton(sandbox, "Start");
		start.text = "Start";
		start.width = 120;
		start.height = 64;
		// Register for Action Events
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				//First Room go here
				addRoom(new PR1(getSandbox()));
				removeMe();
			}
		});
		start.setDisplayColor(Color.blue);

		/*help = new MenuButton(sandbox, "Instructions");
		help.text= "Help";
		help.setImage(ResourceManager.get().images.getImage("stoneButton.gif"));
		help.width = 120;
		help.height = 64;
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//Help Screen go here
				//addRoom(new Help(getSandbox()));
				//removeMe();
			}
		});
		help.setDisplayColor(Color.blue);*/
		
		exit = new MenuButton(sandbox, "Exit");
		exit.text = "Exit";
		exit.width = 120;
		exit.height = 64;
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				getSandbox().exitGame();
			}

		});
		exit.setDisplayColor(Color.blue);

		// Add buttons to layout manager
		layoutManager.addButton(start);
		//layoutManager.addButton(help);
		layoutManager.addButton(exit);

		// Add buttons to object list
		addObject(start);
		//addObject(help);
		addObject(exit);

		// Set a title image for the menu screen
		// layoutManager.setTitleImage("asteroidTitle2.png");

		// Or, if an image is unavailable:
		layoutManager.setTitleString("Brooklyn Bridge");

		// Set the background for the menu
		layoutManager.setBackground("background.gif");

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
