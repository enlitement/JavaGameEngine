package brooklynbridge;

import java.awt.Graphics2D;
import java.awt.Image;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

public class FourSidedPhotoRoom extends PhotoRoom {

	public ArrowButtonLayoutManager layoutManager;
	public ArrowButton left, right, forward;

	/**
	 * Example: getSandbox(), this, "beginning/4";
	 * @param sandbox
	 * @param roomManager
	 * @param imageNumber
	 */
	public FourSidedPhotoRoom(Sandbox sandbox, RoomManager roomManager, String imageNumber) {
		super(sandbox, roomManager);
		
		imagesLoaded = false;

		imageStrings = new String[4];
		images = new Image[4];

		for(int i = 0; i < images.length; i ++) {
			images[i] = ResourceManager.get().addImage(imageNumber + "" + i + ".gif");
			imageStrings[i] = imageNumber + "" + i + ".gif";
		}
		
		setUpGUI();
	}
	/*
	 * No need to specify .gif
	 */
	public FourSidedPhotoRoom(Sandbox sandbox, RoomManager roomManager,
			String image0, String image1, String image2, String image3) {
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