package engine.core;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.core.graphics.GraphicsManager;
import engine.core.input.KeyboardInput;
import engine.core.input.MouseInput;
import engine.core.resources.ResourceManager;


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
		setUpWindow();
		createInput();
		setStrategies();
	}

	/**
	 * Starts the game components and puts the game on its loop.
	 * 
	 * @param sandbox
	 *            The sandbox type you want to run
	 */
	public void runEngine(Sandbox sandbox) {
		this.sandbox = sandbox;
		startGameComponents();
		startLoop();
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
				// framesPerSecond = fps;
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

			} catch (Exception ex) {
				// System.out.println(ex);
			}
		}
	}

	/**
	 * Starts the resourceManager and the graphicsManager.
	 */
	public void startGameComponents() {
		resourceManager = ResourceManager.getInstance();
		ResourceManager.getInstance().initialize(this);
		graphicsManager = GraphicsManager.get();
		GraphicsManager.get().initialize(this);
	}

	/**
	 * Create keyboardInput and mouseInput.
	 */
	public void createInput() {
		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed
		keyboard = KeyboardInput.get();
		addKeyListener(keyboard);

		// add a mouse input system to the canvas to accept mouse
		// input
		mouse = MouseInput.getInstance();
		MouseInput.getInstance().initialize(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
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
	 * Exits the program
	 */
	public void gameExit() {
		WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
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

	public void setStrategies() {
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

	public void setUpWindow() {
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
	}

}
