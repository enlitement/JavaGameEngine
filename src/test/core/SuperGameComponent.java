package test.core;

import java.awt.image.BufferStrategy;

public class SuperGameComponent {
	
	public final static Game game = new Game();
	
	public SuperGameComponent() {
		
	}
	
	public BufferStrategy getStrategy() {
		return game.getBufferStrategy();
	}
}
