package engine.rooms;

import java.awt.Graphics2D;

import engine.gui.AbstractButton;
import engine.gui.MenuLayoutManager;

public abstract class Menu extends Room {

	public MenuLayoutManager _layout;

	public Menu() {
		super();
		// Set up MenuLayoutManager
		_layout = new MenuLayoutManager(this);
	}

	public void removeListeners() {
		for (AbstractButton object : _layout.buttonList)
			object.removeAllListeners();
	}

	public void paint(Graphics2D g) {

	}

	@Override
	public void update() {

	}

	public abstract void onDestroy();

}
