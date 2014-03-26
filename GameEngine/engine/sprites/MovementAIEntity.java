package engine.sprites;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.ai.AStarPathFinder;
import engine.ai.Mover;
import engine.ai.Path;
import engine.map.Tile;

public class MovementAIEntity extends Entity implements Mover {

	public AStarPathFinder finder;
	public Path path;

	public MovementAIEntity(String name, Tile tile) {
		super(name, tile);
		finder = new AStarPathFinder(myTile.grid.map, 500, false);
		classType = MovementAIEntity.class;
	}

	public int getCurrentStepDirection() {
		// get the next step necessary
		int currentX = path.getX(1);
		int currentY = path.getY(1);
		// return the direction needed
		if (currentX < tileX)
			return LEFT;
		if (currentX > tileX)
			return RIGHT;
		if (currentY > tileY)
			return DOWN;
		if (currentY < tileY)
			return UP;
		return STILL;
	}

	public void update() {
		if (getMap().world.player.gridX == gridX
				&& getMap().world.player.gridY == gridY)
			path = finder.findPath(this, gridX, gridY, tileX, tileY,
					getMap().world.player.tileX, getMap().world.player.tileY);
		if (path != null)
			movement = getCurrentStepDirection();
		moveTimer();
		getItem();
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect((int) xpos, (int) ypos, 32, 32);
		for (int x = 0; x < myTile.grid.map.getWidthInTiles(); x++)
			for (int y = 0; y < myTile.grid.map.getHeightInTiles(); y++)
				if (path != null && path.contains(x, y)) {
					g.setColor(Color.blue);
					g.fillRect((x * 32) + 8, (y * 32) + 8, 14, 14);
				}
	}
}
