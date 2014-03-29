package engine.gui;

import java.util.ArrayList;
import java.util.List;

import engine.interfaces.ListenerTag;

public class ListenerC {
	private List<IContainer<?>> containers;

	public ListenerC() {
		containers = new ArrayList<IContainer<?>>();
	}

	public void addContainer(IContainer<?> container) {
		containers.add(container);
	}

	public void removeContainer(IContainer<?> container) {
		containers.remove(container);
	}

	public void addListener(ListenerTag listener) {
		for (IContainer<?> container : containers) {
			if (container.getType() == listener) {
				container.addListener(listener);
				return;
			}
		}
		throw new Error("Listener " + listener.toString()
				+ " was not able to be added to the Listener container");
	}

	public void removeListener(ListenerTag listener) {
		for (IContainer<?> container : containers) {
			if (container.getType() == listener) {
				container.removeListener(listener);
				return;
			}
		}
		throw new Error("Listener " + listener.toString()
				+ " was not able to be removed from the Listener container");
	}

	public IContainer<?> getListenerList(Class<?> type) {
		for (IContainer<?> container : containers)
			if (container.getType() == type)
				return container;

		throw new Error(type + " was not found in the Listener Container.");
	}
}
