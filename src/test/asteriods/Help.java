package test.asteriods;

import java.awt.Graphics2D;

import test.core.Sandbox;
import test.extras.AButton;
import test.extras.ButtonAction;
import test.rooms.Room;

public class Help extends Room {

	public AButton menu;

	public Help(Sandbox sandbox) {
		super(sandbox);

		menu = new AButton(getSandbox(), "Menu", getSandbox().getWidth() / 2,
				getSandbox().getHeight() / 3);
		menu.addAction(new ButtonAction() {
			@Override
			public void actionPerformed() {
				addRoom(new Menu(getSandbox()));
				removeMe();
			}
		});

	}

	@Override
	public void paint(Graphics2D g) {
		g.drawString("You need help, you got it!", 300, 300);
		menu.paint(g);
	}

	@Override
	public void update() {
		menu.update();
	}

}
