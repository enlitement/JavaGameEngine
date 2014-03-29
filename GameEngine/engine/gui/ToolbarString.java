package engine.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.events.EventDispatcher;
import engine.events.JMouseListener;
import engine.events.MouseMoveEvent;
import engine.objects.GameObject;
import engine.utilities.TextUtilities;
import engine.utilities.Utilities;

public class ToolbarString extends Icon {

	private boolean inSelection = false;
	private ArrayList<GameObject> menu_items;
	public int margin = 3;
	private int mouseX, mouseY;

	public ToolbarString(String text) {
		super(text);
		EventDispatcher.addObserver(this, new MouseMoveEvent(this),
				new JMouseListener() {

					@Override
					public void mouseReleased(MouseMoveEvent e) {

					}

					@Override
					public void mousePressed(MouseMoveEvent e) {
						mouseX = e.getXpos();
					}

					@Override
					public void mouseMoved(MouseMoveEvent e) {
						mouseY = e.getYpos();
					}
				});
		menu_items = new ArrayList<GameObject>();
		setWidth(TextUtilities.getFontWidth(font, getName()) + margin * 2);
		setHeight(TextUtilities.getFontHeight(font) + margin);
		// /System.out.println("Width:"+getWidth());
		setNormColor(Color.gray);
		setHoverColor(Color.white);
		setOnPressColor(Color.DARK_GRAY);
	}

	public void addMenuItem() {
		
	}
	private void drawMenuItems(Graphics2D g) {
		
	}
	
	public void checkInSelection() {
		
	}
	@Override
	public void paint(Graphics2D g) {

		if (isPressed() || inSelection) {
			inSelection = true;
			Utilities
					.drawTwoColorBox(g, (int) getX(), (int) getY(), getWidth(),
							getHeight(), getOnPressColor(), getHoverColor());
		} else if (isHover())
			Utilities
					.drawTwoColorBox(g, (int) getX(), (int) getY(), getWidth(),
							getHeight(), getHoverColor(), getOnPressColor());
		g.setColor(Color.black);
		TextUtilities.drawCenteredText(font, getName(), getX(), getY(),
				getWidth(), getHeight());
	}
}