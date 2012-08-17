package test.extras;

import test.core.Sandbox;
import test.core.input.MouseInput;

public class AButton extends Button{

	public AButton(Sandbox sandbox, String text) {
		super(sandbox, text);
	}

	public AButton(Sandbox sandbox, String text, int x, int y) {
		super(sandbox, text, x, y);
	}

	@Override
	public void update() {
		processInput();
		if (pressed) {
			enableClickEffect();
		}
		if (!pressed)
			revertClickEffect();
	}

	@Override
	protected void processInput() {
		if (mouseWithinBounds(MouseInput.getInstance()) && MouseInput.getInstance().buttonDownOnce(1)) {
			pressed = true;
		}
		if (!MouseInput.getInstance().buttonDown(1)) {
			if (pressed && mouseWithinBounds(MouseInput.getInstance()))
				clicked = true;
			pressed = false;
		}
		if (clicked && !mouseWithinBounds(MouseInput.getInstance())) {
			clicked = false;
		}
	}

	@Override
	public void addAction(ButtonAction button) {
		
	}
}