package test.core;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public final static int WIDTH = 600, HEIGHT = 400;
	
	// Game window
	public JFrame container;
	// Graphics strategy
	public BufferStrategy strategy;
	
	// Game components
	// Manages the painting of the game
	public GraphicsManager graphicsManager;
	// Manages all the State objects for the game
	public StateManager stateManager;
	// Manages all resources (Images, Audio, etc.)
	public ResourceManager resourceManager;
	// Keyboard polling
	public KeyboardInput keyboard;

	// Main thread
	public Thread thread;
	public boolean isRunning, isPaused;
	public long lastFpsTime;
	public int fps, framesPerSecond;

	public Game() {
		// Make JFrame
		container = new JFrame("Test");

		// get hold the content of the frame and set up the resolution of the
		// game
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);

		// setup our canvas size and put it into the content of the frame
		setBounds(0, 0, WIDTH, HEIGHT);
		panel.add(this);

		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);

		// finally make the window visible
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		container.setLocationRelativeTo(null);

		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed
		keyboard = new KeyboardInput();
		addKeyListener(keyboard);

		// request the focus so key events come to us
		requestFocusInWindow();

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		// Set ignore repaint so that we can determine when
		// to paint or not
		setIgnoreRepaint(true);

		// Start game components
		resourceManager = new ResourceManager(this);
		stateManager = new StateManager(this);
		graphicsManager = new GraphicsManager(this);

		// Start main game loop
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}

	/**
	 * Main game loop
	 */
	@Override
	public void run() {
		while (isRunning) {

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
					System.out.println("(FPS: " + fps + ")");
					lastFpsTime = 0;
					fps = 0;
				}
				
				// Update keyboard
				keyboard.poll();
				
				// Run the update loop
				stateManager.run();
				
				// Draw the graphics
				graphicsManager.run();

				try {
					// System.out.println("Sleep time:"+((lastLoopTime -
					// System.nanoTime()) / 1000000 + 10));
					Thread.sleep((lastLoopTime - System.nanoTime()) / 1000000 + 10);
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}

	}

	/**
	 * Exits the program
	 */
	public void gameExit() {
		WindowEvent wev = new WindowEvent(container, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}

	public static void main(String[] args) {
		new Game();
	}
}
