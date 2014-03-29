package engine.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class TextWriter {
	/**
	 * Append to specified file and add a newline.
	 * 
	 * @param filename
	 *            Name of file. Add ".txt" to filename. Example: "log.txt"
	 * @param text
	 *            Text to append
	 */
	public static void appendToFile(String filename, String text) {
		try {
			FileWriter fstream = new FileWriter(filename, true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.append(text);
			out.newLine();
			out.close();
		} catch (Exception e) {
			System.err.println("Could not append '" + text + "' to '"
					+ filename + "'.");
			e.printStackTrace();
		}
	}
}
