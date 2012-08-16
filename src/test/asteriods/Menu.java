package test.asteriods;

import java.awt.Graphics2D;

import test.core.Sandbox;
import test.extras.AButton;
import test.rooms.Room;
import test.asteriods.Play;

public class Menu extends Room {
	
	public AButton menu;
	
	public Menu(Sandbox sandbox) {
		super(sandbox);
		
		menu = new AButton(sandbox,"Menu",200,200);
		roomObjects.add(menu);
	}

	@Override
	public void paint(Graphics2D g) {
		menu.paint(g);
	}

	@Override
	public void update() {
		menu.update();
		menuAction();
	}
	
	public void menuAction() {
		if(menu.pressed) {
			getSandbox().addRoom(new Play(getSandbox()));
			//getSandbox().nextRoom();
			removeMe();
		}
	}

}
