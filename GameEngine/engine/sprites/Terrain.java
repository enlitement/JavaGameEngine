package engine.sprites;

import java.awt.Graphics2D;
import java.awt.Image;

import engine.map.Tile;

public class Terrain extends Sprite {
	public transient Image terrainImage;

	public Terrain(String name, Tile tile) {
		super(name, tile);
		TYPE = Tile.BASE;
		classType = Terrain.class;
	}

	public Terrain(String name, String imageName) {
		super(name);
		TYPE = Tile.BASE;
		setImage(imageName);
		classType = Terrain.class;
	}

	public Terrain() {
		TYPE = Tile.BASE;
		classType = Terrain.class;
	}

	@Override
	public void paint(Graphics2D g) {
		if (terrainImage == null)
			g.drawImage(image, (int) xpos, (int) ypos, null);
		else
			g.drawImage(terrainImage, (int) xpos, (int) ypos, null);
	}

	@Override
	public void paint(Graphics2D g, int x, int y) {
		if (terrainImage == null)
			g.drawImage(image, (int) xpos + x, (int) ypos + y, null);
		else
			g.drawImage(terrainImage, (int) xpos + x, (int) ypos + y, null);
	}
	
	public void changeImage(Image image) {
		if(this.image!=null)
			terrainImage = image;
			
	}
	
	public Image getImage() {
		if(terrainImage!=null)
			return terrainImage;
		if(image!=null)
			return image;
		return null;
	}
}
