package engine.map;

import java.awt.Graphics2D;

import engine.test.GlobalVars;

public class Grid {

	public final int tileLength = GlobalVars.tileLength,
			tileWidth = GlobalVars.tileWidth,
			tileHeight = GlobalVars.tileHeight;
	public Tile[][] tiles;
	public int xpos, ypos, gridX, gridY;
	public transient GridMap map;

	/**
	 * Create grid that includes a two-dimensional array of Tiles.
	 * 
	 * @param map
	 *            The specified GridMap.
	 * @param gridX
	 *            The x-coordinate of the grid on the GridMap.
	 * @param gridY
	 *            The y-coordinate of the grid on the GridMap.
	 */
	public Grid(GridMap map, int gridX, int gridY) {
		this.map = map;
		this.gridX = gridX;
		this.gridY = gridY;
		xpos = gridX * tileWidth * tileLength;
		ypos = gridY * tileHeight * tileLength;
		tiles = new Tile[tileLength][tileLength];
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles.length; y++)
				tiles[x][y] = new Tile(this, x * tileWidth, y * tileHeight);
	}

	public Grid(GridMap map, int gridX, int gridY, Tile[][] tiles) {
		this.map = map;
		this.gridX = gridX;
		this.gridY = gridY;
		xpos = gridX * tileWidth * tileLength;
		ypos = gridY * tileHeight * tileLength;
		this.tiles = tiles;
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles.length; y++) {
				tiles[x][y].grid = this;
			}
	}

	public Grid(String asString) {
		
	}

	@Override
	public String toString() {
		return null;
	}

	
	/**
	 * Iterates through the two-dimensional array of tiles and calls the
	 * function sprite.loadImage() for each one. This function checks to see if
	 * the sprite's imageName String has a name, and if it does, loads the image
	 * and sets it to its own.
	 */
	public void loadImages() {
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y].loadImages();
			}
	}
	
	/**
	 * Iterates through the two-dimensional array of tiles and calls the
	 * function sprite.loadImage() for each one. This function checks to see if
	 * the sprite's imageName String has a name, and if it does, loads the image
	 * and sets it to its own.
	 */
	public void setInfo() {
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y].loadImages();
				tiles[x][y].setInfo();
			}
	}

	/**
	 * Iterates through the two-dimensional array, draws a rectangle for each
	 * tile, and calls the tile's paint function.
	 * 
	 * @param g
	 */
	public void paint(Graphics2D g) {
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y].paintBase(g);
			}
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y].paintTerrain(g);
			}
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y].paintItem(g);
			}
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y].paintEntity(g);
			}
	}

	/**
	 * Iterates through a two-dimensional tile array and calls the update
	 * function for each tile.
	 */
	public void update() {
		for (int x = 0; x < tiles.length; x++)
			for (int y = 0; y < tiles[0].length; y++)
				tiles[x][y].update();
	}
}
