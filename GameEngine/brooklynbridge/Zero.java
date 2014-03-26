package brooklynbridge;

import java.awt.Graphics2D;
import java.awt.Image;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;

public class Zero extends PhotoRoom {

	public ArrowButton forward;
	public ArrowButtonLayoutManager layoutManager;
	
	public Zero(Sandbox sandbox, RoomManager roomManager) {
		super(sandbox, roomManager);

		images = new Image[4];
		imageStrings = new String[4];
		
		for (int i = 0; i < 4; i++) {
			images[i] = ResourceManager.get().addImage("beginning/" + i
					+ "0.gif");
			imageStrings[i] = "beginning/" + i + "0.gif";
		}

		face = PhotoRoom.FORWARD;

		layoutManager = new ArrowButtonLayoutManager(this);

		forward = new ArrowButton(sandbox, this, "Forward");
		forward.width = 53;
		forward.height = 64;
		forward.face = ArrowButton.allFace;
		forward.position = ArrowButton.FORWARD;
		forward.image = ResourceManager.get().images.getImage("forward.gif");

		layoutManager.addButton(forward);
		layoutManager.vadlidate();
	}

	public void paint(Graphics2D g) {
		if (face < images.length)
			paintImage(g);
		layoutManager.paint(g);
	}

	public void loadImages() {
		for (int i = 0; i < images.length; i++) {
			if (images[i] == null && imageStrings[i] != null && i<face+1)
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
