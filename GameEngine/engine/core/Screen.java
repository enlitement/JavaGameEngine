package engine.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

import engine.events.EventDispatcher;
import engine.events.FullscreenEvent;
import engine.events.FullscreenListener;
import engine.graphics.GraphicsManager;
import engine.logger.Logger;
import engine.objects.GameObject;

/**
 * The Screen is responsible for setting up a double buffered Window, able to
 * receive Java Mouse and Key events, with full screen capability.
 * 
 * @author Helson
 * 
 */
public class Screen extends Canvas {

	private static final long serialVersionUID = 1L;

	/**
	 * Size of the game canvas
	 */
	public static int WIDTH = 448, HEIGHT = 600;
	public static final int FS_WIDTH = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode().getWidth(), FS_HEIGHT = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.getDisplayMode().getHeight();
	public static boolean FULLSCREEN_COMPATIBLE = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getDefaultScreenDevice()
			.isFullScreenSupported();
	/*
	 * Game window
	 */
	public static JFrame frame;
	/**
	 * Graphics strategy
	 */
	private BufferStrategy strategy;

	private static Fullscreen fullscreen;
	private static FullscreenEvent fsEvt;

	/**
	 * Sets up the screen object.
	 */
	public Screen() {
		Logger.ld("Full screen compatible = " + FULLSCREEN_COMPATIBLE);
		frame = setUpWindow(frame, WIDTH, HEIGHT, false);
		setStrategies();
		fsEvt = new FullscreenEvent(this);
		fullscreen = new Fullscreen();
	}

	/**
	 * Exits the program.
	 */
	public static void exit() {
		WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}

	/**
	 * Returns the buffer strategy.
	 * 
	 * @return
	 */
	public BufferStrategy getGameBufferStrategy() {
		return strategy;
	}

	/**
	 * Returns the JFrame object. USE WITH CAUTION! If you want to change the
	 * size of the JFrame, use JGE.setSize(width,height);
	 * 
	 * @return
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Do all the fancy graphics stuff to have page flipping graphics.
	 */
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

	/**
	 * Sets up the window size, frame, etc.
	 */
	public JFrame setUpWindow(JFrame fr, int width, int height,
			boolean fullscreen) {
		// Make JFrame
		if (!fullscreen)
			fr = new JFrame("Engine");

		// get hold the content of the frame and set up the resolution of the
		// game
		JPanel panel = (JPanel) fr.getContentPane();
		panel.setLayout(null);

		// setup our canvas size and put it into the content of the frame
		if (fullscreen) {
			panel.setPreferredSize(new Dimension(FS_WIDTH, FS_HEIGHT));
			setBounds(FS_WIDTH / 2 - WIDTH / 2, FS_HEIGHT / 2 - HEIGHT / 2,
					WIDTH, HEIGHT);
			panel.setBackground(Color.black);
		} else {
			setBounds(0, 0, WIDTH, HEIGHT);
			panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		}

		panel.add(this);

		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);

		// finally make the window visible
		fr.pack();
		fr.setResizable(false);

		fr.setVisible(true);

		if (!fullscreen)
			fr.setLocationRelativeTo(null);
		if (!fullscreen)
			fr.addWindowListener(new WindowAdapter() {
				@Override
				/**
				 * Manually refreshes the canvas. Sometimes when the window is dragged
				 * the screen shows a chopped version of the game. It has to be moved 
				 * again to be fixed. Instead, this simply repaints the game 
				 * when its moved.
				 */
				public void windowStateChanged(WindowEvent e) {
					super.windowStateChanged(e);
					if (e.getID() == WindowEvent.COMPONENT_MOVED
							&& GameLoop.isStarted)
						GameLoop.get().update
								.onPaint(GraphicsManager.graphics2D);
				}

				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		return fr;
	}

	/**
	 * Toggle the fullscreen mode.
	 */
	public static void toggleFullscreen() {
		EventDispatcher.eventArrived(fsEvt);
	}

	private static boolean toggleFS = false;

	private void checkFullscreen() {
		if (!FULLSCREEN_COMPATIBLE)
			return;
		System.out.println("Toggling fullscreen mode.");
		toggleFS = !toggleFS;
		if (toggleFS)
			fullscreenMode();
		else
			windowMode();
		requestFocusInWindow();
	}

	private void windowMode() {
		Logger.ld("Entering window mode...");
		frame.dispose();
		frame = setUpWindow(frame, WIDTH, HEIGHT, false);
	}

	private void fullscreenMode() {
		Logger.ld("Entering fullscreen mode...");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			Logger.log(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (InstantiationException e) {
			Logger.log(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Logger.log(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			Logger.log(e.getLocalizedMessage());
			e.printStackTrace();
		}

		frame.dispose();

		JFrame newFrame = new JFrame();
		newFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		newFrame.setUndecorated(true);
		newFrame.setResizable(false);
		newFrame.validate();

		frame = newFrame;

		GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().setFullScreenWindow(frame);
		frame = setUpWindow(frame, WIDTH, HEIGHT, true);

	}

	/**
	 * Set the title of the JFrame.
	 * 
	 * @param title
	 */

	public static void setTitle(String title) {
		frame.setTitle(title);
	}

	private class Fullscreen extends GameObject {

		public Fullscreen() {
			EventDispatcher.addObserver(this, fsEvt, new FullscreenListener() {
				@Override
				public void onToggle() {
					checkFullscreen();
				}
			});
		}

		@Override
		public void paint(Graphics2D g) {

		}

	}
}
