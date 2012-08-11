package test.gameStates;

import java.awt.Color;
import java.awt.Graphics;

import test.gameInterfaces.Paintable;

public class AbstractLoadingBar implements Paintable {

	public AbstractTransitionState state;
	public int barWidth, barHeight, barX, barY;
	public Color barColor;

	public AbstractLoadingBar(AbstractTransitionState state, int barWidth,
			int barHeight, Color barColor) {
		this.state = state;
		barX = (state.stateManager.canvas.getWidth() / 2) - (barWidth / 2);
		barY = (state.stateManager.canvas.getHeight() / 2) - (barHeight / 2);
		this.barColor = barColor;
	}

	/**
	 * Paint a round rectangle with the specified color in the middle of the
	 * screen.
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.drawRoundRect(barX, barY, barWidth, barHeight, 3, 3);
		g.setColor(barColor);
		g.fillRoundRect(barX, barY, (int) (state.getPercent() * barWidth),
				barHeight, 3, 3);
	}
}
