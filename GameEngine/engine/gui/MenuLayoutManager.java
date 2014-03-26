package engine.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import engine.core.JGE;
import engine.core.Screen;
import engine.graphics.GraphicsManager;
import engine.rooms.Room;
import engine.utilities.TextUtilities;

public class MenuLayoutManager {
	
	public Room room;

	public int width, height;
	public int buttonX, buttonY;

	// Size between buttons
	public final int inset = 10;
	public List<AbstractButton> buttonList;

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
		buttonList = new ArrayList<AbstractButton>();
		width = Screen.WIDTH;
		height = Screen.HEIGHT;

		tileBackground = false;
		titleFont = new Font("Dialog", Font.BOLD, 50);
	}

	public void addButton(AbstractButton button) {
		buttonList.add(button);
	}

	public int getNumButtons() {
		return buttonList.size();
	}

	public void setBackground(Image background) {
		this.background = background;
	}

	public void setBackground(String backgroundName) throws IOException {
		background = JGE.LOAD_IMAGE(backgroundName);
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

	public void setTitleImage(String titleImageName) throws IOException {
		titleImage = JGE.LOAD_IMAGE(titleImageName);
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
			g.setColor(Color.white);
			g.setFont(titleFont);
			g.drawString(titleString,titleXpos + 20,titleYpos);
		}
		for (AbstractButton b : buttonList)
			b.paint(g);
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
			titleWidth = (int) (TextUtilities.getFontWidth(
					titleFont, titleString)*(titleFont.getSize()-3)/10);
			//System.out.println("Width:"+titleWidth);
			titleHeight = TextUtilities
					.getFontHeight(titleFont);

			titleXpos = width / 2 - titleWidth / 2;
			titleYpos = (height / 2 - titleHeight) / 2;
		}

		// We need to know the information for the buttons before we can start
		// assigning any positions.
		int butWidth = buttonList.get(0).getWidth();
		int butHeight = buttonList.get(0).getHeight();

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
			startY = Screen.HEIGHT/2 - totalHeight / 2;

		// Now, display buttons.
		for (int i = 0; i < buttonList.size(); i++) {
			// Xpos is center xpos of screen minus half the width of the button
			int xpos = width / 2 - butWidth / 2;

			// Subtract by the position of the first button
			int ypos = startY;

			// Add a button and and inset for each button
			ypos += buttAndInset * i;
			
			// Set the position
			buttonList.get(i).setPosition(xpos, ypos);
		}
	}
	
	public void update() {
		for(AbstractButton b : buttonList)
			b.update();
	}
}