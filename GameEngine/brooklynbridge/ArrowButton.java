package brooklynbridge;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;

public class ArrowButton extends engine.gui.Button {

	public double rotation;

	public int position = 0;
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3, FORWARD = 4,
			FORWARDLEFT = 5, FORWARDRIGHT = 6, TOPLEFT = 7, TOPRIGHT = 8;

	public int face;
	public static final int fFace = 0, rFace = 1, bFace = 2, lFace = 3,
			allFace = 4, fbFace = 5, topFace = 6, medFace = 7;

	public Image image;
	public PhotoRoom room;

	public ArrowButton(Sandbox sandbox, PhotoRoom room, String text) {
		super(sandbox, text);
		this.room = room;
		//image = ResourceManager.get().images.getImage("/beginning/button0.gif");
		width = 53;
		height = 53;
		rec = new Rectangle((int) xpos, (int) ypos, width, height);
		rotation = 0;
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(image, (int) xpos, (int) ypos, null);
		// g.rotate(rotation);
	}

	@Override
	public void highlightEffect() {
		if (position != FORWARD) {
			image = ResourceManager.get().images
					.getImage("beginning/buttonhover"+position+".gif");
		}
	}

	@Override
	public void enableClickEffect() {
		if (position != FORWARD) {
			image = ResourceManager.get().images
					.getImage("beginning/buttonpressed"+position+".gif");
		}
	}

	@Override
	public void revertClickEffect() {
		if (position != FORWARD) {
			image = ResourceManager.get().images
					.getImage("beginning/button"+position+".gif");
		}
	}

	@Override
	public void update() {
		processInput();
		clickEffects();
	}
}
