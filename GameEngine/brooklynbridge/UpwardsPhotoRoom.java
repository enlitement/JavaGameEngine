package brooklynbridge;

import java.awt.Graphics2D;
import java.awt.Image;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

/**
 * 6 4 0 5 3 1 2
 * 
 * @author helson.taveras
 * 
 */
public class UpwardsPhotoRoom extends PhotoRoom {

	public ArrowButtonLayoutManager layoutManager;
	public ArrowButton left, right, forward, topLeft, topRight, up;
	public final int TOPLEFT = 4, UP = 5, TOPRIGHT = 6;
	public boolean imagesLoaded;
	public String[] imageStrings;

	/**
	 * Example: getSandbox(), this, "beginning/4";
	 * 
	 * @param sandbox
	 * @param roomManager
	 * @param imageNumber
	 */
	public UpwardsPhotoRoom(Sandbox sandbox, RoomManager roomManager,
			String imageNumber) {
		super(sandbox, roomManager);

		imagesLoaded = false;

		int maxImages = 7;
		imageStrings = new String[maxImages];
		images = new Image[maxImages];

		for (int i = 0; i < images.length; i++) {
			images[i] = ResourceManager.get().addImage(
					imageNumber + "" + i + ".gif");
			imageStrings[i] = imageNumber + "" + i + ".gif";
		}

		setUpGUI();
	}

	/*
	 * No need to specify .gif
	 */
	public UpwardsPhotoRoom(Sandbox sandbox, RoomManager roomManager,
			String[] imageNames) {
		super(sandbox, roomManager);

		imagesLoaded = false;
		int maxImages = 7;

		imageStrings = new String[maxImages];
		images = new Image[maxImages];

		if (imageNames.length != 7)
			System.out.println("Upwards PR image Strings != 7");

		for (int i = 0; i < imageNames.length; i++) {
			if (imageStrings[i] != null) {
				images[i] = ResourceManager.get().addImage(
						imageNames[i] + ".gif");
				imageStrings[i] = imageNames[i] + ".gif";
			} else {
				images[i] = null;
				imageStrings[i] = null;
			}
		}
		/*
		 * if (image0 != null) { images[0] =
		 * ResourceManager.get().addImage(image0 + ".gif"); imageStrings[0] =
		 * image0 + ".gif"; } else { images[0] = null; imageStrings[0] = null; }
		 * 
		 * if (image1 != null) { images[1] =
		 * ResourceManager.get().addImage(image1 + ".gif"); imageStrings[1] =
		 * image1 + ".gif"; } else { images[1] = null; imageStrings[1] = null; }
		 * 
		 * if (image2 != null) { images[2] =
		 * ResourceManager.get().addImage(image2 + ".gif"); imageStrings[2] =
		 * image2 + ".gif"; } else { images[2] = null; imageStrings[2] = null; }
		 * 
		 * if (image3 != null) { images[3] =
		 * ResourceManager.get().addImage(image3 + ".gif"); imageStrings[3] =
		 * image3 + ".gif"; } else { images[3] = null; imageStrings[3] = null; }
		 * 
		 * if (image4 != null) { images[4] =
		 * ResourceManager.get().addImage(image4 + ".gif"); imageStrings[4] =
		 * image4 + ".gif"; } else { images[4] = null; imageStrings[4] = null; }
		 * 
		 * if (image5 != null) { images[5] =
		 * ResourceManager.get().addImage(image5 + ".gif"); imageStrings[5] =
		 * image5 + ".gif"; } else { images[5] = null; imageStrings[5] = null; }
		 * 
		 * if (image6 != null) { images[6] =
		 * ResourceManager.get().addImage(image6 + ".gif"); imageStrings[6] =
		 * image6 + ".gif"; } else { images[6] = null; imageStrings[6] = null; }
		 */

		setUpGUI();
	}

	public void setUpGUI() {
		face = PhotoRoom.FORWARD;

		layoutManager = new ArrowButtonLayoutManager(this);

		left = new ArrowButton(getSandbox(), this, "Left");
		left.face = ArrowButton.allFace;
		left.position = ArrowButton.LEFT;
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (face == FORWARD)
					face = LEFT;
				else
					face--;
				if (imageStrings[face] == null) {
					while (imageStrings[face] == null) {
						turnLeft();
					}
				}
			}
		});
		layoutManager.addButton(left);

		right = new ArrowButton(getSandbox(), this, "Right");
		right.face = ArrowButton.allFace;
		right.position = ArrowButton.RIGHT;
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (face == LEFT)
					face = FORWARD;
				else
					face++;
				if (imageStrings[face] == null) {
					while (imageStrings[face] == null) {
						turnRight();
					}
				}
			}
		});
		layoutManager.addButton(right);

		forward = new ArrowButton(getSandbox(), this, "Forward");
		forward.face = ArrowButton.fFace;
		forward.width = 53;
		forward.height = 64;
		forward.position = ArrowButton.FORWARD;
		forward.image = ResourceManager.get().images.getImage("forward.gif");

		layoutManager.addButton(forward);

		topLeft = new ArrowButton(getSandbox(), this, "Top Left");
		topLeft.face = ArrowButton.allFace;
		topLeft.position = ArrowButton.TOPLEFT;
		topLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				face = TOPLEFT;
			}
		});
		layoutManager.addButton(topLeft);

		topRight = new ArrowButton(getSandbox(), this, "TOP RIGHT");
		topRight.face = ArrowButton.allFace;
		topRight.position = ArrowButton.TOPRIGHT;
		topRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				face = TOPRIGHT;
			}
		});
		layoutManager.addButton(topLeft);

		up = new ArrowButton(getSandbox(), this, "UP");
		up.face = ArrowButton.allFace;
		up.position = ArrowButton.UP;
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				face = UP;
			}
		});
		layoutManager.addButton(up);

		layoutManager.vadlidate();
	}

	public void paint(Graphics2D g) {
		paintImage(g);
		layoutManager.paint(g);
	}

	@Override
	public void update() {
		if (!imagesLoaded)
			loadImages();
		layoutManager.update();
	}
}