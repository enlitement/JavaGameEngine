package engine.sprites;

import java.awt.Graphics2D;

import engine.map.Tile;

public class Item extends Sprite {
	
	public Entity owner;
	
	public Item(String name, Tile tile) {
		super(name, tile);
		myTile = tile;
		isItem = true;
		TYPE = Tile.ITEM;
		owner = null;
		classType = Item.class;
	}
	
	public Item(String name) {
		super(name);
		isItem = true;
		TYPE = Tile.ITEM;
		owner = null;
		classType = Item.class;
	}

	public Item() {
		isItem = true;
		TYPE = Tile.ITEM;
		owner = null;
		classType = Item.class;
	}

	public void paint(Graphics2D g) {
		if (image != null)
			g.drawImage(image, (int) xpos, (int) ypos, null);
	}
}
