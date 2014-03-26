package engine.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.core.Screen;
import engine.objects.GameObject;

public class Toolbar extends GameObject {

	private ArrayList<Icon> icons;
	private Color body_color;
	public static final int TOP = 1, BOTTOM = 2, LEFT = 3, RIGHT = 4;
	private int orientation;
	private final int marginY = 4, marginX = 1;

	public Toolbar(int orientation) {
		icons = new ArrayList<Icon>();
		setOrientation(orientation);
		body_color = Color.gray;
	}

	public void addIcon(Icon icon) {
		icons.add(icon);
		validate();
	}

	public int getLargestHeight() {
		int height = 0;
		for (int i = 0; i < icons.size(); i++)
			if (icons.get(i).getHeight() > height)
				height = icons.get(i).getHeight();
		if (height == 0)
			return Icon.DEF_ICON_H;
		return height;
	}

	public int getLargestWidth() {
		int width = 0;
		for (int i = 0; i < icons.size(); i++)
			if (icons.get(i).getWidth() > width)
				width = icons.get(i).getWidth();
		if (width == 0)
			return Icon.DEF_ICON_WID;
		return width;
	}

	public void validate() {
		setOrientation(orientation);
		int width = 0;
		for (int i = 0; i < icons.size(); i++) {
			if (orientation == TOP) {
				// icons.get(i).setX(
				// getX() + marginX + getLargestWidth() * i + marginX * i);
				icons.get(i).setX(getX() + width + marginX);
				icons.get(i).setY(getY() + marginY);
			}
			if (orientation == RIGHT) {
				icons.get(i).setX(getX() + marginX);
				icons.get(i).setY(
						getY() + marginY + getLargestWidth() * i + marginY * i);
			}
			if (orientation == LEFT) {
				icons.get(i).setX(getX() + marginX);
				icons.get(i).setY(
						getY() + marginY + getLargestWidth() * i + marginY * i);
			}
			if (orientation == BOTTOM) {
				icons.get(i).setX(
						getX() + marginX + getLargestWidth() * i + marginX * i);
				icons.get(i).setY(getY() + marginX);
			}
			width += icons.get(i).getWidth();
		}
	}

	public void setOrientation(int orientation) {
		if (orientation < 1 || orientation > 4)
			throw new IllegalArgumentException(
					"Specify an orientation: TOP, BOTTOM, LEFT, or RIGHT");
		this.orientation = orientation;
		orientToolbar();
	}

	private void orientToolbar() {
		switch (orientation) {
		case TOP:
			setX(0);
			setY(0);
			setWidth(Screen.WIDTH);
			setHeight(getLargestHeight() + marginY * 2);
			break;
		case RIGHT:
			setX(Screen.WIDTH - marginX * 2 - getLargestWidth());
			setY(0);
			setWidth(getLargestWidth() + marginX * 2);
			setHeight(Screen.HEIGHT);
			break;
		case LEFT:
			setX(0);
			setY(0);
			setWidth(getLargestWidth() + marginX * 2);
			setHeight(Screen.HEIGHT);
			break;
		case BOTTOM:
			setX(0);
			setY(Screen.HEIGHT - marginY * 2 - getLargestHeight() - 100);
			setWidth(Screen.WIDTH);
			setHeight(getLargestHeight() + marginY * 2);
			break;
		}
	}

	public void setBodyColor(Color color) {
		this.body_color = color;
	}

	@Override
	public void paint(Graphics2D g) {
		if (getImage() != null)
			g.drawImage(getImage(), (int) getX(), (int) getY(), getWidth(),
					getHeight(), null);
		else {
			g.setColor(body_color);
			g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
		}

		for (int i = 0; i < icons.size(); i++) {
			icons.get(i).paint(g);
		}
	}
}
