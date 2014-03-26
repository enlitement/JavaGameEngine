package engine.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import engine.core.JGE;
import engine.events.JActionListener;
import engine.events.JKeyListener;
import engine.events.JMouseListener;
import engine.gui.ListenerContainer;

public abstract class GameObject {

	private String name;
	private double xpos, ypos;
	private transient Image image;
	private transient Rectangle rec;
	private ListenerContainer listeners;
	private int ID;

	public GameObject() {
		setListeners(new ListenerContainer(this));
		rec = new Rectangle(0, 0, 0, 0);
	}

	public GameObject(String name) {
		this.setName(name);
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setImage(String imageName) throws IOException {
		image = JGE.LOAD_IMAGE(imageName);
	}

	abstract public void paint(Graphics2D g);

	public void update() {

	}

	public int getWidth() {
		return rec.width;
	}

	public int getHeight() {
		return rec.height;
	}

	public void setWidth(int width) {
		rec.width = width;
	}

	public void setHeight(int height) {
		rec.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return xpos;
	}

	public void setX(double xpos) {
		this.xpos = xpos;
		rec.setLocation((int) xpos, (int) ypos);
	}

	public double getY() {
		return ypos;
	}

	public void setY(double ypos) {
		this.ypos = ypos;
		rec.setLocation((int) xpos, (int) ypos);
	}

	public Rectangle getRec() {
		return rec;
	}

	public void setRec(Rectangle rec) {
		this.rec = rec;
	}

	public boolean contains(int x, int y) {
		return rec.contains(x, y);
	}

	protected ListenerContainer getListeners() {
		return listeners;
	}

	void setListeners(ListenerContainer listeners) {
		this.listeners = listeners;
	}

	public void addActionListener(JActionListener actionListener) {
		getListeners().addActionListener(actionListener);
	}

	public void removeActionListener(JActionListener actionListener) {
		getListeners().removeActionListener(actionListener);
	}

	public void addMouseListener(JMouseListener mouseListener) {
		getListeners().addMouseListener(mouseListener);
	}

	public void removeMouseListener(JMouseListener mouseListener) {
		getListeners().removeMouseListener(mouseListener);
	}

	public void addKeyListener(JKeyListener keyListener) {
		getListeners().addKeyListener(keyListener);
	}

	public void removeKeyListener(JKeyListener keyListener) {
		getListeners().removeKeyListener(keyListener);
	}

	public void removeAllListeners() {
		getListeners().removeAllListeners();
	}
}
