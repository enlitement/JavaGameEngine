package engine.editor;

import java.awt.Graphics2D;

import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.rooms.Room;
import engine.sprites.HealthPot;
import engine.sprites.Sprite;
import engine.sprites.Terrain;
import engine.test.SpriteSlot;
import engine.utilities.JSONReader;

public class EditorGUI extends Room {

	public Editor editor;
	public Sprite[] spriteList;
	public Inventory inventory;

	public EditorGUI(Editor editor) {
		super();
		this.editor = editor;
		inventory = new Inventory(6, 6, 14);

		for (int i = 0; i < inventory.small_slots.size(); i++) {
			Listener listener = new Listener(i);
			inventory.small_slots.get(i).addActionListener(listener);
		}

		for (final SpriteSlot<Sprite> slot : inventory.large_slots)
			slot.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					setSelected(slot.type);
				}
			});
		ListOfSprites LOS = new ListOfSprites();
		inventory.addAllSprites(LOS.getSprites());
	}

	@Override
	public void paint(Graphics2D g) {
		inventory.paint(g);
	}

	@Override
	public void update() {
		inventory.update();
	}

	public void setSelected(Sprite sprite) {
		editor.selected = sprite;
	}

	public Sprite getSelected() {
		return editor.selected;
	}
	
	public Editor getEditor() {
		return editor;
	}

	class Listener implements ActionListener {
		int i = 0;

		public Listener(int i) {
			this.i = i;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			setSelected(inventory.small_slots.get(i).type);
		}
	}
}
