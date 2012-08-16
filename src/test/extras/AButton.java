package test.extras;

import test.core.Sandbox;

public class AButton extends Button {

	public AButton(Sandbox sandbox, String text, int x, int y) {
		super(sandbox, text, x, y);
	}

	
	@Override
	public void update() {
		processInput();
		if(pressed)
			enableClickEffect();
		else
			revertClickEffect();
	}
	
	@Override
	protected void processInput() {
		if(mouseWithinBounds(getMouse()) && getMouse().buttonDownOnce(1)) {
			System.out.println("Clicked");
			pressed = true;
		}
		else if(!getMouse().buttonDown(1))
			pressed = false;
	}
}