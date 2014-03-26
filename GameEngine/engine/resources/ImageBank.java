package engine.resources;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.imageio.ImageIO;

import engine.logger.Logger;

/**
 * Handles the loading of images/sprites. Other objects use this class to
 * access/load their images so that no image is loaded twice and that all images
 * are handled neatly and separately.
 * 
 * @author Helson
 * 
 */
public final class ImageBank {

	private static Properties prop = System.getProperties();
	private static String userDir;

	private HashMap<String, Image> hm = new HashMap<String, Image>(30);

	public ImageBank() {
		userDir = getUserDir();
	}

	public static void setUserDir(String dir) {
		userDir = dir;
	}

	public static String getUserDir() {
		return prop.getProperty("user.dir", null) + "/";
	}

	public void clearImages() {
		hm.clear();
	}

	/**
	 * Returns an optimized image for faster use and manipulation.
	 * 
	 * @param image
	 *            The image to convert.
	 * @return An optimized image.
	 */
	private BufferedImage toCompatibleImage(BufferedImage image) {
		// obtain the current system graphical settings
		GraphicsConfiguration gfx_config = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		/*
		 * if image is already compatible and optimized for current system
		 * settings, simply return it
		 */
		if (image.getColorModel().equals(gfx_config.getColorModel()))
			return image;

		// image is not optimized, so create a new image that is
		BufferedImage new_image = gfx_config.createCompatibleImage(
				image.getWidth(), image.getHeight(), image.getTransparency());

		// get the graphics context of the new image to draw the old image on
		Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		// actually draw the image and dispose of context no longer needed
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		// return the new optimized image
		return new_image;
	}
	
	public String getFileSeperator() {
		return System.getProperty("file.separator");
	}
	
	/**
	 * Finds the image requested at the path specified.
	 * 
	 * @param imgName
	 *            Name of the sprite
	 * @return Returns the sprite
	 * @throws IOException
	 */
	public Image loadImage(String imgName) throws IOException,
			IllegalArgumentException {
		// Attempts to load sprite sprName
		Logger.log("LOADING IMAGE:[" + imgName + "]");
		return (Image) ImageIO
				.read(this.getClass().getResource(imgName));
	}

	/**
	 * Checks if a sprite has already been added/loaded into the hash map
	 * 
	 * @param imgName
	 *            Name of the sprite
	 * @return A boolean
	 */
	public boolean imgExists(String imgName) {
		// checks if a sprite has already been added/loaded
		// into the hash map
		boolean b = hm.containsKey(imgName);
		return b;
	}

	/**
	 * Checks if the image requested has already been loaded. If it hasn't, it
	 * retrieves the image and saves it in the hashmap.
	 * 
	 * @param imgName
	 *            Name of the sprite
	 * @return The image requested
	 * @throws IOException
	 */
	public Image getImage(String imgName) throws IOException,
			IllegalArgumentException {
		// used by other objects to load/get their image
		// that represents their sprite name.
		if (imgExists(imgName)) {
			// Sprite already loaded.
			return hm.get(imgName);
		} else {
			// Sprite hasn't been loaded yet.
			Image img;
			img = loadImage(imgName);
			if (img == null) {
				// An error occured, couldn't load sprite.
				return null;
			} // else move on

			// save the loaded sprite (and now transparent) into the hashmap
			hm.put(imgName, img);
			// and then return the sprite
			return img;
		}
	}

	/**
	 * Checks if the image requested has already been added. If it hasn't, then
	 * it adds the image to the bank.
	 * 
	 * @param imgName
	 *            Name of the sprite
	 * @return The image requested
	 */
	public void addImage(String imageName, Image image) {
		// Add an image to the sprite bank.
		if (imgExists(imageName)) {
			// Sprite already loaded.
			return;
		} else {
			// save the loaded sprite (and now transparent) into the hashmap
			hm.put(imageName, toCompatibleImage((BufferedImage) image));
		}
	}

	/**
	 * @return The number of images loaded into memory
	 */
	public int size() {
		// return the number of images loaded into memory
		return hm.size();
	}
}
