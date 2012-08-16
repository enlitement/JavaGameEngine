package unused;

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite{

	public double xpos, ypos;
	public double dx, dy;
	public Image img;
	public String imgName;
	public Rectangle rec;
	
	public Sprite(int x, int y) {
		xpos = x;
		ypos = y; 
		dx = dy = 0;
	}	
	
	public Sprite() {
		
	}
}
