package engine.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import engine.graphics.GraphicsManager;
import engine.input.KeyboardInput;
import engine.input.MouseInput;
import engine.logger.Logger;
import engine.utilities.TextUtilities;

public class GameLoop {

	private static ThreadLoop loop;
	public IUpdate update;
	private static GameLoop game_loop;
	static boolean isStarted;

	/**
	 * Returns the GameLoop object associated with the game.
	 * 
	 * @return
	 */
	public static GameLoop get() {
		if (game_loop != null)
			return game_loop;
		else
			return createGameLoop();
	}

	/**
	 * If the Game Loop isn't created by using a lock to determine whether the
	 * engine is initialized, then it will most likely throw an error in the
	 * Graphics subsystem.
	 * 
	 * @return A secure GameLoop.
	 */
	private synchronized static GameLoop createGameLoop() {
		if (!JGE.init_lock.ready) {
			try {
				JGE.init_lock.wait();
				return new GameLoop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			isStarted = true;
		} else {
			isStarted = true;
			return new GameLoop();
		}
		return null;
	}

	private GameLoop() {
		loop = new ThreadLoop();
	}

	public void setLoop(IUpdate update) {
		this.update = update;
	}

	/**
	 * This class contains the actual thread of the loop.
	 * 
	 * @author Helson July 20, 2013.
	 */
	private class ThreadLoop implements Runnable {
		private Thread thread;

		// desired fps
		private final int MAX_FPS = JGE.FRAMES_PER_SECOND;
		// maximum number of frames to be skipped
		private final int MAX_FRAME_SKIPS = 5;
		// the frame period
		private final int FRAME_PERIOD = 1000 / MAX_FPS;

		// Stuff for stats */
		private DecimalFormat df = new DecimalFormat("0.##"); // 2 dp
		// we'll be reading the stats every second
		private final static int STAT_INTERVAL = 1000; // ms
		// the average will be calculated by storing
		// the last n FPSs
		private final static int FPS_HISTORY_NR = 10;
		// last time the status was stored
		private long lastStatusStore = 0;
		// the status time counter
		private long statusIntervalTimer = 0l;
		// number of frames skipped since the game started
		private long totalFramesSkipped = 0l;
		// number of frames skipped in a store cycle (1 sec)
		private long framesSkippedPerStatCycle = 0l;

		// number of rendered frames in an interval
		private int frameCountPerStatCycle = 0;
		private long totalFrameCount = 0l;
		// the last FPS values
		private double fpsStore[];
		// the number of times the stat has been read
		private long statsCount = 0;
		// the average FPS since the game started
		private double averageFps = 0.0;

		// flag to hold game state
		private boolean running;

		public void setRunning(boolean running) {
			this.running = running;
		}

		public ThreadLoop() {
			thread = new Thread(this);
			setRunning(true);
			thread.start();
		}

		public long beginTime; // the time when the cycle begun
		public long timeDiff; // the time it took for the cycle to execute
		public int sleepTime; // ms to sleep (<0 if we're behind)
		public int framesSkipped; // number of frames being skipped

		@Override
		public void run() {
			Logger.log("Starting game loop");
			// initialise timing elements for stat gathering
			initTimingElements();

			sleepTime = 0;

			while (running) {

				beginTime = System.currentTimeMillis();
				framesSkipped = 0; // resetting the frames skipped

				// DO STUFF HERE
				JGE.dispatcher.run();
				updateEngine();
				update.onUpdate();
				JGE.CLEAR_SCREEN();
				update.onPaint(GraphicsManager.graphics2D);
				JGE.BLIT_SURFACE();

				// calculate how long did the cycle take
				timeDiff = System.currentTimeMillis() - beginTime;
				// calculate sleep time
				sleepTime = (int) (FRAME_PERIOD - timeDiff);

				if (sleepTime > 0) {
					// if sleepTime > 0 we're OK
					try {
						// send the thread to sleep for a short period
						// very useful for battery saving
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
					}
				}

				while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
					// we need to catch up
					update.onUpdate(); // update without rendering
					sleepTime += FRAME_PERIOD; // add frame period to
												// check if in next
												// frame
					framesSkipped++;
				}

				if (framesSkipped > 0)
					Logger.log("Skipped:" + framesSkipped);

				// for statistics
				framesSkippedPerStatCycle += framesSkipped;
				// calling the routine to store the gathered statistics
				storeStats();
			}
		}

		/**
		 * The statistics - it is called every cycle, it checks if time since
		 * last store is greater than the statistics gathering period (1 sec)
		 * and if so it calculates the FPS for the last period and stores it.
		 * 
		 * It tracks the number of frames per period. The number of frames since
		 * the start of the period are summed up and the calculation takes part
		 * only if the next period and the frame count is reset to 0.
		 */
		private void storeStats() {
			frameCountPerStatCycle++;
			totalFrameCount++;

			// check the actual time
			statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

			if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL) {
				// calculate the actual frames pers status check interval
				double actualFps = (double) (frameCountPerStatCycle / (STAT_INTERVAL / 1000));

				// stores the latest fps in the array
				fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps;

				// increase the number of times statistics was calculated
				statsCount++;

				double totalFps = 0.0;
				// sum up the stored fps values
				for (int i = 0; i < FPS_HISTORY_NR; i++) {
					totalFps += fpsStore[i];
				}

				// obtain the average
				if (statsCount < FPS_HISTORY_NR) {
					// in case of the first 10 triggers
					averageFps = totalFps / statsCount;
				} else {
					averageFps = totalFps / FPS_HISTORY_NR;
				}
				// saving the number of total frames skipped
				totalFramesSkipped += framesSkippedPerStatCycle;
				// resetting the counters after a status record (1 sec)
				framesSkippedPerStatCycle = 0;
				statusIntervalTimer = 0;
				frameCountPerStatCycle = 0;

				statusIntervalTimer = System.currentTimeMillis();
				lastStatusStore = statusIntervalTimer;
			}
		}

