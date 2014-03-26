package engine.sprites;

import engine.ai.Mover;
import engine.animation.Animation;
import engine.editor.Inventory;
import engine.map.Grid;
import engine.map.GridMap;
import engine.map.Tile;
import engine.test.GlobalVars;

public class Entity extends Sprite implements Mover {

	private long timeMoved;
	private boolean timerStarted = false;
	public int movement, direction, dx, dy, health, max_health, mana, max_mana;
	public static final int STILL = 0, LEFT = 1, UP = 2, RIGHT = 3, DOWN = 4,
			REPEAT_DELAY = 20, WIDTH = GlobalVars.tileWidth,
			HEIGHT = GlobalVars.tileHeight;

	public Inventory inventory;
	public Animation animation;

	public Entity(String name, Tile tile) {
		super(name, tile);
		health = 100;
		max_health = 100;
		mana = 0;
		max_mana = 10;

		dx = 0;
		dy = 0;
		movement = STILL;
		direction = STILL;

		solid = true;
		TYPE = Tile.ENTITY;

		if (tile != null)
			setTile(tile);

		inventory = new Inventory(this, 2, 2);
		classType = Entity.class;
	}

	public Entity() {
		this("Entity", null);
	}

	private void movementAnimation(int direction) {
		Tile tile = getTileInDirection(direction);
		if (tile != null && !tile.solid)
			move(tile);
	}

	public void move(Tile tile) {
		switch (direction) {
		case UP:
			dy = -1;
			if (tile.ypos < ypos)
				ypos += dy;
			else {
				setTile(tile);
				dy = 0;
				movement = STILL;

			}
			break;
		case DOWN:
			dy = 1;
			if (tile.ypos > ypos)
				ypos += dy;
			else {
				setTile(tile);
				dy = 0;
				movement = STILL;

			}
			break;
		case LEFT:
			dx = -1;
			if (tile.xpos < xpos)
				xpos += dx;
			else {
				setTile(tile);
				dx = 0;
				movement = STILL;

			}
			break;
		case RIGHT:
			dx = 1;
			if (tile.xpos > xpos)
				xpos += dx;
			else {
				setTile(tile);
				dx = 0;
				movement = STILL;

			}
			break;
		}
	}

	public void getItem() {
		if (myTile.hasItem() != null) {
			myTile.hasItem().owner = this;
			inventory.addSprite(myTile.hasItem());
			myTile.item = null;
		}
	}

	public Tile getTileInDirection(int direction) {
		switch (direction) {
		case LEFT:
			if ((tileX == 0) && (gridX != 0))
				return getGridArray()[gridX - 1][gridY].tiles[getTileLength() - 1][tileY];
			else if (tileX != 0)
				return getGridArray()[gridX][gridY].tiles[tileX - 1][tileY];

			break;

		case UP:
			if (tileY == 0 && (gridY != 0))
				return getGridArray()[gridX][gridY - 1].tiles[tileX][getTileLength() - 1];
			else if (tileY != 0)
				return getGridArray()[gridX][gridY].tiles[tileX][tileY - 1];

			break;
		case RIGHT:
			if (tileX == getTileLength() - 1 && gridX != getGridWidth() - 1)
				return getGridArray()[gridX + 1][gridY].tiles[0][tileY];
			else if (tileX != getTileLength() - 1)
				return getGridArray()[gridX][gridY].tiles[tileX + 1][tileY];

			break;

		case DOWN:
			if (tileY == getTileLength() - 1 && (gridY != getGridHeight() - 1))
				return (getGridArray()[gridX][gridY + 1].tiles[tileX][0]);
			else if (tileY != getTileLength() - 1)
				return (getGridArray()[gridX][gridY].tiles[tileX][tileY + 1]);
			break;
		case STILL:
			return null;
		}
		return null;
	}

	public void moveTimer() {
		if (direction != STILL && !timerStarted) {
			timeMoved = System.currentTimeMillis();
			timerStarted = true;
			movement = direction;
		}
		animation.loop();
		//if (movement != STILL)
			movementAnimation(movement);
		long currentTime = System.currentTimeMillis();
		if (currentTime - REPEAT_DELAY > timeMoved) {
			timerStarted = false;
		}
	}

	public void update() {
		moveTimer();
	}

	public int getTileLength() {
		return getGrid().tiles.length;
	}

	public int getGridWidth() {
		return getGridArray().length;
	}

	public int getGridHeight() {
		return getGridArray()[0].length;
	}

	public Grid getGrid() {
		return myTile.grid;
	}

	public Grid[][] getGridArray() {
		return myTile.grid.map.grids;
	}

	public GridMap getMap() {
		return myTile.grid.map;
	}
}
