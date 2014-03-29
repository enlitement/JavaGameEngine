package engine.utilities;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageManipulator {

	public static Image getSubimage(Image image, int x, int y, int w, int h) {
		return ((BufferedImage) image).getSubimage(x, y, w, h);
	}

	/**
	 * Rotate an image based on the specified angle.
	 * @param image The image to rotate
	 * @param angle Angle in radians.
	 * @return A rotated image.
	 */ 
	public static Image rotate(Image image, double angle) {
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage dimg = new BufferedImage(w, h,
				((BufferedImage) image).getType());
		Graphics2D g = dimg.createGraphics();
		g.rotate(angle, w / 2, h / 2);
		g.drawImage((BufferedImage) image, null, 0, 0);
		return dimg;
	}

	public static Image mirror(Image image, boolean horizontal) {
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage dimg = new BufferedImage(w, h,
				((BufferedImage) image).getType());
		Graphics2D g = dimg.createGraphics();
		if (horizontal)
			g.drawImage(image, 0, 0, w, h, w, 0, 0, h, null);
		else
			g.drawImage(image, 0, 0, w, h, 0, h, w, 0, null);
		g.dispose();
		return (Image) dimg;
	}
}
