package unused;

import java.awt.Color;
import java.awt.Graphics;

import test.gameInterfaces.Collectable;
import test.gameInterfaces.Paintable;

public class Item extends Sprite implements Paintable, Collectable{

	public Item(int x, int y) {
		super(x, y);
	}

	@Override
	public void collect(Player player) {
	
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect((int)xpos,(int)ypos,16,16);
	}

}
