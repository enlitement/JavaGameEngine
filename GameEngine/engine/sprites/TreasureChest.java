package engine.sprites;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.core.JGE;
import engine.core.input.MouseInput;
import engine.editor.Inventory;
import engine.interfaces.Openable;
import engine.map.Tile;
import engine.utilities.Utilities;

public class TreasureChest extends Terrain implements Openable {
	public Inventory chest;
	public HealthPot pot; 
	public TreasureChest(String name, Tile myTile) {
		super(name, myTile);
		chest = new Inventory(null, 3, 3);
		classType = TreasureChest.class;
		pot = new HealthPot("Health pot", myTile);
		chest.addSprite(pot);
	}

	public TreasureChest() {
		name = "Treasure Chest";
		chest = new Inventory(null, 3, 3);
		classType = TreasureChest.class;
	}

	@Override
	public void show(Inventory inventory) {
		myTile.grid.map.world.gui.roomObjects.add(chest);
		chest.displayingLargeInventory = true;
	}

	public void paint(Graphics2D g) {
		g.setColor(Color.cyan);
		g.fillRect((int) xpos, (int) ypos, 32, 32);
		// paintInventory(g);
	}

	public void paintInventory(Graphics2D g) {
		chest.paint(g);
	}

	public void update() {
		// System.out.println("updating");
		if (Utilities.withinBounds(xpos, ypos, xpos + 32, ypos + 32,
				JGE.getMouseX(), JGE.getMouseY())
				&& MouseInput.get().buttonDownOnce(1))
			show(chest);
		if (chest.displayingLargeInventory)
			chest.update();
	}

	@Override
	public void hide(Inventory inventory) {
		myTile.grid.map.world.gui.removeObject(chest);
	}
}
