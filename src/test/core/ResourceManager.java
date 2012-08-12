package test.core;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import test.sprite.Sprite;

public class ResourceManager extends GameComponent implements Runnable {
	public SpriteBank sprites;
	public Sprite spriteLoad;

	public AtomicBoolean loading;
	// public AudioBank audios;
	// public AudioStream audioLoad;s

	public List<String> loadQueue = new ArrayList<String>();

	public int totalNum;

	public Thread resourceThread;

	public ResourceManager(Game game) {
		super(game);
		loading = new AtomicBoolean(false);
		sprites = new SpriteBank();
		spriteLoad = new Sprite();

		// audios = new AudioBank();

		resourceThread = new Thread(this, "Resource Manager");
		resourceThread.start();
		totalNum = 0;
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
		return sprites.size(); // + audios.size();
	}

	/**
	 * @return The number of sprites in the hashmap divided by the total number
	 *         of sprites that need to be processed
	 */
	public synchronized double getPercentComplete() {
		if (!loadQueue.isEmpty())
			return (totalNum - getUnprocessed()) / (double) totalNum;
		return 0;
	}

	public Image getSprite(String name) {
		return sprites.getSprite(name);
	}

	/*
	 * public AudioStream getAudio(String name) { return audios.getAudio(name);
	 * }
	 */
	public void addResource(String name, Sprite spr) {
		if (!loadQueue.contains(name) && !sprites.sprExists(name)) {// &&
																	// !audios.audioExists(name))
																	// {
			loadQueue.add(name);
			spriteLoad = spr;
		} else if (sprites.sprExists(name))
			spr.img = sprites.hm.get(name);
		//loading = true;
	}

	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void load() {
		//loading = true;
		while(!loading.get()) { }
		
		if(loading.get()) {
			System.out.println("Request receieved");
			totalNum = loadQueue.size();
			System.out.printf("totalNum %d\n", totalNum);
			for (int i = 0; i < loadQueue.size(); i++) {
				if (loadQueue.get(i) != null) {
					String key = loadQueue.get(i);
					spriteLoad.img = sprites.getSprite(key);
					spriteLoad.imgName = key;
					loadQueue.remove(i);
					System.out.println("Percent:" + getPercentComplete());
					System.out.println("Left in queue: " + getUnprocessed());
					sleep(1);
				}
			}
			System.out.printf("totalNum %n\n", totalNum);System.out.printf("totalNum %n\n", totalNum);
			System.out.println("Final Percent:" + getPercentComplete());
			if (loadQueue.isEmpty()) {
				totalNum = 0;
			}
			sleep(5);
		}
	}

	@Override
	public void run() {
		System.out.print("Thread - ResMan");
		if (!loading.get())
			load();
	}
}
