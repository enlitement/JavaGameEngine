package engine.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

import engine.core.graphics.GraphicsManager;
import engine.gui.Button2;

public class GradientButton extends Button2 {

	public Color CRISP_BLACK, CRISP_GREY, NEON_BLUE, LIGHT_GREY;
	//public GameGUI gui;
	
	public GradientButton(String text) {
		super(text);
		//this.gui = gui;
		CRISP_BLACK = new Color(34, 34, 34);
		CRISP_GREY = new Color(70, 70, 70);
		NEON_BLUE = new Color(91, 180, 249);
		LIGHT_GREY = new Color(180, 180, 180);
	}

	public void paint(Graphics2D g) {
		GradientPaint gradient = new GradientPaint((int) (xpos + width / 2),
				(int) ypos, CRISP_BLACK, (int) (xpos + width / 2), (int) ypos
						+ height + 20, CRISP_GREY, true);

		// Draw gradient
		g.setPaint(gradient);
		g.fillRect((int) xpos, (int) ypos, width, height);

		// Draw outline
		g.setColor(NEON_BLUE);
		GraphicsManager.get().drawRectOutline(g, (int) xpos, (int) ypos, width,
				height, 4);

		// Draw text
		g.setColor(LIGHT_GREY);
		g.setFont(font);
		GraphicsManager.get().textManager.drawCenteredText(font, name, xpos,
				ypos, width, height);

	}
}
