package test.core.resources;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import test.core.GameComponent;

public class ResourceManager extends GameComponent implements Runnable {
	public ImageBank images;
	public Image imageLoad;

	public AtomicBoolean loading;
	public AtomicInteger numLoaded = new AtomicInteger();
	public AtomicInteger numTotal = new AtomicInteger();
	// public AudioBank audios;
	// public AudioStream audioLoad;

	public List<String> loadQueue = new ArrayList<String>();

	public ResourceManager() {
		super();
		loading = new AtomicBoolean(false);
		images = new ImageBank();
		imageLoad = null;
	}

	/**
	 * @return The size of the sprite queue.
	 */
	public int getUnprocessed() {
		return loadQueue.size();
	}

	/**
	 * @return The size of the sprite hashmap.
	 */
	public int getProcessed() {
		return images.size(); // + audios.size();
	}

	/**
	 * @return The number of sprites in the hashmap divided by the total number
	 *         of sprites that need to be processed
	 */
	public synchronized double getPercentComplete() {
		if (!loadQueue.isEmpty())
			return (numTotal.get() - getUnprocessed())
					/ (double) numTotal.get();
		return 0;
	}

	/**
	 * Gets the image with the specified name. Be sure that you have loaded the
	 * image before using this method!
	 * 
	 * @param name
	 *            Name of the image
	 * @return The image
	 */
	public Image getImage(String name) {
		if (images.getImage(name) != null)
			return images.getImage(name);
		else {
			System.out.println("The image you requested:" + name
					+ " is null. Please use addImage() and get the image "
					+ "requested before assigning it to an object.");
			return null;
		}
	}

	/*
	 * public AudioStream getAudio(String name) { return audios.getAudio(name);
	 * }
	 */
	public Image addImage(String imageName) {
		if (!loadQueue.contains(imageName)) {
			loadQueue.add(imageName);
			loading.set(true);
		} else if (images.imgExists(imageName))
			return images.hm.get(imageName);
		return null;
	}

	public void load() {
		if (loading.get()) {
			System.out.println("Request receieved");
			numTotal.set(loadQueue.size());
			System.out.printf("totalNum %d\n", numTotal.get());
			for (int i = 0; i < loadQueue.size(); i++) {
				if (loadQueue.get(i) != null) {
					String key = loadQueue.get(i);
					imageLoad = images.getImage(key);
					loadQueue.remove(i);
					System.out.println("Percent:" + getPercentComplete());
					System.out.println("Left in queue: " + getUnprocessed());
					numLoaded.getAndIncrement();
				}
			}
			System.out.printf("totalNum %n\n", numTotal.get());
			System.out.printf("totalNum %n\n", numTotal.get());
			System.out.println("Final Percent:" + getPercentComplete());
			if (loadQueue.isEmpty()) {
				loading.set(false);
				numLoaded.set(0);
				numTotal.set(0);
			}
		}
	}

	@Override
	public void run() {
		if (loading.get())
			load();
	}
}
