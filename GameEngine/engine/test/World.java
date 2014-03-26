package engine.test;

import java.awt.Graphics2D;

import engine.core.resources.ResourceManager;
import engine.gui.GameGUI;
import engine.map.GridMap;
import engine.rooms.Room;
import engine.sprites.Player;
import engine.sprites.TreasureChest;

public class World extends Room {

	public PlayState _state;
	public GameGUI gui;
	public GridMap map;
	public Player player;
	public TreasureChest chest;

	public World(PlayState state) {
		ResourceManager.get().playContinuousTrack("action-new.wav");
		this._state = state;
		map = new GridMap(this);
		player = new Player("Player", map.grids[0][0].tiles[4][2]);
		map.grids[0][0].tiles[0][0].addSprite(player);
		chest = new TreasureChest("chest", map.grids[0][0].tiles[3][3]);
		map.grids[0][0].tiles[3][3].addSprite(chest);
		gui = new GameGUI(this);
	}

	@Override
	public void paint(Graphics2D g) {
		map.paint(g);
		gui.paint(g);
	}

	@Override
	public void update() {
		map.update();
		gui.update();
	}
}
