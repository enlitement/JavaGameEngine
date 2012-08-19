package engine.objects;

import java.awt.Image;
import java.awt.Rectangle;

import engine.core.Sandbox;


public abstract class GameObject {
	
	public String name;
	public Image image;
	public boolean isSolid, isVisible;
	public Rectangle rec;
	public double xpos, ypos, dx, dy;
	private Sandbox sandbox;
	
	public GameObject(Sandbox sandbox) {
		this.sandbox = sandbox;
	}
	
	public GameObject(Sandbox sandbox, String name) {
		this.sandbox = sandbox;
		this.name = name;
	}
	
	public Sandbox getSandbox() {
		return sandbox;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public void setImage(String imageName) {
		getSandbox().addImage(imageName);
	}
}
