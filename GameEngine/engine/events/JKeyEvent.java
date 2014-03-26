package engine.events;

public class JKeyEvent extends EEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private char keyChar;
	private java.awt.event.KeyEvent java_key_event;

	public JKeyEvent(Object arg0) {
		super(arg0);
		if (arg0 instanceof java.awt.event.KeyEvent) {
			java_key_event = (java.awt.event.KeyEvent) arg0;
			keyChar = ((java.awt.event.KeyEvent) arg0).getKeyChar();
		}
		// System.out.println(keyChar);
	}

	public char getKeyChar() {
		return keyChar;
	}

	public java.awt.event.KeyEvent getJavaKeyEvent() {
		return java_key_event;
	}
}
