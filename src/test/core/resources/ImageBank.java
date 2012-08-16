package test.core.resources;

/*
 * SpriteBank class
 * Desc: Handles the loading of images/sprites.
 * Other objects use this class to access/load their images
 * so that no image is loaded twice and that all images are handled
 * neatly and seperately.
 */

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageBank {

	public HashMap<String, Image> hm = new HashMap<String, Image>(30);

	public String path = "C://Users//Helson//Desktop//Images//";

	public ImageBank() {

	}

	/**
	 * Finds the image requested at the path specified.
	 * 
	 * @param imgName
	 *            Name of the sprite
	 * @return Returns the sprite
	 */
	public synchronized Image loadImage(String imgName) {
		// Attempts to load sprite sprName
		Image img = null;
		try {
			img = ImageIO.read(new File(path + imgName));
		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
		return img;
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
	 */
	public synchronized Image getImage(String imgName) {
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
	 * @return The number of images loaded into memory
	 */
	public synchronized int size() {
		// return the number of images loaded into memory
		return hm.size();
	}
}
