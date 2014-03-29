package brooklynbridge;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import engine.core.Game;
import engine.core.resources.ResourceManager;
import engine.gui.Button;
import engine.rooms.Room;

public class ArrowButtonLayoutManager {
	public Room room;

	public int width, height;
	public int buttonX, buttonY;

	// Size between buttons
	public final int inset = 10;
	public List<ArrowButton> buttonList;

	public ArrowButtonLayoutManager(Room room) {
		this.room = room;
		buttonList = new ArrayList<ArrowButton>();
		width = Game.WIDTH;
		height = Game.HEIGHT;
	}

	public void addButton(ArrowButton button) {
		buttonList.add(button);
	}

	public int getNumButtons() {
		return buttonList.size();
	}

	public void paint(Graphics2D g) {
		for (ArrowButton b : buttonList) {
			// If the button's face is appropriate to the current room face
			if (b.face == b.room.face
					// the button is required by all faces
					|| b.face == ArrowButton.allFace
					// if the buttons is a forward/backward button and the
					// current room face is forward/backward, draw the button
					|| ((b.room.face == ArrowButton.fFace || b.room.face == ArrowButton.bFace) && b.face == ArrowButton.fbFace)
					// If it's a top face only button, ex. Down button
					|| (b.room.face > 7 && b.face == ArrowButton.topFace)
					// If it's a middle/lower level face
					|| (b.face == ArrowButton.medFace
							&& b.room.face >= ArrowButton.fFace && b.room.face <= ArrowButton.lFace))
				b.paint(g);
		}
	}

	/**
	 * Orients all the buttons and the titleImage/titleString according to
	 * relative positions. Call this method after all modifications to
	 * MenuLayoutManager have been made.
	 */

	public void vadlidate() {
		// We need to know the information for the buttons before we can start
		// assigning any positions.
		int butWidth = buttonList.get(0).width;
		int arrowButtonWidth = 53;
		int arrowButtonHeight = 53;
		// Now, display buttons.
		for (int i = 0; i < buttonList.size(); i++) {
			switch (buttonList.get(i).position) {
			case ArrowButton.UP:
				buttonList.get(i).xpos = width / 2 - butWidth / 2;
				buttonList.get(i).ypos = inset;
				// buttonList.get(i).rotation = Math.PI/2;
				buttonList.get(i).renewPosition();
				break;
			case ArrowButton.RIGHT:
				buttonList.get(i).xpos = width - inset - butWidth;
				buttonList.get(i).ypos = height / 2 - butWidth / 2 - inset;
				buttonList.get(i).renewPosition();
				break;
			case ArrowButton.DOWN:
				buttonList.get(i).xpos = width / 2 - butWidth / 2;
				buttonList.get(i).ypos = height - arrowButtonHeight - inset;
				buttonList.get(i).renewPosition();
				break;
			case ArrowButton.LEFT:
				buttonList.get(i).xpos = inset;
				buttonList.get(i).ypos = height / 2 - butWidth / 2;
				// buttonList.get(i).rotation = Math.PI;
				buttonList.get(i).renewPosition();
				break;
			case ArrowButton.FORWARD:
				buttonList.get(i).xpos = width / 2 - arrowButtonWidth / 2;
				buttonList.get(i).ypos = 2 * height / 3 - arrowButtonHeight / 2;
				buttonList.get(i).image = ResourceManager.get().images
						.getImage("forward.gif");
				buttonList.get(i).renewPosition();
				break;
			case ArrowButton.FORWARDLEFT:
				buttonList.get(i).xpos = width / 2 / 2 - arrowButtonWidth / 2;
				buttonList.get(i).ypos = height / 2 - arrowButtonHeight / 2;
				buttonList.get(i).renewPosition();
				break;
			case ArrowButton.FORWARDRIGHT:
				buttonList.get(i).xpos = width / 2 + width / 4
						+ arrowButtonWidth / 2;
				buttonList.get(i).ypos = height / 2 - arrowButtonHeight / 2;
				buttonList.get(i).renewPosition();
				break;
			case ArrowButton.TOPLEFT:
				buttonList.get(i).xpos = inset;
				buttonList.get(i).ypos = inset;
				buttonList.get(i).renewPosition();
				break;
			case ArrowButton.TOPRIGHT:
				buttonList.get(i).xpos = width - inset - arrowButtonWidth;
				buttonList.get(i).ypos = inset;
				buttonList.get(i).renewPosition();
				break;

			}
		}
	}

	public void update() {
		for (Button b : buttonList) {
			b.update();
		}
	}
}
