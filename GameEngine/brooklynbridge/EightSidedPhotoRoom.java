package brooklynbridge;

import java.awt.Graphics2D;
import java.awt.Image;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

public class EightSidedPhotoRoom extends PhotoRoom {

	public final int TOPLEFT = 4, UP = 5, TOPRIGHT = 6;
	public ArrowButtonLayoutManager layoutManager;
	public ArrowButton left, right, forward, topLeft, topRight, up;

	/**
	 * Example: getSandbox(), this, "beginning/4";
	 * @param sandbox
	 * @param roomManager
	 * @param imageNumber
	 */
	public EightSidedPhotoRoom(Sandbox sandbox, RoomManager roomManager, String imageNumber) {
		super(sandbox, roomManager);
		
		imagesLoaded = false;

		imageStrings = new String[8];
		images = new Image[8];

		for(int i = 0; i < images.length; i ++) {
			images[i] = ResourceManager.get().addImage(imageNumber + "" + i + ".gif");
			imageStrings[i] = imageNumber + "" + i + ".gif";
		}
		
		setUpGUI();
	}
	/*
	 * No need to specify .gif
	 */
	public EightSidedPhotoRoom(Sandbox sandbox, RoomManager roomManager,
			String image0, String image1, String image2, String image3, String image4, String image5, String image6, String image7) {
		super(sandbox, roomManager);

		imagesLoaded = false;

		imageStrings = new String[4];
		images = new Image[4];
		if (image0 != null) {
			images[0] = ResourceManager.get().addImage(image0 + ".gif");
			imageStrings[0] = image0 + ".gif";
		} else {
			images[0] = null;
			imageStrings[0] = null;
		}

		if (image1 != null) {
			images[1] = ResourceManager.get().addImage(image1 + ".gif");
			imageStrings[1] = image1 + ".gif";
		} else {
			images[1] = null;
			imageStrings[1] = null;
		}

		if (image2 != null) {
			images[2] = ResourceManager.get().addImage(image2 + ".gif");
			imageStrings[2] = image2 + ".gif";
		} else {
			images[2] = null;
			imageStrings[2] = null;
		}

		if (image3 != null) {
			images[3] = ResourceManager.get().addImage(image3 + ".gif");
			imageStrings[3] = image3 + ".gif";
		} else {
			images[3] = null;
			imageStrings[3] = null;
		}
		
		if (image4 != null) {
			images[4] = ResourceManager.get().addImage(image4 + ".gif");
			imageStrings[4] = image4 + ".gif";
		} else {
			images[4] = null;
			imageStrings[4] = null;
		}
		
		if (image5 != null) {
			images[5] = ResourceManager.get().addImage(image5 + ".gif");
			imageStrings[5] = image5 + ".gif";
		} else {
			images[5] = null;
			imageStrings[5] = null;
		}
		
		if (image6 != null) {
			images[6] = ResourceManager.get().addImage(image6 + ".gif");
			imageStrings[6] = image6 + ".gif";
		} else {
			images[6] = null;
			imageStrings[6] = null;
		}
		
		if (image7 != null) {
			images[7] = ResourceManager.get().addImage(image7 + ".gif");
			imageStrings[7] = image7 + ".gif";
		} else {
			images[7] = null;
			imageStrings[7] = null;
		}

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
		
		forward = new ArrowButton(getSandbox(), this, "Forward");
		forward.face = ArrowButton.fFace;
		forward.width = 53;
		forward.height = 64;
		forward.position = ArrowButton.FORWARD;
		forward.image = ResourceManager.get().images.getImage("forward.gif");

		layoutManager.addButton(forward);

		layoutManager.vadlidate();
	}
	public void paint(Graphics2D g) {
		paintImage(g);
		layoutManager.paint(g);
	}

	public void loadImages() {
		for (int i = 0; i < images.length; i++) {
			if (images[i] == null && imageStrings[i] != null)
				images[i] = ResourceManager.get().images
						.getImage(imageStrings[i]);
		}
		boolean next = true;
		for(int i = 0; i < images.length; i++) {
			if(images[i]!=null && next)
				next = true;
			else
				next = false;
		}
		if(next == true) {
			imagesLoaded = true;
			System.out.println("Image loading completed");
		}
	}


	@Override
	public void update() {
		if (!imagesLoaded)
			loadImages();
		layoutManager.update();
	}
}