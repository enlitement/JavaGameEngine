package test.core.resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import sun.audio.AudioStream;

//http://www.javaprogrammingforums.com/whats-wrong-my-code/9116-could-not-create-audio-stream-input-stream.html

@SuppressWarnings("restriction")
public class AudioBank {
	public final String path = "C://Users//Helson//Desktop//Images//";
	public HashMap<String, AudioStream> hm = new HashMap<String, AudioStream>(
			30);

	public AudioBank() {
	}

	/**
	 * Finds the sound requested at the path specified.
	 * 
	 * @param audioName
	 *            Name of the sound
	 * @return Returns the sound
	 */
	public synchronized AudioStream loadAudio(String audioName) {
		// Attempts to load sound audioName
		AudioStream stream = null;
		try {
			InputStream in = new FileInputStream(path + audioName);
			stream = new AudioStream(in);
		} catch (IOException e) {
			System.out.println(e);
			return null;
		}
		return stream;
	}

	/**
	 * Checks if a sound has already been added/loaded into the hash map
	 * 
	 * @param audioName
	 *            Name of the sound
	 * @return A boolean
	 */
	public boolean audioExists(String audioName) {
		// checks if the sound has already been added/loaded
		// into the hash map
		return hm.containsKey(audioName);
	}

	/**
	 * Checks if the sound requested has already been loaded. If it hasn't, it
	 * retrieves the sound and saves it in the hashmap.
	 * 
	 * @param audioName
	 *            Name of the sound
	 * @return The image requested
	 */
	public synchronized AudioStream getAudio(String audioName) {
		// used by other objects to load/get their sound
		// that represents their sound name.
		if (audioExists(audioName)) {
			// Sound already loaded.
			return hm.get(audioName);
		} else {
			// Sound hasn't been loaded yet.
			AudioStream audio;
			audio = loadAudio(audioName);
			if (audio == null) {
				// An error occured, couldn't load sound.
				return null;
			} // else move on

			// save the loaded sound (and now transparent) into the hashmap
			hm.put(audioName, audio);
			// and then return the sound
			return audio;
		}
	}

	/**
	 * @return The number of sound loaded into memory
	 */
	public synchronized int size() {
		// return the number of sound loaded into memory
		return hm.size();
	}
}
