package test.gameStates;

import java.awt.Color;
import java.awt.Graphics;

import test.core.StateManager;

/**
 * The Transition State is a state that is created between the transitioning between 
 * one state and another that requires loading new resources from the Resource
 * Manager. It features a loading bar
 * @author Helson
 *
 */
public class TransitionState extends AbstractTransitionState {

	public TransitionState(StateManager stateManager, State state) {
		super(stateManager, state);
	}
	
	public void paint(Graphics g) {
		loadingBar.paint(g);
	}
	/*public void paint(Graphics g) {
		/*g.setColor(Color.black);
		g.fillRect(0, 0, unused.Main.GWidth, unused.Main.GHeight);
		
		g.setColor(Color.white);
		g.drawString("Loading...", unused.Main.GWidth / 2 - 20,
				unused.Main.GHeight / 2 - 40);
		
		g.drawRect(10,180,unused.Main.GWidth-20,20);
		g.fillRect(10, 180, (int) (getPercent()*(unused.Main.GWidth-20)), 20);
	}*/

	@Override
	public void update() {
		checkLoad();
	}
}
