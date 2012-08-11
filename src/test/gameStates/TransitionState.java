package test.gameStates;

import java.awt.Color;
import java.awt.Graphics;

import test.core.AbstractStateManager;
import test.core.StateManager;

public class TransitionState extends AbstractTransitionState {

	public TransitionState(AbstractStateManager stateManager, State state) {
		super(stateManager, state);
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, unused.Main.GWidth, unused.Main.GHeight);
		
		g.setColor(Color.white);
		g.drawString("Loading...", unused.Main.GWidth / 2 - 20,
				unused.Main.GHeight / 2 - 40);
		
		g.drawRect(10,180,unused.Main.GWidth-20,20);
		g.fillRect(10, 180, (int) (getPercent()*(unused.Main.GWidth-20)), 20);
	}

	@Override
	public void update() {
		
	}
}
