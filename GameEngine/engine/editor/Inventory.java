package engine.editor;

import java.awt.Graphics2D;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ArrayList;

import engine.core.Screen;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.objects.GameObject;
import engine.sprites.Entity;
import engine.sprites.Sprite;
import engine.test.SpriteSlot;

public class Inventory extends GameObject implements DropTargetListener {

	public ArrayList<SpriteSlot<Sprite>> large_slots;
	public ArrayList<SpriteSlot<Sprite>> small_slots;
	public final int inset = 5;
	public boolean displayingLargeInventory, refresh;
	public int numSmallSlots;
	public Entity owner;
	public Sprite selected;

	/**
	 * Create an inventory fit for a Player.
	 * 
	 * @param owner
	 * @param xSlots
	 * @param ySlots
	 * @param smallSlots
	 */
	public Inventory(Entity owner, int xSlots, int ySlots, int smallSlots) {
		this(xSlots, ySlots, smallSlots);
		this.owner = owner;
		refresh = true;
	}

	/**
	 * Create an inventory for an computer or chest. (No small_slots inventory)
	 * 
	 * @param owner
	 * @param xSlots
	 * @param ySlots
	 */
	public Inventory(Entity owner, int xSlots, int ySlots) {
		int slotWidth = 32;
		int slotHeight = 32;

		large_slots = new ArrayList<SpriteSlot<Sprite>>(xSlots * ySlots + 1);
		createInventory(large_slots, xSlots, ySlots, slotWidth, slotHeight,
				inset);
		SpriteSlot<Sprite> closeButton = new SpriteSlot<Sprite>(
				(int) large_slots.get(0).xpos, (int) large_slots.get(0).ypos
						- slotHeight - inset, slotWidth, slotHeight);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				displayingLargeInventory = !displayingLargeInventory;
			}
		});
		large_slots.add(closeButton);
		refresh = true;
	}
	
	/**
	 * Create an inventory that does not belong to an entity. 
	 * Use: Map Editor. (GUI purposes)
	 * 
	 * @param xSlots
	 * @param ySlots
	 * @param smallSlots
	 */
	public Inventory(int xSlots, int ySlots, int smallSlots) {
		this.numSmallSlots = smallSlots;
		int slotWidth = 32;
		int slotHeight = 32;

		large_slots = new ArrayList<SpriteSlot<Sprite>>(xSlots * ySlots + 1);
		small_slots = new ArrayList<SpriteSlot<Sprite>>(smallSlots);

		createInventory(large_slots, xSlots, ySlots, slotWidth, slotHeight,
				inset);
		createInventory(small_slots, numSmallSlots, 1, slotWidth, slotHeight,
				inset);

		SpriteSlot<Sprite> closeButton = new SpriteSlot<Sprite>(this,
				(int) large_slots.get(0).xpos, (int) large_slots.get(0).ypos
						- slotHeight - inset, slotWidth, slotHeight);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				displayingLargeInventory = !displayingLargeInventory;
			}
		});

		large_slots.add(closeButton);

		small_slots.get(0).addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				displayingLargeInventory = !displayingLargeInventory;
			}
		});

		displayingLargeInventory = false;
		refresh = true;
	}

	public void createInventory(ArrayList<SpriteSlot<Sprite>> buttonList,
			int xLength, int yLength, int width, int height, int inset) {

		// The total size for the inset, or spaces between buttons
		int totalXInset = (xLength - 1) * inset;
		int totalYInset = (yLength - 1) * inset;
		// The total height = height of buttons + size of insets
		int totalHeight = totalYInset + yLength * height;
		int totalWidth = totalXInset + xLength * width;
		// One button + one inset that follows
		int buttAndInset = width + inset;
		// Start y = the first button's ypos
		int startX = Screen.WIDTH / 2 - totalWidth / 2;

		int startY = 0;
		if (yLength > 1)
			startY = Screen.HEIGHT / 2 - totalHeight / 2;
		else if (yLength == 1)
			startY = inset;

		// Now, display buttons.
		for (int y = 0; y < yLength; y++) {
			for (int x = 0; x < xLength; x++) {
				int xpos = startX;
				xpos += buttAndInset * x;
				// Ypos starts at half the height
				int ypos = startY;
				ypos += buttAndInset * y;

				// Set the position
				SpriteSlot<Sprite> slot = new SpriteSlot<Sprite>(this, xpos,
						ypos, width, height);
				buttonList.add(slot);
			}
		}

	}

	public void paint(Graphics2D g) {
		if (displayingLargeInventory)
			paintLargeInventory(g);
		else if (small_slots != null)
			paintSmallInventory(g);
		if(selected!=null) {
			selected.paint(g);
		}
	}

	public void paintLargeInventory(Graphics2D g) {
		for (SpriteSlot<Sprite> slot : large_slots)
			slot.paint(g);
	}

	public void paintSmallInventory(Graphics2D g) {
		for (SpriteSlot<Sprite> slot : small_slots)
			slot.paint(g);
	}

	public void addSprite(Sprite sprite) {
		if (small_slots != null && !displayingLargeInventory)
			for (int i = 1; i < small_slots.size(); i++) {
				if (small_slots.get(i).type == null) {
					small_slots.get(i).setSprite(sprite);
					small_slots.get(i).type.loadImage();
					return;
				}
			}
		for (SpriteSlot<Sprite> slot : large_slots)
			if (slot.type == null) {
				slot.setSprite(sprite);
				slot.type.loadImage();
				return;
			}
	}

	public void addAllSprites(Sprite[] sprites) {
		for (int i = 0; i < sprites.length; i++) {
			addSprite(sprites[i]);
		}

	}

	public void removeItem(Sprite sprite) {
		for (SpriteSlot<Sprite> slot : large_slots)
			if (slot.type == sprite) {
				slot.removeSprite();
				break;
			}
	}

	public void update() {
		if (small_slots != null) {
			if (!displayingLargeInventory) {
				if (refresh == false) {
					refreshSmallInventory();
					refresh = true;
				}
				for (int i = 0; i < small_slots.size(); i++)
					small_slots.get(i).update();
			}
			if (displayingLargeInventory) {
				if (refresh)
					refreshLargeInventory();
				for (int i = 0; i < large_slots.size(); i++)
					large_slots.get(i).update();
			}
		} else
			if (displayingLargeInventory) 
				for (int i = 0; i < large_slots.size(); i++)
					large_slots.get(i).update();
	}

	public void refreshLargeInventory() {
		for (int i = 1; i < small_slots.size(); i++) 
			large_slots.get(i - 1).setSprite(small_slots.get(i).type);
		refresh = false;
	}

	public void refreshSmallInventory() {
		for (int i = 1; i < small_slots.size(); i++) {
			small_slots.get(i).setSprite(large_slots.get(i - 1).type);
		}
	}

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drop(DropTargetDropEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
