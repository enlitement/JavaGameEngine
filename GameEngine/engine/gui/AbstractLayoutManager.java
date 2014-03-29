package engine.gui;

import java.awt.Graphics2D;

import engine.rooms.Room;

public abstract class AbstractLayoutManager {

	public Room room;

	public AbstractLayoutManager(Room room) {
		this.room = room;
	}

	public abstract void paint(Graphics2D g);

	public abstract void arrange();

	public abstract void update();
}
