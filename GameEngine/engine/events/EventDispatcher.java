package engine.events;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import engine.core.JGE;
import engine.interfaces.ListenerTag;
import engine.objects.GameObject;

/**
 * The event dispatcher controls the update of all the GUI of the game. Using an
 * observer/listener relationship detailed in this website --
 * http://iweb.dl.sourceforge
 * .net/project/eventdrivenpgm/event_driven_programming.pdf -- an observer is
 * attached to a GameObject with a Listener. Once the event is fired by using
 * the EventDispatcher.eventArrived(EEvent event) method, all observers waiting
 * for that EEvent to be be fired call their respective listeners.
 * 
 * @author Helson Taveras
 * 
 */
public final class EventDispatcher {

	/**
	 * If true, prints out all the information about events being received and
	 * observers being added.
	 */
	public static boolean DEBUG_EVENTS = false;
	/**
	 * The list of current observers that the EventDispatcher must check on each
	 * update.
	 */
	private static Vector<Observer> observerList;
	/**
	 * The list of Game Objects which are registered to an EEvent.
	 */
	private static Vector<GameObject> objects;
	/**
	 * Because of concurrency issues, a seperate list must be kept for all
	 * observers that have been requested to be added.
	 */
	private static Vector<Observer> addObserverList;
	/**
	 * Because of concurrency issues, a seperate list must be kept for all
	 * observers that have been requested to be removed.
	 */
	private static Vector<ListenerTag> removeObserverList;
	/**
	 * The list of received events.
	 */
	private static ConcurrentLinkedQueue<EEvent> eventQueue;
	/**
	 * Whether the EventDispatcher is currently receiving events for processing.
	 * No events are lost if this is true.
	 */
	private static boolean acceptingEvents;

	private static boolean running;

	/**
	 * Singleton object.
	 */
	public static EventDispatcher dispatcher;

	private EventDispatcher() {

		acceptingEvents = true;
		running = true;
		objects = new Vector<GameObject>();
		observerList = new Vector<Observer>();
		addObserverList = new Vector<Observer>();
		removeObserverList = new Vector<ListenerTag>();
		eventQueue = new ConcurrentLinkedQueue<EEvent>();
	}

	/**
	 * Add an observer for a specific listener.
	 * 
	 * @param event
	 *            The event to wait for.
	 * @param listener
	 *            The listener to wait for.
	 */
	public static void addObserver(final GameObject object, final EEvent event,
			final ListenerTag listener) {
		objects.add(object);
		Observer observer = new Observer(object, event, listener);
		// if (!DEBUG_EVENTS)
		// System.out.println("Adding observer:" + observer.toString());
		addObserverList.add(observer);
	}

	/**
	 * Call when an event (KeyEvent, DragEvent) arrives.
	 * 
	 * @param event
	 *            The event that arrived.
	 */
	public static void eventArrived(EEvent event) {
		if (acceptingEvents)
			synchronized (eventQueue) {
				eventQueue.add(event);
			}
		// if (!DEBUG_EVENTS)
		// System.out.println("EVENT ARRIVED:" + event.getClass());
	}

	/**
	 * Checks to see if the event is being listened for.
	 * 
	 * @param event
	 *            The event to search for
	 * @return Whether it's being observed or not.
	 */
	public static boolean getEvent(EEvent event) {
		synchronized (eventQueue) {
			for (EEvent eventQ : eventQueue)
				if (eventQ.getClass() == event.getClass())
					return true;
			return false;
		}
	}

	/**
	 * Used to get rid of a listener from an object.
	 * 
	 * @param listener
	 *            The listener to get rid of.
	 */
	public static void removeObserver(ListenerTag listener) {
		removeObserverList.add(listener);
	}

	private boolean updateRemove() {
		if (!removeObserverList.isEmpty()) {
			acceptingEvents = false;
			for (Observer observer : observerList)
				for (ListenerTag tag : removeObserverList)
					if (observer.getListener() == tag) {
						if (!DEBUG_EVENTS) {
							System.out.println("Removing "
									+ observer.toString());

						}
						observerList.remove(observer);
						removeObserverList.remove(tag);
						return true;
					}
		}

		return false;
	}

	private void updateAdd() {
		if (!addObserverList.isEmpty()) {
			observerList.addAll(addObserverList);
			addObserverList.clear();
		}
	}

	private void updateObserverList() {
		// remove proper observers
		boolean needsUpdate = updateRemove();
		while (needsUpdate) {
			needsUpdate = updateRemove();
		}
		// add the proper observers
		updateAdd();
		acceptingEvents = true;
	}

	/**
	 * Notifies Observers of an event being received.
	 * 
	 * @param event
	 */
	private void notifyObservers(EEvent event) {
		Vector<Observer> list = observerList;
		for (Observer observer : list)
			if (event.getClass() == (observer.getEvent().getClass())
					&& !removeObserverList.contains(observer.getListener())
					&& observer.getObject().getID() != -1) {
				if (JGE.DEBUG)
					System.out.println("Event triggered:" + event.getClass()
							+ " at GameObject name:"
							+ observer.getObject().getName() + " class:"
							+ observer.getClass());
				observer.eventHandler(event);
			}
	}

	/**
	 * Stops the Event Dispatcher from sending out notifications.
	 */
	public static void stop() {
		setRunning(false);
	}

	private static void setRunning(boolean run) {
		running = run;
		//if (!EventDispatcher.DEBUG_EVENTS)
		//	System.out.println("Running = " + running);
	}

	/**
	 * Starts the Event Dispatcher again. Unnecessary to call when insatiated or
	 * on first run. Only call after a stop() method.
	 */
	public static void start() {
		setRunning(true);
	}

	/**
	 * Returns the whether the run() method is running.
	 * 
	 * @return
	 */
	public static boolean isRunning() {
		return running;
	}

	/**
	 * Runs the EventDispatcher.
	 */
	public void run() {
		if (running && !eventQueue.isEmpty()) {
			// synchronized (observerList) {
			for (int i = 0; i < eventQueue.size(); i++)
				notifyObservers(eventQueue.poll());
			// }
		}
		updateObserverList();

	}

	/**
	 * 
	 * @return Returns the game objects associated with a Listener.
	 */
	Vector<GameObject> getObjects() {
		return objects;
	}

	public static EventDispatcher get() {
		if (dispatcher == null)
			return new EventDispatcher();
		else
			return dispatcher;
	}
}
