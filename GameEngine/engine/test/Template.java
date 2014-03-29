package engine.test;

import engine.core.JGE;
import engine.core.input.KeyboardInput;
import engine.core.input.MouseInput;

public class Template implements Runnable{

	// Main thread
		private Thread thread;
		private boolean isRunning;
		private long lastFpsTime;
		private int fps, framesPerSecond;
		
		public Template () {
			isRunning = true;
			thread = new Thread(this);
			thread.start();
			
			
		}
		
		public static void main(String[]args) {
			JGE.INIT();
			Template s = new Template();
		}
		/**
		 * Main game loop
		 */
		@Override
		public void run() {
			long lastLoopTime = System.nanoTime();

			while (isRunning) {
				long now = System.nanoTime();
				long updateLength = now - lastLoopTime;
				lastLoopTime = now;

				// update the frame counter
				lastFpsTime += updateLength;
				fps++;

				if (lastFpsTime >= 1000000000) {
					framesPerSecond = fps;
					//System.out.println("(FPS: " + fps + ")");
					lastFpsTime = 0;
					fps = 0;
				}

				// Update keyboard
				KeyboardInput.get().poll();

				// Update mouse
				MouseInput.get().poll();

				// Render the graphics
				JGE.CLEAR_SCREEN();
				
				JGE.BLIT_SURFACE();
				
				try {
					// System.out.println("Sleep time:"
					// + ((lastLoopTime - System.nanoTime()) / 1000000 + 10));
					Thread.sleep((lastLoopTime - System.nanoTime()) / 1000000 + 10);

				} catch (Exception ex) {
					// System.out.println(ex);
				}
			}
		}
}
