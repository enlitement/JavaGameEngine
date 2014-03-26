package engine.editor;

import java.util.ArrayList;

import engine.map.Tile;
import engine.sprites.HealthPot;
import engine.sprites.Player;
import engine.sprites.Sprite;
import engine.sprites.Terrain;

public class ListOfSprites {
	public ArrayList<Sprite> sprites;

	public ListOfSprites() {
		sprites = new ArrayList<Sprite>();

		HealthPot pot = new HealthPot("Health Potion");
		Terrain grass = new Terrain("Grass", "Grass.png");
		Terrain grass_and_sand = new Terrain("GrassSand", "GrassSandLeft.png");
		Terrain brick = new Terrain("Brick", "Brick.png");
		brick.solid = true;
		Terrain house = new Terrain("House", "House.png");
		house.TYPE = Tile.TERRAIN;
		house.solid = true;
		Terrain limestone = new Terrain("Limestone", "Limestone.png");
		Terrain sand = new Terrain("Sand", "Sand.png");
		Terrain water = new Terrain("Water", "Water.png");
		water.TYPE = Tile.TERRAIN;
		Player player = new Player("Player",null);
		Terrain trees = new Terrain("Trees", "Trees.png");
		trees.TYPE = Tile.TERRAIN;
		trees.solid = true;
		Terrain treesFinish = new Terrain("TreesFinish", "Trees-Finish.png");
		treesFinish.TYPE = Tile.TERRAIN;
		treesFinish.solid = true;
		addSpritesToList(pot, grass, grass_and_sand, brick, house, limestone,
				sand, water, player,treesFinish, trees);
	}

	public void addSpritesToList(Sprite... sprites) {
		for (int i = 0; i < sprites.length; i++)
			this.sprites.add(sprites[i]);
	}

	public Sprite[] getSprites() {
		Sprite[] spriteList = new Sprite[sprites.size()];
		for (int i = 0; i < spriteList.length; i++)
			spriteList[i] = sprites.get(i);
		return spriteList;
	}
}
