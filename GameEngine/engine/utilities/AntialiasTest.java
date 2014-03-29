package engine.utilities;

import java.awt.Graphics2D;

import engine.core.IUpdate;
import engine.core.JGE;

public class AntialiasTest {

	public AntialiasTest() {
		JGE.setGameLoop(new IUpdate() {

			@Override
			public void onPaint(Graphics2D g) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onUpdate() {

			}
		});
	}
	
	public void antialias(Graphics2D g) {
		
	}
	public static void main(String[] args) {

	}
}
