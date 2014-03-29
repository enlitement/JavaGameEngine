package engine.map;

import java.awt.Graphics2D;

import engine.ai.Mover;
import engine.ai.TileBasedMap;
import engine.test.World;
import engine.utilities.JSONReader;

public class GridMap implements TileBasedMap {

	public Grid[][] grids;
	public World world;
	public int gridX, gridY;
	public boolean[][] visited;

	public GridMap(World world) {
		this.world = world;
		gridX = 2;
		gridY = 1;
		grids = new Grid[gridX][gridY];
		for (int x = 0; x < grids.length; x++)
			for (int y = 0; y < gridY; y++) {
				grids[x][y] = JSONReader.loadGrid(x+""+y+".json", true);
				grids[x][y].gridX = x;
				grids[x][y].gridY = y;
				grids[x][y].map = this;
				grids[x][y].setInfo();
			}
		visited = new boolean[getWidthInTiles()][getHeightInTiles()];
	}

	public void paint(Graphics2D g) {
		grids[world.player.gridX][world.player.gridY].paint(g);
	}

	@Override
	public int getWidthInTiles() {
		return grids[0][0].tiles.length;
	}

	@Override
	public int getHeightInTiles() {
		return grids[0][0].tiles[0].length;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}

	@Override
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		return 1;
	}

	@Override
	public boolean blocked(Mover mover, int gx, int gy, int tx, int ty) {
		return false;
		// return grids[gx][gy].tiles[tx][ty].solid;
	}

	@Override
	public int getGridWidth() {
		return gridX;
	}

	@Override
	public int getGridHeight() {
		return gridY;
	}

	public void update() {
		grids[world.player.gridX][world.player.gridY].update();
	}
}