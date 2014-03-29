package engine.asteriods;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import engine.core.Game;
import engine.core.Sandbox;
import engine.core.input.KeyboardInput;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.gui.MenuButton;
import engine.rooms.Room;

@SuppressWarnings("unused")
public class Play extends Room {

	public AsteroidCreator creator;
	public MenuButton pause;
	public Ship ship;
	public double backX, backY;
	public static int score;
	public int vpx = 0;
	public int vpy = 0;
	public int lives;
	public final int scoreIncrement = 10, gameWidth = Game.WIDTH,
			gameHeight = Game.HEIGHT;

	public Play(Sandbox sandbox) {
		super(sandbox);
		name = "Play";
		score = 0;
		pause = new MenuButton(getSandbox(), "Pause", (Game.WIDTH / 2) - 50,
				Game.HEIGHT - 40);
		pause.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				addRoom(new Pause(getSandbox()));
			}
		});
		pause.setOutlineColor(Color.cyan);
		addObject(pause);

		ship = new Ship(sandbox, this, 150, 150);
		addObject(ship);

		vpx = (int) (ship.xpos - gameWidth / 2);
		vpy = (int) (ship.ypos - gameHeight / 2);
		backX = -200;
		backY = -200;
		creator = new AsteroidCreator(sandbox, this, vpx, vpy);
		addObject(creator);
		setBackground("asteroidBackground.png");

		lives = 3;
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		// drawBackground(g);
		ship.paint(g, vpx, vpy);
		creator.paint(g, vpx, vpy);
		pause.paint(g);
		drawFPS();
		g.drawString("Lives:" + lives, 10, 40);
		g.drawString("Score:" + score, 10, 30);
	}

	@Override
	public void update() {
		vpx = (int) (ship.xpos - gameWidth / 2);
		vpy = (int) (ship.ypos - gameHeight / 2);

		creator.update(vpx, vpy);
		ship.update(vpx, vpy);
		pause.update();

		getSandbox().updateCollisionManager();
		
		if (KeyboardInput.get().keyDown(java.awt.event.KeyEvent.VK_F)) {
			System.out.println("LIST_____________");
			for (int i = 0; i < getCollidables().size(); i++) {
				System.out.println(getCollidables().get(i) + ":("
						+ getCollidables().get(i).xpos + ","
						+ getCollidables().get(i).ypos + ")");
			}
			System.out.println("END______________");
		}
		if (score%200==0) {
			lives++;
		}
		if (lives <= 0) {
			try {
				saveHighscore();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getSandbox().addRoom(new Menu(getSandbox()));
			removeMe();
		}
	}

	public static void saveHighscore() throws IOException {
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
		int newScore = Integer.parseInt(line);
		if (score > newScore) {
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter("C://Score.txt"));
				writer.write(String.valueOf(score));
				// System.out.println("Score:" + score);

			} catch (IOException e) {
			} finally {
				try {
					if (writer != null)
						writer.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public void drawBackground(Graphics2D g) {
		int parallax = 200;
		int backgroundWidth = background.getWidth(null);
		int backgroundHeight = background.getHeight(null);
		// calculate posx and posy, ensuring they lie within 0-2999:
		int posx = (backgroundWidth + ((int) ((vpx / parallax)))
				% backgroundWidth)
				% backgroundWidth;
		int posy = (backgroundHeight + ((int) ((vpy / parallax)))
				% backgroundHeight)
				% backgroundHeight;

		// test for overflow:
		boolean xOverflow = (posx + Game.WIDTH > backgroundWidth);
		boolean yOverflow = (posy + Game.HEIGHT > backgroundWidth);

		// draw the image:
		drawMyImage(g, posx, posy, backgroundWidth, backgroundHeight);
		// if necessary, draw displaced copies:
		if (xOverflow)
			drawMyImage(g, posx - backgroundWidth, posy, backgroundWidth,
					backgroundHeight);
		if (yOverflow)
			drawMyImage(g, posx, posy - backgroundHeight, backgroundWidth,
					backgroundHeight);
		if (xOverflow && yOverflow)
			drawMyImage(g, posx - backgroundWidth, posy - backgroundHeight,
					backgroundWidth, backgroundHeight);
	}

	public void drawMyImage(Graphics2D g, int x, int y, int backgroundWidth,
			int backgroundHeight) {
		int dx1 = Math.max(-x, 0);
		int dy1 = Math.max(-y, 0);
		int dx2 = Math.min(backgroundWidth - x, Game.WIDTH);
		int dy2 = Math.min(backgroundHeight - y, Game.HEIGHT);
		int sx1 = Math.max(x, 0);
		int sy1 = Math.max(y, 0);
		int sx2 = Math.min(x + Game.WIDTH, backgroundWidth);
		int sy2 = Math.min(y + Game.HEIGHT, backgroundHeight);
		g.drawImage(background, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null,
				null);
	}
}
