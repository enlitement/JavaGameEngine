package engine.gui;

import java.util.ArrayList;
import java.util.List;

import engine.events.DragEvent;
import engine.events.DropEvent;
import engine.events.EventDispatcher;

public class DnDContainer extends AbstractListenerContainer{
	private List<DragListener> dragList;
	private List<DropListener> dropList;

	public DnDContainer(DraggableIcon draggable) {
		super(draggable);
	}

	public void addDragListener(DragListener dragListener) {
		if (dragList == null)
			dragList = new ArrayList<DragListener>();
		dragList.add(dragListener);
		EventDispatcher.addObserver(getObject(), new DragEvent(this), dragListener);
	}

	public void removeDragListener(DragListener dragListener) {
		if (dragList != null) {
			dragList.remove(dragListener);
			EventDispatcher.removeObserver(dragListener);
		} else
			throw new Error("JGE.Events: Attempted to remove an drag listener "
					+ "that does not exist in this object.");
	}

	public void addDropListener(DropListener dropListener) {
		if (dropList == null)
			dropList = new ArrayList<DropListener>();
		dropList.add(dropListener);
		EventDispatcher.addObserver(getObject(), new DropEvent(this), dropListener);
	}

	public void removeDropListener(DropListener dropListener) {
		if (dropList != null) {
			dropList.remove(dropListener);
			EventDispatcher.removeObserver(dropListener);
		} else
			throw new Error("JGE.Events: Attempted to remove an drop listener "
					+ "that does not exist in this object.");
	}

	public List<DragListener> getDragList() {
		return dragList;
	}

	public List<DropListener> getDropList() {
		return dropList;
	}
}
