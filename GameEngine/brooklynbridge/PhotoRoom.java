package brooklynbridge;

import java.awt.Graphics2D;
import java.awt.Image;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;
import engine.rooms.Room;

/**
 * Image num is defined from forward going clockwise.
 * @author helson.taveras
 *
 */
public class PhotoRoom extends Room{
	
	public Image[] images;
	public String[] imageStrings;
	public boolean imagesLoaded;
	public int face;
	public static final int FORWARD = 0, RIGHT = 1, BACKWARD = 2, LEFT = 3;
	public RoomManager roomManager;
	
	public PhotoRoom(Sandbox sandbox, RoomManager roomManager) {
		super(sandbox);
		this.roomManager = roomManager;
	}
	
	public void paintImage(Graphics2D g ) {
		g.drawImage(getCurrentImage(), 0, 0, null);
	}
	
	@Override
	public void paint(Graphics2D g) {
		
	}

	@Override
	public void update() {
		
	}
	
	public void turnRight() {
		if(face==LEFT) 
			face = FORWARD;
		else 
			face++;
	}
	
	public void turnLeft() {
		if(face == FORWARD) 
			face = LEFT;
		else
			face--;
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
	
	public Image getImage(int imageNum) {
		return images[imageNum];
	}
	
	public Image getCurrentImage() {
		return images[face];
	}
	
	public void setCurrentImage(int imageNum){
		face = imageNum;
	}
}
