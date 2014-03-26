package engine.gui;

import java.util.List;

import engine.interfaces.ListenerTag;

public interface IContainer<T>{
	
	public void addListener(ListenerTag listener);
	public void removeListener(ListenerTag listener);
	public List<T> getListenerList();
	/**
	 * Return the generic type T.
	 * @return
	 */
	public T getType();
}
