package engine.core;

import java.awt.Image;
import java.io.IOException;

import engine.events.EventDispatcher;
import engine.graphics.GraphicsManager;
import engine.input.KeyboardInput;
import engine.input.MouseInput;
import engine.logger.Logger;
import engine.resources.ResourceManager;

public class JGE {

	/**
	 * INIT_STATE is set to this when a subsystem is initialized properly.
	 */
	public static final int INIT_SUCESS = 0;
	/**
	 * An error for a subsystem set up failure.
	 */
	public static final int GRAPHICS_ERROR = 1, RESOURCE_ERROR = 2,
			INPUT_ERROR = 3, WINDOW_ERROR = 4;
	
	/**
	 * INIT_STATE is set to this when all subsystems have been set up properly.
	 */
	public static final int INITIALIZED = 5;
	
	/**
	 * The current initialization state.
	 */
	public static int INIT_STATE = 0;

	/**
	 * A lock used to properly initialize the engine.
	 */
	static INIT_LOCK init_lock;
	
	/**
	 * The screen of the game.
	 */
	private static Screen screen;
	/**
	 * Manages all resources (Images, Audio, etc.)
	 */
	@SuppressWarnings("unused")
	private static ResourceManager resourceManager;
	/**
	 * Keyboard polling
	 */
	private static KeyboardInput keyboard;
	/**
	 * Mouse polling
	 */
	private static MouseInput mouse;
	
	/**
	 * Event dispatcher
	 */
	static EventDispatcher dispatcher;
	
	/**
	 * Game loop object for easy main thread loop creation.
	 */
	private static GameLoop game_loop;

	/**
	 * For debug error messages. True by default.
	 */
	public static boolean DEBUG = true;

	/**
	 * The refresh rate of the game.
	 */
	public static final int FRAMES_PER_SECOND = 60;

	/**
	 * Initialize Window (JFrame and Canvas), GraphicsManager, ResourceManager,
	 * and InputManager.
	 * 
	 * @return 0 if successful.
	 */
	public static int INIT() {
		init_lock = new INIT_LOCK();
		INIT_EVENTS();
		INIT_WINDOW();
		INIT_GRAPHICS();
		INIT_RESOURCE();
		INIT_INPUT();
		Logger.getLogger();
		Logger.log("Started program at:" + Logger.getDate() + ".");
		INIT_STATE = INIT_SUCESS;
		init_lock.ready = true;
		return INITIALIZED;
	}

	/**
	 * Initializes the window of the game by using Swing and AWT components.
	 * 
	 * @return 0 if successful, -1 if not.
	 */
	private static int INIT_WINDOW() {
		screen = new Screen();
		if (screen != null)
			return 0;
		try {
			throw new InitException("INIT_WINDOW EXCEPTION");
		} catch (Exception e) {
			System.out.println(e);
			Logger.err(e.getMessage());
			INIT_STATE = WINDOW_ERROR;
			return WINDOW_ERROR;
		}
	}

	/**
	 * This is a quick way to create an update method and paint method without 
	 * having to worry about calculating FPS or threading.
	 * @param update The update interface to use.
	 */
	public static void setGameLoop(IUpdate update) {
		game_loop = GameLoop.get();
		game_loop.setLoop(update);
	}

	/**
	 * Initializes the Graphics context of the game.
	 * 
	 * @return 0 if successful.
	 */
	private static int INIT_GRAPHICS() {
		GraphicsManager.get().initialize(screen);
		if (GraphicsManager.get() != null)
			return 0;
		try {
			throw new InitException("INIT_GRAPHICS EXCEPTION");
		} catch (Exception e) {
			Logger.err(e.getMessage());
			INIT_STATE = GRAPHICS_ERROR;
			return GRAPHICS_ERROR;
		}
	}

	private static int INIT_RESOURCE() {
		resourceManager = ResourceManager.get();
		if (ResourceManager.get() != null)
			return 0;
		try {
			throw new InitException("INIT_RESOURCE EXCEPTION");
		} catch (Exception e) {
			if (JGE.DEBUG)
				System.err.print(e);
			Logger.err(e.getMessage());
			INIT_STATE = RESOURCE_ERROR;
			return RESOURCE_ERROR;
		}
	}

	/**
	 * Initialize keyboard and mouse input
	 * 
	 * @return 0 if successful, -1 if unsuccessful
	 */
	private static int INIT_INPUT() {
		// add a key input system to "screen" canvas
		keyboard = KeyboardInput.get();
		screen.addKeyListener(keyboard);

		// add a mouse input system to the "screen" canvas
		mouse = MouseInput.get();
		MouseInput.get().initialize(screen);
		screen.addMouseListener(mouse);
		screen.addMouseMotionListener(mouse);

		if (keyboard != null && mouse != null) {
			INIT_STATE = INIT_SUCESS;
			return INIT_SUCESS;
		} else
			try {
				throw new InitException("INIT_INPUT EXCEPTION");
			} catch (Exception e) {
				if (JGE.DEBUG)
					System.err.print(e);
				Logger.err(e.getMessage());
				INIT_STATE = INPUT_ERROR;
				return INPUT_ERROR;
			}
	}

	/**
	 * Initialize the EventDispatcher that controls the GUI events.
	 * 
	 * @return 0 if successful
	 */
	private static int INIT_EVENTS() {
		dispatcher = EventDispatcher.get();
		INIT_STATE = INIT_SUCESS;
		return INIT_SUCESS;
	}

	/**
	 * Uses Graphics2D clearRect on the entire screen.
	 */
	public static void CLEAR_SCREEN() {
		GraphicsManager.CLEAR_SCREEN();
	}

	/**
	 * Uses Graphics2D dispose and shows the Graphics strategy.
	 */
	public static void BLIT_SURFACE() {
		GraphicsManager.BLIT_SURFACE();
	}

	/**
	 * Gets the image with the specified filename. This uses the method
	 * ImageIO.read(getClass().getResource(fileName)). Please use the file
	 * separator before writing the fileName. Example:
	 * JGE.LOAD_IMAGE("/background.png");
	 * 
	 * @param filename
	 *            Name of the file.
	 * @return The Image.
	 * @throws IOException
	 */
	public static Image LOAD_IMAGE(String filename) throws IOException,
			IllegalArgumentException {
		return ResourceManager.get().getImage(filename);
	}

	/**
	 * Add the specified image with the name you want to store it under.
	 * 
	 * @param imageName
	 * @param image
	 */
	public static void ADD_IMAGE(String imageName, Image image) {
		ResourceManager.get().addImage(imageName, image);
	}

	/**
	 * Sets the screen size to the specified width and height.
	 * 
	 * @param width
	 *            Width of the screen size.
	 * @param height
	 *            Height of the screen size.
	 */
	public static void SET_SCREEN_SIZE(int width, int height) {
		Screen.frame.setSize(width, height);
		screen.setBounds(0, 0, width, height);
		Screen.HEIGHT = height;
		Screen.WIDTH = width;
		Screen.frame.setLocationRelativeTo(null);
	}
	
	private static synchronized void notifyLock() {
		if(init_lock.ready)
			init_lock.notifyAll();
	}
	
	static class INIT_LOCK {
		public boolean ready;
		
		public INIT_LOCK() {
			ready = false;
		}
	}
}
