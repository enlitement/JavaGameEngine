package test.core;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import test.core.graphics.GraphicsManager;
import test.core.input.KeyboardInput;
import test.core.input.MouseInput;
import test.core.resources.ResourceManager;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	// Size of the window
	public static int WIDTH = 600, HEIGHT = 400;
	// Game window
	private JFrame frame;
	// Graphics strategy
	private BufferStrategy strategy;
	// Manages the painting of the game
	private static GraphicsManager graphicsManager;
	// Manages the actual game creation
	private Sandbox sandbox;
	// Manages all resources (Images, Audio, etc.)
	private static ResourceManager resourceManager;
	// Keyboard polling
	private static KeyboardInput keyboard;
	// Mouse polling
	private static MouseInput mouse;

	// Main thread
	private Thread thread;
	private boolean isRunning;
	private long lastFpsTime;
	private int fps;

	public Game() {
		// Make JFrame
		frame = new JFrame("Engine");

		// get hold the content of the frame and set up the resolution of the
		// game
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);

		// setup our canvas size and put it into the content of the frame
		setBounds(0, 0, WIDTH, HEIGHT);
		panel.add(this);

		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);

		// finally make the window visible
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		createInput();

		// request the focus so key events come to us
		requestFocusInWindow();

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		// Set ignore repaint so that we can determine when
		// to paint or not
		setIgnoreRepaint(true);
	}

	public void runEngine(Sandbox sandbox) {
		setSandbox(sandbox);
		startGameComponents();
		startLoop();
	}

	public void startGameComponents() {
		// Start game components
		resourceManager = ResourceManager.getInstance();
		ResourceManager.getInstance().initialize(this);
		graphicsManager = GraphicsManager.getInstance();
		GraphicsManager.getInstance().initialize(this);

		System.out.println("Game Components started");
	}

	public void createInput() {
		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed
		keyboard = KeyboardInput.getInstance();
		addKeyListener(keyboard);

		// add a mouse input system to the canvas to accept mouse
		// input
		mouse = MouseInput.getInstance();
		MouseInput.getInstance().initialize(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		System.out.println("Input constructed");
		if (mouse != null)
			System.out.println("Mouse is not null");
	}

	/**
	 * Start main game loop
	 */
	public void startLoop() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
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
					//framesPerSecond = fps;
					System.out.println("(FPS: " + fps + ")");
					lastFpsTime = 0;
					fps = 0;
				}

				// Check for any pending resources
				resourceManager.run();

				// Update keyboard
				keyboard.poll();

				// Update mouse
				mouse.poll();

				// Update the game
				sandbox.run();

				// Draw the graphics
				graphicsManager.run();

				try {
					// System.out.println("Sleep time:"
					// + ((lastLoopTime - System.nanoTime()) / 1000000 + 10));
					Thread.sleep((lastLoopTime - System.nanoTime()) / 1000000 + 10);
					// Thread.sleep(1500);
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
		WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}

	public void setSandbox(Sandbox sandbox) {
		this.sandbox = sandbox;
	}

	public Sandbox getSandbox() {
		return sandbox;
	}

	public BufferStrategy getGameBufferStrategy() {
		return strategy;
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
