package engine.gui;

import engine.core.Sandbox;

public class MenuButton extends Button {

	public MenuButton(Sandbox sandbox, String text) {
		super(sandbox, text);
	}

	public MenuButton(Sandbox sandbox, String text, int x, int y) {
		super(sandbox, text, x, y);
	}

	@Override
	public void update() {
		processInput();
		clickEffects();
	}
}