package engine.resources;

import java.awt.Image;
import java.io.IOException;

import engine.logger.Logger;

/**
 * A class for managing Image resources.
 * 
 * @author Helson
 * 
 */
public class ResourceManager {

	private static ResourceManager resourceManager;
	private ImageBank images = new ImageBank();

	private ResourceManager() {
	}

	/**
	 * Gets the resourceManager singleton class.
	 * 
	 * @return The singleton class.
	 */
	public static synchronized ResourceManager get() {
		if (resourceManager == null) {
			resourceManager = new ResourceManager();
		}
		return resourceManager;
	}

	/**
	 * Returns if the image has already been loaded into the ImageBank.
	 * 
	 * @param name
	 *            The name of the image.
	 * @return True if it has been loaded; false if not.
	 */
	public boolean imageIsLoaded(String name) {
		return images.imgExists(name);
	}

	/**
	 * Sets the directory where the resources are to be found.
	 * 
	 * @param directory
	 *            The directory where the resources are.
	 */
	public void setResourceDirectory(String directory) {
		ImageBank.setUserDir(directory);
	}

	/**
	 * Gets the image with the specified name. Be sure that you have loaded the
	 * image before using this method!
	 * 
	 * @param name
	 *            Name of the image
	 * @return The image
	 * @throws IOException
	 */
	public Image getImage(String name) throws IOException,
			IllegalArgumentException {
		if (images.getImage(name) != null)
			return images.getImage(name);
		else {
			System.out.println("The image you requested:" + name
					+ " is null. Please use addImage() and get the image "
					+ "requested before assigning it to an object.");
			Logger.err("Image not found:" + ImageBank.getUserDir() + name + ".");
			return null;
		}
	}

	/**
	 * Adds the specified image to the ImageBank under the given imageName.
	 * 
	 * @param imageName
	 *            The name as it will be used to access it in the future
	 * @param image
	 *            The image to be added.
	 */
	public void addImage(String imageName, Image image) {
		images.addImage(imageName, image);
	}
}
