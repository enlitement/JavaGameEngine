package engine.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import engine.map.Grid;
import engine.map.GridAdapter;
import engine.sprites.Sprite;

public class JSONReader {

	static String getClassPath() {
		Properties prop = System.getProperties();
		return prop.getProperty("java.class.path", null);
	}

	public static String getUserDir() {
		Properties prop = System.getProperties();
		return prop.getProperty("user.dir", null) + "/";
	}

	public static Grid loadGrid(String name, boolean addClassPath) {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Grid.class, new GridAdapter());
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		
		Grid grid = null;
		try {
			System.out.println(name);
			BufferedReader br;
			if(addClassPath)
				br = new BufferedReader(new FileReader(getUserDir()+ name));
			else 
				br = new BufferedReader(new FileReader(name));
			
			// convert the json string back to object
			grid = gson.fromJson(br, Grid.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return grid;
	}
	
	public static Sprite[] loadSpriteList(String name) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Sprite[] spriteList = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(getUserDir()
					+ name + ".json"));

			// convert the json string back to object
			spriteList = gson.fromJson(br, Sprite[].class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return spriteList;
	}

	@SuppressWarnings("unchecked")
	public static <T> T loadObject(T t, String name) {
		Gson gson = new Gson();
		T json_object = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(getUserDir()
					+ name + ".json"));

			// convert the json string back to object
			json_object = (T) gson.fromJson(br, t.getClass());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json_object;
	}

	public static void saveObject(String name, Object object) {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Grid.class, new GridAdapter());
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		
		String json = gson.toJson(object);

		try {
			// write converted json data to a file named "file.json"
			FileWriter writer = new FileWriter(name + ".json");
			writer.write(json);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(json);
	}
}
