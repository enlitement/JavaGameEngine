package engine.asteriods;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import engine.core.Screen;
import engine.core.Sandbox;
import engine.core.graphics.GraphicsManager;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.gui.MenuButton;
import engine.rooms.Room;

public class Highscore extends Room {

	public MenuButton menu;
	public int highscore;

	public Highscore(Sandbox sandbox) {
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

		try {
			highscore = getHighscore();
		} catch (IOException e) {
			System.out.println(e);
		}
		
		setBackground("asteroidBackground.png");
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		g.setColor(Color.white);
		Font menuFont = new Font("Dialog", Font.BOLD, 13);
		String str = "Highscore:" + highscore;
		GraphicsManager.get().textManager.drawCenteredText(menuFont, str, 0, 0,
				Screen.WIDTH, Screen.HEIGHT);
		menu.paint(g);
	}

	@Override
	public void update() {
		menu.update();
	}

	public int getHighscore() throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(
				"C://Score.txt"));
		while (true) {
			/** Read the line */
			String line = reader.readLine();
			lines.add(line);
			/** No more lines to read */
			if (line == null) {
				reader.close();
				break;
			}
		}
		String line = (String) lines.get(0);
		return Integer.parseInt(line);
	}
}
