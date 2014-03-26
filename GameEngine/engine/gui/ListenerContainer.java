package engine.gui;

import java.util.ArrayList;
import java.util.List;

import engine.events.EventDispatcher;
import engine.events.JActionEvent;
import engine.events.JActionListener;
import engine.events.JKeyEvent;
import engine.events.JKeyListener;
import engine.events.JMouseListener;
import engine.events.MouseMoveEvent;
import engine.objects.GameObject;

public class ListenerContainer extends AbstractListenerContainer {
	private List<JMouseListener> mouseList;
	private List<JKeyListener> keyList;
	private List<JActionListener> actionList;
	public CustomListenerRemoval customList;
	
	public ListenerContainer(GameObject object) {
		super(object);
	}

	public void addActionListener(JActionListener actionListener) {
		if (actionList == null)
			actionList = new ArrayList<JActionListener>();
		actionList.add(actionListener);
		EventDispatcher.addObserver(getObject(), new JActionEvent(this),
				actionListener);
	}

	public void removeActionListener(JActionListener actionListener) {
		if (actionList != null) {
			EventDispatcher.removeObserver(actionListener);
		} else
			throw new Error(
					"JGE.Events: Attempted to remove an action listener "
							+ "that does not exist in this object.");
	}

	public void addMouseListener(JMouseListener mouseListener) {
		if (mouseList == null)
			mouseList = new ArrayList<JMouseListener>();
		EventDispatcher.addObserver(getObject(), new MouseMoveEvent(this),
				mouseListener);
		mouseList.add(mouseListener);
	}

	public void removeMouseListener(JMouseListener mouseListener) {
		if (mouseList != null) {
			// mouseList.remove(mouseListener);
			EventDispatcher.removeObserver(mouseListener);
		} else
			throw new Error("JGE.Events: Attempted to remove a mouse listener "
					+ "that does not exist in this object.");
	}

	public void addKeyListener(JKeyListener keyListener) {
		if (keyList == null)
			keyList = new ArrayList<JKeyListener>();
		keyList.add(keyListener);
		EventDispatcher.addObserver(getObject(), new JKeyEvent(this),
				keyListener);
	}

	public void removeKeyListener(JKeyListener keyListener) {
		if (keyList != null) {
			//keyList.remove(keyListener);
			EventDispatcher.removeObserver(keyListener);
		} else
			throw new Error("JGE.Events: Attempted to remove a key listener "
					+ "that does not exist in this object.");
	}

	public void removeAllListeners() {
		if (keyList != null)
			for (JKeyListener key : keyList)
				removeKeyListener(key);
		if (mouseList != null)
			for (JMouseListener mouse : mouseList)
				removeMouseListener(mouse);
		if (actionList != null)
			for (JActionListener action : actionList)
				removeActionListener(action);
		if(customList!=null)
			customList.removeCustomListeners();
	}

	public List<JActionListener> getActionListeners() {
		return actionList;
	}

	public List<JKeyListener> getKeyList() {
		return keyList;
	}

	public List<JMouseListener> getMouseList() {
		return mouseList;
	}
}
