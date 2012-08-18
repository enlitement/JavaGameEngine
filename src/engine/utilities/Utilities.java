package engine.utilities;


public class Utilities {
	private static Utilities utilities = null;

	private Utilities() {

	}

	public static synchronized Utilities get() {
		if (utilities == null) {
			utilities = new Utilities();
		}
		return utilities;
	}
	
}
