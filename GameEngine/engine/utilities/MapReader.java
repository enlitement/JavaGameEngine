package engine.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapReader {

	private static boolean printText;

	public MapReader() {

	}

	/**
	 * Reads the file "fileName".txt and sees places it into a 2D array
	 */
	public static char[][] readFile(String fileName) throws IOException {
		/** An ArrayList composed of Strings */
		// Inefficient? Why not just make it chars?
		ArrayList<String> lines = new ArrayList<String>();
		// Max width of a row
		int width = 0;
		// Height of the file
		int height = 0;
		// read every line in the text file into the list
		BufferedReader reader = new BufferedReader(new FileReader(
				System.getProperty("user.dir") + File.separator + fileName));
		while (true) {
			// Read the line
			String line = reader.readLine();
			// no more lines to read
			if (line == null) {
				reader.close();
				break;
			}
			// add every line except for comments
			if (!line.startsWith("#")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}

		height = lines.size();
		// Now we know the width and height, so make the matrix
		char[][] matrix = new char[width][height];
		// for each row and column, insert the char.
		for (int y = 0; y < height; y++) {
			String line = (String) lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				char ch = line.charAt(x);
				matrix[x][y] = ch;
				if (printText)
					System.out.print(ch);
			}
			if (printText)
				System.out.println();
		}
		return matrix;
	}

	public static void printTextFile(boolean bool) {
		printText = bool;
	}

	public static boolean getPrintTextFile() {
		return printText;
	}
}
