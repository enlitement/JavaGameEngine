package engine.audio;

/**
 * 
 * @author http://www.cs.unc.edu/~luv/teaching/COMP110/programs/AudioPlayer.java
 *
 */
abstract class PlayThread extends Thread {
	public abstract void setLooping(boolean loop);

	public abstract void playSound();

	public abstract void stopSound();
}