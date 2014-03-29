package engine.asteriods;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import engine.core.Screen;
import engine.core.Sandbox;
import engine.core.graphics.GraphicsManager;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.gui.MenuButton;
import engine.rooms.Room;

public class Help extends Room {

	public MenuButton menu;

	public Help(Sandbox sandbox) {
		super(sandbox);

		menu = new MenuButton(getSandbox(), "Menu", (Screen.WIDTH / 2) - 50,
				Screen.HEIGHT - 60);
		menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				addRoom(new Menu(getSandbox()));
				removeMe();
			}
		});
		setBackground("asteroidBackground.png");
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		g.setColor(Color.white);
		Font menuFont = new Font("Dialog", Font.BOLD, 13);
		String str = "You need help; you've got it!";
		GraphicsManager.get().textManager.drawCenteredText(menuFont, str, 0, 0,
				Screen.WIDTH, Screen.HEIGHT);
		menu.paint(g);
	}

	@Override
	public void update() {
		menu.update();
	}

}
