package test.extras;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import test.core.resources.ResourceManager;
import test.rooms.Room;

public class MenuLayoutManager {
	public Room room;

	public int width, height;
	public int buttonX, buttonY;

	// Size between buttons
	public final int inset = 10;
	public List<Button> buttonList;

	// The menu can have either an image to display
	// a title
	public Image titleImage;
	// Or just a plain String
	public String titleString;
	// This position defines a position for either the
	// image or string.
	public int titleXpos, titleYpos;

	// The menu can have a background
	public Image background;
	public boolean tileBackground;

	public MenuLayoutManager(Room room) {
		this.room = room;
		buttonList = new ArrayList<Button>();
		width = room.getSandbox().getWidth();
		height = room.getSandbox().getHeight();

		tileBackground = false;
	}

	public void addButton(Button button) {
		buttonList.add(button);
	}

	public int getNumButtons() {
		return buttonList.size();
	}

	public void setBackground(Image background) {
		this.background = background;
	}

	public void setBackground(String backgroundName) {
		background = ResourceManager.getInstance().images
				.getImage(backgroundName);
	}

	/**
	 * Select whether or not to tile the background. By default, the background
	 * is not tiled.
	 * 
	 * @param tile
	 */
	public void tileBackgroundImage(boolean tile) {
		tileBackground = tile;
	}

	public void setTitleImage(Image titleImage) {
		this.titleImage = titleImage;
	}

	public void setTitleImage(String titleImageName) {
		titleImage = ResourceManager.getInstance().images
				.getImage(titleImageName);
	}

	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public void paint(Graphics2D g) {
		if (background != null)
			g.drawImage(background, 0, 0, null);
		if (titleImage != null)
			g.drawImage(titleImage, titleXpos, titleYpos, null);
		for (Button b : buttonList) {
			b.paint(g);
		}
	}

	/**
	 * Orients all the buttons and the titleImage/titleString according to
	 * relative positions. Call this method after all modifications to
	 * MenuLayoutManager have been made.
	 */
	public void validate() {
		// First, display the titleImage/titleString
		int titleHeight = 0, titleWidth = 0;
		if (titleImage != null) {
			titleWidth = titleImage.getWidth(null);
			titleHeight = titleImage.getHeight(null);

			titleXpos = width / 2 - titleWidth / 2;
			titleYpos = (height / 2 - titleHeight) / 2;
			System.out.println("ypos:" + titleYpos);
		}

		// We need to know the information for the buttons before we can start
		// assigning any positions.
		int butWidth = buttonList.get(0).width;
		int butHeight = buttonList.get(0).height;

		// The total size for the inset, or spaces between buttons
		// int totalInset = (getNumButtons() - 1) * inset;
		// The total height = height of buttons + size of insets
		// int totalHeight = totalInset + getNumButtons() * butHeight;
		// One button + one inset that follows
		int buttAndInset = butHeight + inset;
		// Start y = the first button's ypos
		int startY = titleYpos + titleHeight + inset * 2;

		// Now, display buttons.
		for (int i = 0; i < buttonList.size(); i++) {
			System.out.println("Name:" + buttonList.get(i).name);
			// Xpos is center xpos of screen minus half the width of the button
			int xpos = width / 2 - butWidth / 2;

			// Ypos starts at half the height
			int ypos = height / 2;

			// Subtract by the position of the first button
			ypos = startY;

			// Add a button and and inset for each button
			ypos += buttAndInset * i;

			// Set the position
			buttonList.get(i).setPosition(xpos, ypos);
		}
	}

	public void update() {
		for(Button b: buttonList) {
			b.update();
		}
	}
}