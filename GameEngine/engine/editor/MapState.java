package engine.editor;

import java.awt.Graphics2D;

import engine.test.State;
import engine.test.StateManager;

public class MapState extends State{

	public Editor editor;
	
	public MapState(StateManager manager) {
		super(manager);
		editor = new Editor(this);
	}
	@Override
	public void paint(Graphics2D g) {
		editor.paint(g);
	}

	@Override
	public void update() {
		editor.update();
	}
}
