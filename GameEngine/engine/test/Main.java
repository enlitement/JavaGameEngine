package engine.test;

import java.awt.Graphics2D;

import engine.core.JGE;
import engine.core.input.KeyboardInput;
import engine.core.input.MouseInput;

public class Main implements Runnable {

	public StateManager _manager;
	// Main thread
	private Thread thread;
	private boolean isRunning;
	private long lastFpsTime;
	private int fps, framesPerSecond;

	public Main() {
		int WIDTH = 600;
		int HEIGHT = 600;
		JGE.SET_SCREEN_SIZE(WIDTH, HEIGHT);
		_manager = new StateManager(this);

		isRunning = true;
		thread = new Thread(this);
		thread.start();

	}

	public static void main(String[] args) {
		JGE.INIT();
		new Main();
	}

	public void paint(Graphics2D g) {
		_manager.paint(g);
		g.drawString("FPS:" + framesPerSecond, 1, 15);
		g.drawString(JGE.getMouseX() + "," + JGE.getMouseY(), JGE.getMouseX(),
				JGE.getMouseY() - 10);
	}

	public void update() {
		_manager.update();
	}

	/**
	 * Main game loop
	 */
	@Override
	public void run() {
		long lastLoopTime = System.nanoTime();

		while (isRunning) {
			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;

			// update the frame counter
			lastFpsTime += updateLength;
			fps++;

			if (lastFpsTime >= 1000000000) {
				framesPerSecond = fps;
				lastFpsTime = 0;
				fps = 0;
			}

			// Update keyboard
			KeyboardInput.get().poll();

			// Update mouse
			MouseInput.get().poll();

			update();

			// Render the graphics
			JGE.CLEAR_SCREEN();
			paint(JGE.getGraphics());
			JGE.BLIT_SURFACE();
			// Sleep
			try {
				// System.out.println("Sleep time:"
				// + ((lastLoopTime - System.nanoTime()) / 1000000 + 10));
				Thread.sleep((lastLoopTime - System.nanoTime()) / 1000000 + 10);

			} catch (Exception ex) {
				//Logger.getLogger().err("Main loop fatal error:" + ex);
			}
		}
	}
}
