package engine.logger;

import java.util.Date;

/**
 * Logger class that logs errors and messages.
 * 
 * @author Helson
 * 
 */
public class Logger {

	private static Logger logger;
	private final static String DEFAULT_TEXTFILE = "logger.txt";
	private static String textFile = DEFAULT_TEXTFILE;

	public Logger() {
		textFile = DEFAULT_TEXTFILE;
	}

	/*
	 * public String getTime() { Date date = new Date(); long t1 =
	 * date.getTime(); System.out.println((t1 / 1000 / 60 / 60) % 24 + ":" + (t1
	 * / 1000 / 60) % 60 + ":" + (t1 / 1000) % 60); long t2 =
	 * System.currentTimeMillis(); System.out.println((t2 / 1000 / 60 / 60) % 24
	 * + ":" + (t2 / 1000 / 60) % 60 + ":" + (t2 / 1000) % 60); return null; }
	 */

	/**
	 * Returns the String format of a new Date.
	 * 
	 * @return
	 */
	public static String getDate() {
		return new Date().toString();
	}

	public static Logger getLogger() {
		if (logger == null) {
			logger = new Logger();
		}
		return logger;
	}

	public static void log(String text) {
		appendToFile(text);
	}

	/**
	 * Log with date.
	 * 
	 * @param text
	 *            Text to log.
	 */
	public static void ld(String text) {
		appendToFile(getDate() + "[" + text + "]");
	}

	public static void err(String text) {
		appendToFile("ERROR: " + text);
	}

	public static void appendToFile(String text) {
		TextWriter.appendToFile(textFile, text);
	}

	public String getTextFileName() {
		return textFile;
	}

	public void setTextFileName(String textFileName) {
		textFile = textFileName;
	}
}
