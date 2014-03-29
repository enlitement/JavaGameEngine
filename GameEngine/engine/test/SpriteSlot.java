package engine.test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import engine.core.JGE;
import engine.core.input.MouseInput;
import engine.editor.Inventory;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.gui.Button2;
import engine.interfaces.Draggable;
import engine.sprites.Item;
import engine.sprites.ItemAction;
import engine.sprites.Sprite;

public class SpriteSlot<T extends Sprite> extends Button2 implements Draggable {

	public int counter;
	public Color blue, green, current;
	public T type;
	public boolean removeListener, drag;
	public Listener listener;
	public Inventory inventory;

	public SpriteSlot(int xpos, int ypos, int width, int height) {
		super("Slot");
		blue = Color.blue;
		green = Color.green;
		current = blue;
		this.width = width;
		this.height = height;
		this.xpos = xpos;
		this.ypos = ypos;
		setPosition(xpos, ypos);
		removeListener = false;
		counter = 0;
		drag = false;
	}

	public SpriteSlot(Inventory inv, int xpos, int ypos, int width, int height) {
		this(xpos, ypos, width, height);
		this.inventory = inv;
	}

	@Override
	public void paint(Graphics2D g) {
		paintSlot(g);
		if (type != null && !drag) {
			type.paint(g);
		}
	}

	public void paintSlot(Graphics2D g) {
		g.setColor(Color.black);
		g.drawRect((int) xpos, (int) ypos, width, height);
		g.setColor(current);
		g.fillRect((int) xpos, (int) ypos, width, height);
	}

	@Override
	public void enableClickEffect() {
		current = green;
	}

	@Override
	public void revertClickEffect() {
		current = blue;
	}

	@Override
	public void customInput() {
		if (type == null && listener != null)
			removeListener = true;
		if (removeListener && listener != null) {
			listener = null;
			removeActionListener(listener);
			removeListener = false;
		}
		if (type != null) {
			if (pressed)
				counter++;
			else
				counter = 0;
			if (counter > 50 || drag) 
				drag(inventory);
			
			if (drag && !mouseWithinBounds(MouseInput.get())
					&& !MouseInput.get().buttonDown(1)) {
				if (inventory.displayingLargeInventory)
					checkForPlacement(inventory.large_slots);
				else
					checkForPlacement(inventory.small_slots);

				drag = false;
				counter = 0;
				inventory.selected = null;
			}
			if (!MouseInput.get().buttonDown(1)) {
				drag = false;
				if (type != null) {
					type.xpos = xpos;
					type.ypos = ypos;
				}
			}
		}
	}
	

	private void removeActionListener(Listener listener2) {
		// TODO Auto-generated method stub
		
	}

	public T getType() {
		return type;
	}

	public void setSprite(T type) {
		this.type = type;
		if (type != null) {
			this.type.xpos = xpos;
			this.type.ypos = ypos;
			if (type instanceof Item && ((Item) type).owner != null
					&& listener == null) {
				listener = new Listener();
				System.out.println("Adding actionListener");
				addActionListener(listener);
			}
		}
	}

	public void removeSprite() {
		type = null;
	}

	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (type instanceof ItemAction) {
				((ItemAction) type).action();
				removeSprite();
				removeListener = true;
			}
		}
	}

	@Override
	public void drag(Inventory inventory) {
		if (type != null) {
			drag = true;
			inventory.selected = type;
			type.xpos = JGE.getMouseX() - 16;
			type.ypos = JGE.getMouseY() - 16;
		}
	}

	@Override
	public void drag(Inventory inventory, ArrayList<SpriteSlot<Sprite>> array1,
			ArrayList<SpriteSlot<Sprite>> array2) {
		array1 = inventory.large_slots;
		array2 = inventory.small_slots;

	}
	
	@Override
	public void checkForPlacement(ArrayList<SpriteSlot<Sprite>> list) {
		for (int i = 0; i < list.size() - 1; i++)
			if (list.get(i).mouseWithinBounds(MouseInput.get())
					&& list.get(i).type == null) {
				if (list.get(i).mouseWithinBounds(MouseInput.get())
						&& list.get(i).type == null) {
					SpriteSlot<Sprite> newslot = list.get(i);
					newslot.setSprite(type);
					type = null;
				}
			}
	}
}
