package engine.gui;

public class RelativeLayoutSpecification {
	private AbstractButton button;
	public double percentOfScreenWidth, percentOfScreenHeight, bX, bY;

	public RelativeLayoutSpecification(AbstractButton button) {
		this.button = button;
	}

	public AbstractButton getButton() {
		return button;
	}

}
