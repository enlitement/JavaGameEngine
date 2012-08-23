package engine.gui;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import engine.core.Game;
import engine.core.graphics.GraphicsManager;
import engine.core.resources.ResourceManager;
import engine.rooms.Room;

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
	public Font titleFont;
	// This position defines a position for either the
	// image or string.
	public int titleXpos, titleYpos;

	// The menu can have a background
	public Image background;
	public boolean tileBackground;

	public MenuLayoutManager(Room room) {
		this.room = room;
		buttonList = new ArrayList<Button>();
		width = Game.WIDTH;
		height = Game.HEIGHT;

		tileBackground = false;
		titleFont = new Font("Dialog", Font.BOLD, 50);
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
		background = ResourceManager.get().images
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
		titleImage = ResourceManager.get().images
				.getImage(titleImageName);
	}

	/**
	 * Specify font and add a title for the menu.
	 * 
	 * @param font
	 *            Title font
	 * @param titleString
	 *            Title
	 */
	public void setTitleString(Font font, String titleString) {
		this.titleFont = font;
		this.titleString = titleString;
	}

	/**
	 * Use default title font and set a title for the menu.
	 * 
	 * @param titleString
	 *            Title
	 */
	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}

	public void paint(Graphics2D g) {
		if (background != null)
			g.drawImage(background, 0, 0, null);
		if (titleImage != null)
			g.drawImage(titleImage, titleXpos, titleYpos, null);
		if (titleImage == null && titleString != null) {
			g.setFont(titleFont);
			g.drawString(titleString,titleXpos,titleYpos);
		}
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
		// First, display the titleImage or titleString
		int titleHeight = 0, titleWidth = 0;
		if (titleImage != null) {
			titleWidth = titleImage.getWidth(null);
			titleHeight = titleImage.getHeight(null);

			//(int) (xpos + (width/2) - (textWidth/2), (int) (
			//ypos+height/2);
			
			titleXpos = (width / 2) - (titleWidth / 2);
			titleYpos = (height / 2 - titleHeight) / 2;
		} 
		// If there is no image available, see if text is available
		else if (titleImage == null && titleString != null) {
			// TODO: What's up with titleWidth calculation?
			titleWidth = (int) (GraphicsManager.get().textManager.getFontWidth(
					titleFont, titleString)*(titleFont.getSize()-3)/10);
			//System.out.println("Width:"+titleWidth);
			titleHeight = GraphicsManager.get().textManager
					.getFontHeight(titleFont);

			titleXpos = width / 2 - titleWidth / 2;
			titleYpos = (height / 2 - titleHeight) / 2;
		}

		// We need to know the information for the buttons before we can start
		// assigning any positions. NOTE: ASSUMES THAT YOU ARE USING MenuButtons
		int butWidth = buttonList.get(0).width;
		int butHeight = buttonList.get(0).height;

		// The total size for the inset, or spaces between buttons
		int totalInset = (getNumButtons() - 1) * inset;
		// The total height = height of buttons + size of insets
		int totalHeight = totalInset + getNumButtons() * butHeight;
		// One button + one inset that follows
		int buttAndInset = butHeight + inset;
		// Start y = the first button's ypos
		int startY = 0;
		if (titleHeight != 0)
			startY = titleYpos + titleHeight + inset * 2;
		else
			startY = Game.HEIGHT - totalHeight / 2;

		// Now, display buttons.
		for (int i = 0; i < buttonList.size(); i++) {
			//System.out.println("Name:" + buttonList.get(i).name);
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
		for (Button b : buttonList) {
			b.update();
		}
	}
}