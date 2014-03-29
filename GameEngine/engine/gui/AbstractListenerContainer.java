package engine.gui;

import engine.objects.GameObject;

public abstract class AbstractListenerContainer {

	private GameObject object;

	public AbstractListenerContainer(GameObject object) {
		this.setObject(object);
	}

	public GameObject getObject() {
		return object;
	}

	public void setObject(GameObject object) {
		this.object = object;
	}
}
