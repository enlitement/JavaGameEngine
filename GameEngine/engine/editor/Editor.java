package engine.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import engine.core.JGE;
import engine.core.Screen;
import engine.core.input.MouseInput;
import engine.map.Grid;
import engine.map.Tile;
import engine.rooms.Room;
import engine.sprites.Sprite;
import engine.test.GlobalVars;
import engine.test.MenuState;
import engine.utilities.ImageManipulator;
import engine.utilities.JSONReader;

public class Editor extends Room {

	public final int tileWidth = GlobalVars.tileWidth,
			tileHeight = GlobalVars.tileHeight,
			tileLength = GlobalVars.tileLength,
			x_increase = (Screen.WIDTH - tileLength * tileWidth) / 2,
			y_increase = (Screen.HEIGHT - tileLength * tileHeight) / 2,
			NO_FLIP = 0, FLIP_HOR = 1, FLIP_VERT = 2, FLIP_BOTH = 3;
	public int flip;
	public MapState mapState;
	public EditorGUI editorGUI;
	public Sprite selected;
	public boolean erase, fill, rotate;
	public Grid grid;
	public MouseInput _mouse;
	public JMenuBar menuBar;
	public Tile currentTile;

	public Editor(MapState mapState) {
		this.mapState = mapState;
		grid = new Grid(null, 0, 0);
		selected = new Sprite("Empty");
		_mouse = MouseInput.get();
		editorGUI = new EditorGUI(this);

		erase = false;
		fill = false;
		rotate = false;

		currentTile = null;

		menuBar = new JMenuBar();
		Screen.frame.add(menuBar);

		JMenuItem newGrid = new JMenuItem("New");
		newGrid.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		menuBar.add(newGrid);
		ActionListener newLevelActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				grid = new Grid(null, 0, 0);
			}
		};
		newGrid.addActionListener(newLevelActionListener);

		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		ActionListener saveListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(JSONReader.getUserDir());
				int returnVal = fc.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					if (fc.getSelectedFile() != null) {
						String location = fc.getSelectedFile().getPath();
						JSONReader.saveObject(location, grid);
					}
				}
			}
		};
		save.addActionListener(saveListener);
		menuBar.add(save);

		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser(JSONReader
						.getUserDir());
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String location = fc.getSelectedFile().getPath();
					grid = JSONReader.loadGrid(location, false);
					for (int x = 0; x < grid.tiles.length; x++)
						for (int y = 0; y < grid.tiles[0].length; y++)
							grid.tiles[x][y].grid = grid;
					grid.loadImages();
				}
			}
		});
		menuBar.add(load);

		JMenuItem rotateItem = new JMenuItem("Rotate");
		rotateItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setRotate(!rotate);
				System.out.println("Rotate:" + rotate);
			}
		});
		menuBar.add(rotateItem);

		JMenuItem flipItem = new JMenuItem("Flip H/V");
		flipItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				increaseFlip();
				System.out.println("Flip:" + flip);
			}
		});
		menuBar.add(flipItem);

		JMenuItem menu = new JMenuItem("Menu");
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getMapState().getStateManager().addState(
						new MenuState(getMapState().getStateManager()));
				Screen.frame.setJMenuBar(null);
				Screen.frame.pack();
				JGE.SET_SCREEN_SIZE(Screen.WIDTH, Screen.HEIGHT);
				getMapState().removeThis();
			}
		});
		menuBar.add(menu);

		Screen.frame.setJMenuBar(menuBar);
		Screen.frame.pack();
		JGE.SET_SCREEN_SIZE(Screen.WIDTH, Screen.HEIGHT);
	}

	@Override
	public void paint(Graphics2D g) {
		drawSprite(g);
		drawRectangles(g);
		editorGUI.paint(g);
		drawCurrentTileInfo(g, MouseInput.get());

	}

	public void drawSprite(Graphics2D g) {
		for (int x = 0; x < grid.tiles.length; x++)
			for (int y = 0; y < grid.tiles[0].length; y++)
				grid.tiles[x][y].paintImages(g, x_increase, y_increase);
	}

	public void drawRectangles(Graphics g) {
		g.setColor(Color.black);
		for (int x = 0; x < grid.tiles.length; x++) {
			for (int y = 0; y < grid.tiles[0].length; y++) {
				g.drawRect((int) grid.tiles[x][y].xpos + x_increase,
						(int) grid.tiles[x][y].ypos + y_increase,
						grid.tiles[x][y].WIDTH, grid.tiles[x][y].HEIGHT);
			}
		}
	}

	int rightClick = 3;
	int leftClick = 1;

	@Override
	public void update() {
		currentTile = getCurrentTile(_mouse);

		if (_mouse.buttonDown(rightClick))
			erase(_mouse);
		if (editorGUI.inventory.selected == null
				&& _mouse.buttonDownOnce(leftClick) && rotate
				&& flip == NO_FLIP)
			rotate(_mouse);
		if (editorGUI.inventory.selected == null
				&& _mouse.buttonDownOnce(leftClick) && flip != NO_FLIP
				&& !rotate)
			flip(_mouse);
		if (editorGUI.inventory.selected == null
				&& _mouse.buttonDown(leftClick) && !rotate && flip == NO_FLIP)
			fillTile(_mouse, selected);
		editorGUI.update();
	}

	public void rotate(MouseInput e) {
		if (currentTile != null && currentTile.base != null) {
			currentTile.base.setImage(ImageManipulator.rotate(
					currentTile.base.getImage(), Math.PI / 2));
			if (currentTile.base.rotation < Math.PI)
				currentTile.base.rotation += Math.PI / 2;
			else
				currentTile.base.rotation = 0;
		}
	}

	public void flip(MouseInput e) {
		if (currentTile != null && currentTile.base != null) {
			if (flip == FLIP_HOR)
				currentTile.base.changeImage(ImageManipulator.mirror(
						currentTile.base.getImage(), true));

			if (flip == FLIP_VERT)
				currentTile.base.changeImage(ImageManipulator.mirror(
						currentTile.base.getImage(), false));

			if (flip == FLIP_BOTH) {
				currentTile.base.changeImage(ImageManipulator.mirror(
						currentTile.base.getImage(), false));
				currentTile.base.changeImage(ImageManipulator.mirror(
						currentTile.base.getImage(), true));
			}
			currentTile.base.flip = flip;
		}

	}

	public void erase(MouseInput e) {
		if (currentTile != null)
			currentTile.clear();
	}

	public void fillTile(MouseInput e, Sprite sprite) {
		if (sprite != null && currentTile != null)
			currentTile.setTile(sprite);
	}

	public void drawCurrentTileInfo(Graphics2D g, MouseInput e) {
		if (currentTile != null)
			g.drawString("[" + currentTile.xpos / tileWidth + "]["
					+ currentTile.ypos / tileHeight + "]", e.getPosition().x,
					e.getPosition().y - 30);

	}

	public Tile getCurrentTile(MouseInput mi) {
		Point mouse = mi.getPosition();
		for (int x = 0; x < grid.tiles.length; x++)
			for (int y = 0; y < grid.tiles[0].length; y++)
				if (grid.tiles[x][y].xpos + x_increase < mouse.x
						&& grid.tiles[x][y].xpos + tileWidth + x_increase > mouse.x
						&& grid.tiles[x][y].ypos + x_increase < mouse.y
						&& grid.tiles[x][y].ypos + tileHeight + x_increase > mouse.y) {
					return grid.tiles[x][y];
				}
		return null;
	}

	public void setRotate(boolean rot) {
		rotate = rot;
	}

	public void increaseFlip() {
		if (flip >= FLIP_BOTH)
			flip = NO_FLIP;
		else
			flip++;
	}

	public MapState getMapState() {
		return mapState;
	}
}
