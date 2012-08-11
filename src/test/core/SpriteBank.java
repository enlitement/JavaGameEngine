package test.core;

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

public class SpriteBank {

	public HashMap<String, Image> hm = new HashMap<String, Image>(30);

	public String path = "C://Users//Helson//Desktop//Images//";

	public SpriteBank() {

	}

	/**
	 * Finds the image requested at the path specified.
	 * 
	 * @param sprName
	 *            Name of the sprite
	 * @return Returns the sprite
	 */
	public synchronized Image loadSprite(String sprName) {
		// Attempts to load sprite sprName
		Image spr = null;
		try {
			spr = ImageIO.read(new File(path + sprName));
		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
		return spr;
	}

	/**
	 * Checks if a sprite has already been added/loaded into the hash map
	 * 
	 * @param sprName
	 *            Name of the sprite
	 * @return A boolean
	 */
	public boolean sprExists(String sprName) {
		// checks if a sprite has already been added/loaded
		// into the hash map
		boolean b = hm.containsKey(sprName);
		return b;
	}

	/**
	 * Checks if the image requested has already been loaded. If it hasn't, it
	 * retrieves the image and saves it in the hashmap.
	 * 
	 * @param sprName
	 *            Name of the sprite
	 * @return The image requested
	 */
	public synchronized Image getSprite(String sprName) {
		// used by other objects to load/get their image
		// that represents their sprite name.
		if (sprExists(sprName)) {
			// Sprite already loaded.
			return hm.get(sprName);
		} else {
			// Sprite hasn't been loaded yet.
			Image spr;
			spr = loadSprite(sprName);
			if (spr == null) {
				// An error occured, couldn't load sprite.
				return null;
			} // else move on

			// save the loaded sprite (and now transparent) into the hashmap
			hm.put(sprName, spr);
			// and then return the sprite
			return spr;
		}
	}

	/**
	 * @return The number of images loaded into memory
	 */
	public synchronized int size() {
		// return the number of images loaded into memory
		return hm.size();
	}

	/**
	 * Clears unused sprites. Empty
	 */
	public void clearSprites() {

	}
}
