package engine.test;

import java.awt.Graphics2D;

public class PlayState extends State {
	
	public World world;
	
	public PlayState(StateManager manager) {
		super(manager);
		world = new World(this);
	}

	@Override
	public void paint(Graphics2D g) {
		world.paint(g);
	}

	@Override
	public void update() {
		world.update();
	}

}
