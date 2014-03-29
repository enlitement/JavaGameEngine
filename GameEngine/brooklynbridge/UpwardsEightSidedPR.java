package brooklynbridge;

import java.awt.Graphics2D;
import java.awt.Image;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

public class UpwardsEightSidedPR extends PhotoRoom {

	public final int TOPLEFT = 15, UP = 8, TOPRIGHT = 12;
	public ArrowButtonLayoutManager layoutManager;
	public ArrowButton left, right, forward, topLeft, topRight, up, down;

	/**
	 * Example: getSandbox(), this, "beginning/4";
	 * 
	 * @param sandbox
	 * @param roomManager
	 * @param imageNumber
	 */
	public UpwardsEightSidedPR(Sandbox sandbox, RoomManager roomManager,
			String imageNumber) {
		super(sandbox, roomManager);

		imagesLoaded = false;

		imageStrings = new String[16];
		images = new Image[16];

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
	public UpwardsEightSidedPR(Sandbox sandbox, RoomManager roomManager,
			String[] strings) {
		super(sandbox, roomManager);

		imagesLoaded = false;

		imageStrings = new String[16];
		images = new Image[16];

		for (int i = 0; i < imageStrings.length; i++) {
			if (strings[i] != null) {
				images[i] = ResourceManager.get().addImage(strings[i] + ".gif");
				imageStrings[i] = strings[i] + ".gif";
			}
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
				else if (face == UP)
					face = 15;
				else
					turnLeft();
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
					turnRight();
				if (imageStrings[face] == null) {
					while (imageStrings[face] == null) {
						turnRight();
					}
				}
			}
		});
		layoutManager.addButton(right);

		down = new ArrowButton(getSandbox(), this, "Down");
		down.face = ArrowButton.topFace;
		down.position = ArrowButton.DOWN;
		down.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (face > 7) {
					face -= 8;
					//if(face<0)
					//	face+=16;
				}
				System.out.println("Face:"+face);
				if (imageStrings[face] == null) {
					while (imageStrings[face] == null) {
						if(face==LEFT) 
							face = FORWARD;
						else 
							face++;
						if(face>7)
							face = 0;
					}
				}
			}
		});
		layoutManager.addButton(down);

		topLeft = new ArrowButton(getSandbox(), this, "Top Left");
		topLeft.face = ArrowButton.medFace;
		topLeft.position = ArrowButton.TOPLEFT;
		topLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (face > 3 && face < 8)
					face += 4;

				if (face > 0 && face < 4)
					face += 11;

				if (face == 0)
					face = 15;

				if (imageStrings[face] == null) {
					while (imageStrings[face] == null) {
						turnLeft();
					}
				}
			}
		});
		
		layoutManager.addButton(topLeft);

		topRight = new ArrowButton(getSandbox(), this, "TOP RIGHT");
		topRight.face = ArrowButton.medFace;
		topRight.position = ArrowButton.TOPRIGHT;
		topRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (face > 3 && face < 8)
					face += 5;
				if (face > 0 && face < 4)
					face += 12;
				if (face == 7)
					face = 8;
				if (imageStrings[face] == null)
					while (imageStrings[face] == null)
						turnRight();

			}
		});
		layoutManager.addButton(topRight);

		up = new ArrowButton(getSandbox(), this, "UP");
		up.face = ArrowButton.medFace;
		up.position = ArrowButton.UP;
		up.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				face = UP;
				if (imageStrings[face] == null)
					while (imageStrings[face] == null)
						turnRight();
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

	@Override
	public void turnRight() {
		if (face == LEFT)
			face = FORWARD;
		else if (face > 7) {
			if (face == 8)
				face = 12;
			else if (face == 12)
				face = 9;
			else if (face == 9)
				face = 13;
			else if (face == 13)
				face = 10;
			else if (face == 10)
				face = 14;
			else if (face == 14)
				face = 11;
			else if (face == 11)
				face = 15;
			else if (face == 15)
				face = 8;
		} else 
			face++;
	}

	@Override
	public void turnLeft() {
		if (face == FORWARD)
			face = LEFT;
		else if (face > 7) {
			if (face == 12)
				face = 8;
			else if (face == 9)
				face = 12;
			else if (face == 13)
				face = 9;
			else if (face == 10)
				face = 13;
			else if (face == 14)
				face = 10;
			else if (face == 11)
				face = 14;
			else if (face == 15)
				face = 11;
			else if (face == 8)
				face = 15;
		} else 
			face--;
	}

	public void loadImages() {
		for (int i = 0; i < images.length; i++) {
			if (images[i] == null && imageStrings[i] != null)
				images[i] = ResourceManager.get().images
						.getImage(imageStrings[i]);
		}
		boolean next = true;
		for (int i = 0; i < images.length; i++) {
			if (images[i] != null && next)
				next = true;
			else
				next = false;
		}
		if (next == true) {
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