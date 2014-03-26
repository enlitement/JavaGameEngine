package engine.map;

import java.awt.Graphics2D;

import engine.sprites.Entity;
import engine.sprites.Item;
import engine.sprites.Sprite;
import engine.sprites.Terrain;
import engine.test.GlobalVars;

public class Tile {

	public boolean solid;
	public int xpos, ypos;
	public final int WIDTH = GlobalVars.tileWidth,
			HEIGHT = GlobalVars.tileHeight;
	public final static int BASE = 0, TERRAIN = 1, ITEM = 2, ENTITY = 3;
	public transient Grid grid;
	public Entity entity;
	public Terrain base, terrain;
	public Item item;

	/**
	 * Creates a single tile, which has 4 TileLayers that contain sprites.
	 * 
	 * @param grid
	 *            The specified grid.
	 * @param xpos
	 *            The x-coordinate of the tile.
	 * @param ypos
	 *            The y-coordinate of the tile.
	 */
	public Tile(Grid grid, int xpos, int ypos) {
		this.grid = grid;
		this.xpos = xpos;
		this.ypos = ypos;
		solid = false;
	}

	/**
	 * Add a sprite to this tile. For in game purposes.
	 * 
	 * @param sprite
	 */
	public void addSprite(Sprite sprite) {
		if (sprite instanceof Entity) {
			entity = (Entity) sprite;
			solid = true;
			return;
		}
		if (sprite instanceof Item) {
			item = (Item) sprite;
			return;
		}
		if (sprite instanceof Terrain) {
			terrain = (Terrain) sprite;
			return;
		}
	}

	/**
	 * For Editor purposes only.
	 * 
	 * @param sprite
	 */
	public void setTile(Sprite sprite) {
		if (sprite instanceof Entity) {
			try {
				entity = (Entity) sprite.getClass().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			solid = true;
			updateSpriteInfo(entity);
		} else if (sprite instanceof Item) {
			try {
				item = (Item) sprite.getClass().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			updateSpriteInfo(item);
			return;
		} else if (sprite instanceof Terrain && sprite.TYPE == Tile.BASE) {
			base = new Terrain();
			base.image = sprite.image;
			base.imageName = sprite.imageName;
			base.solid = sprite.solid;
			updateSpriteInfo(base);
			return;
		} else if (sprite instanceof Terrain && sprite.TYPE == Tile.TERRAIN) {
			terrain = new Terrain();
			terrain.image = sprite.image;
			terrain.imageName = sprite.imageName;
			terrain.solid = sprite.solid;
			updateSpriteInfo(terrain);
			return;
		}
	}

	public void updateSpriteInfo(Sprite sprite) {
		sprite.myTile = this;
		sprite.loadImage();
		sprite.xpos = xpos;
		sprite.ypos = ypos;
		sprite.tileX = xpos / WIDTH;
		sprite.tileY = ypos / HEIGHT;
		sprite.gridX = grid.gridX;
		sprite.gridY = grid.gridY;
		if (sprite.solid)
			solid = true;
	}

	/**
	 * Clears the Tile's 4 TileLayer objects from any sprite.
	 */
	public void clear() {
		base = null;
		terrain = null;
		item = null;
		entity = null;
	}

	/**
	 * Searches for the specified sprite within the TileLayers. If it's found,
	 * it is set to null and the Tile's solid status is updated if necessary.
	 * 
	 * @param sprite
	 */
	public void removeSprite(Sprite sprite) {
		if (sprite == entity)
			entity = null;
		if (sprite == item)
			item = null;
		if (sprite == base)
			base = null;
		if (sprite == terrain)
			terrain = null;
		if (sprite.solid)
			solid = false;

	}

	/**
	 * Checks to see if TileLayer[ITEM] has an item.
	 * 
	 * @return The item if it is found, null if not.
	 */
	public Item hasItem() {
		return item;
	}

	public void paintEntity(Graphics2D g) {
		if (entity != null)
			entity.paint(g);
	}

	public void paintBase(Graphics2D g) {
		if (base != null)
			base.paint(g);
	}

	public void paintTerrain(Graphics2D g) {
		if (terrain != null)
			terrain.paint(g);
	}

	public void paintItem(Graphics2D g) {
		if (item != null)
			item.paint(g);
	}

	/**
	 * Iterates through the TileLayers and calls their paint function that
	 * specifies an increase in drawing position.
	 * 
	 * @param g
	 *            The Graphics2D object
	 * @param x_increase
	 *            Specified x increase.
	 * @param y_increase
	 *            Specified y increase.
	 */
	public void paintImages(Graphics2D g, int x_increase, int y_increase) {
		if (base != null && base.image != null)
			g.drawImage(base.image, (int) xpos + x_increase, (int) ypos
					+ y_increase, null);

		if (terrain != null && terrain.getImage() != null)
			g.drawImage(terrain.getImage(), (int) xpos + x_increase, (int) ypos
					+ y_increase, null);

		if (item != null && item.image != null)
			g.drawImage(item.image, (int) xpos + x_increase, (int) ypos
					+ y_increase, null);

		if (entity != null && entity.image != null)
			g.drawImage(entity.image, (int) xpos + x_increase, (int) ypos
					+ y_increase, (int) xpos + x_increase + Entity.WIDTH,
					(int) ypos + y_increase + Entity.HEIGHT, 0, 0,
					Entity.WIDTH, Entity.HEIGHT, null);

	}

	/**
	 * Iterates through the TileLayer array and calls each one's update function
	 * if it has a sprite.
	 */
	public void update() {

		if (base != null)
			base.update();
		if (terrain != null)
			terrain.update();
		if (item != null)
			item.update();
		if (entity != null)
			entity.update();
	}

	public void loadImages() {
		if (base != null)
			base.loadImage();
		if (terrain != null)
			terrain.loadImage();
		if (item != null)
			item.loadImage();
		if (entity != null)
			entity.loadImage();
	}

	public void setInfo() {
		if (base != null)
			base.myTile = this;
		if (terrain != null)
			terrain.myTile = this;
		if (item != null)
			item.myTile = this;
		if (entity != null)
			entity.myTile = this;
	}
}
