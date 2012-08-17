package test.objects;

import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import test.core.Sandbox;
import test.core.input.KeyboardInput;
import test.core.input.MouseInput;

public abstract class GameObject {
	
	public String name;
	public Image image;
	public boolean isSolid, isVisible;
	public Rectangle rec;
	public double xpos, ypos, dx, dy;
	private Sandbox sandbox;
	
	public GameObject(Sandbox sandbox, String name) {
		this.sandbox = sandbox;
		this.name = name;
	}
	
	public GameObject(Sandbox sandbox) {
		this.sandbox = sandbox;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public void setImage(String imageName) {
		getSandbox().addImage(imageName);
	}
	
	public void addImage(String imageName) {
		getSandbox().addImage(imageName);
	}
	
	public abstract void update();
	
	public KeyboardInput getKeyBoard() {
		return KeyboardInput.getInstance();
	}
	
	public MouseInput getMouse() {
		return MouseInput.getInstance();
	}
	
	public Point getMousePosition() {
		return MouseInput.getInstance().getPosition();
	}
	
	public Sandbox getSandbox() {
		return sandbox;
	}
}
