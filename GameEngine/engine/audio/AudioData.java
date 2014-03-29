package engine.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;

/**
 * 
 * @author http://www.cs.unc.edu/~luv/teaching/COMP110/programs/AudioPlayer.java
 * 
 */
public class AudioData {
	public AudioInputStream audioInputStream = null;
	public DataLine dataLine = null;
	public PlayThread thread = null;
}
