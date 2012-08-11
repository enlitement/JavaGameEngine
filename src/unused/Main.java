package unused;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import test.core.Game;


public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// The Game class
	private JPanel game;
	
	// The Dimensions of the Game
	private Dimension dim;

	// Accessible from all classes
	public static int GWidth = 400;
	public static int GHeight = 400;

	public Main(String name) {
		// Make the container
		setTitle("Test");
		
		Container c = getContentPane();
		game = new Game(this);
		c.add(game, "Center");
		
		createAndShowGUI();
	}

	/**
	 * Exits the program
	 */
	public void pullThePlug() {
		WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}

	public void createAndShowGUI() {
		dim = new Dimension(GWidth, GHeight);
		setPreferredSize(dim);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main("Game");
	}
}