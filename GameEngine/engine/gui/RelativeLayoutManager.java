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

public class RelativeLayoutManager extends AbstractLayoutManager{

	public Room room;

	public int width, height;
	public int buttonX, buttonY;

	// Size between buttons
	public final int inset = 10;
	public List<RelativeLayoutSpecification> specList;

	// Or just a plain String
	public Font titleFont;
	// This position defines a position for either the
	// image or string.
	public int titleXpos, titleYpos;

	// The menu can have a background
	public Image background;
	public boolean tileBackground;

	public RelativeLayoutManager(Room room) {
		super(room);
		specList = new ArrayList<RelativeLayoutSpecification>();
		width = Screen.WIDTH;
		height = Screen.HEIGHT;

		tileBackground = false;
		titleFont = new Font("Dialog", Font.BOLD, 50);
	}

	public void addSpecification(
			RelativeLayoutSpecification relativeLayoutSpecification) {
		specList.add(relativeLayoutSpecification);
	}

	public int getSpecificationSize() {
		return specList.size();
	}

	public void setBackground(Image background) {
		this.background = background;
	}

	public void setBackground(String backgroundName) throws IOException {
		background = JGE.LOAD_IMAGE(backgroundName);
	}

	public void paint(Graphics2D g) {
		if (background != null)
			g.drawImage(background, 0, 0, null);
		for (RelativeLayoutSpecification spec : specList)
			spec.getButton().paint(g);
	}

	public void update() {
		for (RelativeLayoutSpecification spec : specList)
			spec.getButton().update();
	}

	@Override
	public void arrange() {
		
		
	}
}