		private void initTimingElements() {
			// initialise timing elements
			fpsStore = new double[FPS_HISTORY_NR];
			for (int i = 0; i < FPS_HISTORY_NR; i++) {
				fpsStore[i] = 0.0;
			}
			Logger.log(".initTimingElements()"
					+ "Timing elements for stats initialised");
		}

		private void updateEngine() {
			//KeyboardInput.get().poll();
			//MouseInput.get().poll();

		}

	}

	/**
	 * Returns the frames per second being achieved, rather than what is being
	 * desired.
	 * 
	 * @return
	 */
	public static double getAverageFPS() {
		return loop.averageFps;
	}

	/**
	 * Takes the time it takes to poll the keyboard, mouse, paint, and update
	 * the game.
	 * 
	 * @return
	 */
	public static long getLoopTime() {
		return loop.timeDiff;
	}

	public static int getSleepTime() {
		return loop.sleepTime;
	}

	public static int getFramesSkipped() {
		return loop.framesSkipped;
	}

	/**
	 * Paints the loop stats on a nice transparent rectangle.
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	public static void nicePaintLoopStats(Graphics2D g, int x, int y) {
		Color grey = new Color(0, 0, 0, 128);
		g.setColor(grey);
		g.fillRoundRect(x, y, 100, 75, 10, 10);
		g.setFont(TextUtilities.getDebugFont());
		g.setColor(Color.green);
		g.drawString("FPS:" + getAverageFPS(), x+ 5, y + 15);
		g.drawString("Skipped:" + getFramesSkipped(), x + 5, y + 25);
		g.drawString("Loop Time:" + getLoopTime(), x + 5, y + 35);
		g.drawString("Sleep Time:" + getSleepTime(), x + 5, y + 45);
	}

	public static void paintLoopStats(Graphics2D g, int x, int y) {
		g.drawString("FPS:" + getAverageFPS(), x, y + 10);
		g.drawString("Skipped:" + getFramesSkipped(), x, y + 20);
		g.drawString("Loop Time:" + getLoopTime(), x, y + 30);
		g.drawString("Sleep Time:" + getSleepTime(), x, y + 40);
	}
}
