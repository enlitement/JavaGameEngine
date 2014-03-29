package engine.sprites;

import java.awt.Graphics2D;

import engine.core.JGE;
import engine.map.Tile;
import engine.objects.GameObject;
import engine.utilities.ImageManipulator;

public class Sprite extends GameObject {

	public transient Tile myTile;
	public boolean solid, isItem;
	public int TYPE, tileX, tileY, gridX, gridY, flip;
	public double rotation;
	public String imageName;
	public Class<? extends Sprite> classType;

	public Sprite(String name, Tile tile) {
		this.name = name;
		myTile = tile;
		if (tile != null) {
			xpos = tile.xpos;
			ypos = tile.ypos;
		}
		solid = false;
		isItem = false;

	}

	public Sprite(String name) {
		this.name = name;
		myTile = null;
		solid = false;
		isItem = false;
	}

	public Sprite() {
		solid = false;
		isItem = false;
	}

	public void paint(Graphics2D g) {
		g.drawImage(image, (int) xpos, (int) ypos, null);
	}

	public void paint(Graphics2D g, int x, int y) {
		g.drawImage(image, (int) xpos + x, (int) ypos + y, null);
	}

	public void update() {

	}

	public void setImage(String imageName) {
		this.imageName = imageName;
		image = JGE.LOAD_IMAGE("images/" + imageName);
		if (rotation > 0)
			image = ImageManipulator.rotate(image, rotation);
	}

	public void loadImage() {
		if (imageName != null)
			image = JGE.LOAD_IMAGE("images/" + imageName);
		if (rotation > 0)
			image = ImageManipulator.rotate(image, rotation);
	}

	public void setTile(Tile tile) {
		if (myTile != null)
			myTile.removeSprite(this);
		myTile = tile;
		myTile.addSprite(this);
		tileX = tile.xpos / 32;
		tileY = tile.ypos / 32;
		gridX = tile.grid.gridX;
		gridY = tile.grid.gridY;
		xpos = tile.xpos + tile.grid.xpos;
		ypos = tile.ypos + tile.grid.ypos;
	}
}
