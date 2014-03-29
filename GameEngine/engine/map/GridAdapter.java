package engine.map;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import engine.sprites.Entity;
import engine.sprites.Item;
import engine.sprites.Sprite;
import engine.sprites.Terrain;
import engine.test.GlobalVars;

public class GridAdapter extends TypeAdapter<Grid> {

	@Override
	public Grid read(JsonReader reader) throws IOException {
		// create the tile array
		Tile[][] tiles = new Tile[GlobalVars.tileLength][GlobalVars.tileLength];

		// open up the double array
		reader.beginArray();
		
		// Iterate through double array
		for (int x = 0; x < tiles.length; x++) {
			reader.beginArray();
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y] = readTile(reader);
			}
			reader.endArray();
		}
		// end first array
		reader.endArray();
		// return the grid based on the tiles
		return new Grid(null, 0, 0, tiles);
	}

	public Tile readTile(JsonReader reader) throws IOException {
		boolean solid = false;
		int xpos = 0;
		int ypos = 0;
		Terrain terrain = null;
		Terrain base = null;
		Item item = null;
		Entity entity = null;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("solid")) {
				solid = reader.nextBoolean();
			} else if (name.equals("xpos")) {
				xpos = reader.nextInt();
			} else if (name.equals("ypos")) {
				ypos = reader.nextInt();
			} else if (name.equals("terrain")
					&& reader.peek() != JsonToken.NULL) {
				terrain = readTerrain(reader);
			} else if (name.equals("base") & reader.peek() != JsonToken.NULL) {
				base = readTerrain(reader);
			} else if (name.equals("item") & reader.peek() != JsonToken.NULL) {
				item = readItem(reader);
			} else if (name.equals("entity") & reader.peek() != JsonToken.NULL) {
				entity = readEntity(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		Tile tile = new Tile(null, xpos, ypos);
		tile.base = base;
		tile.terrain = terrain;
		tile.item = item;
		tile.solid = solid;
		tile.entity = entity;
		return tile;
	}

	private Entity readEntity(JsonReader reader) throws IOException {
		return loadEntity(reader);
	}

	private Item readItem(JsonReader reader) throws IOException {
		return loadItem(reader);
	}

	public Terrain readTerrain(JsonReader reader) throws IOException {
		return loadTerrain(reader);
	}

	public Terrain loadTerrain(JsonReader reader) throws IOException {
		Class<? extends Sprite> spriteClass = null;
		boolean solid = false, isItem = false;
		int xpos = 0, ypos = 0, TYPE = 0, tileX = 0, tileY = 0, gridX = 0, gridY = 0;
		int flip = 0;
		double rotation = 0;
		String imageName = null, name = null;

		reader.beginObject();
		while (reader.hasNext()) {
			String rName = reader.nextName();
			if (rName.equals("class")) {
				spriteClass = readClass(reader);
			} else if (rName.equals("solid")) {
				solid = reader.nextBoolean();
			} else if (rName.equals("isItem")) {
				isItem = reader.nextBoolean();
			} else if (rName.equals("xpos")) {
				xpos = reader.nextInt();
			} else if (rName.equals("ypos")) {
				ypos = reader.nextInt();
			} else if (rName.equals("TYPE")) {
				TYPE = reader.nextInt();
			} else if (rName.equals("rotation")) {
				rotation = reader.nextDouble();
			} else if (rName.equals("flip")) {
				flip = reader.nextInt();
			} else if (rName.equals("gridX")) {
				gridX = reader.nextInt();
			} else if (rName.equals("gridY")) {
				gridY = reader.nextInt();
			} else if (rName.equals("tileX")) {
				tileX = reader.nextInt();
			} else if (rName.equals("tileY")) {
				tileY = reader.nextInt();
			} else if (rName.equals("imageName")) {
				imageName = reader.nextString();
			} else if (rName.equals("name")) {
				name = reader.nextString();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		Terrain sprite = null;
		try {
			sprite = (Terrain) spriteClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sprite.flip = flip;
		sprite.gridX = gridX;
		sprite.gridY = gridY;
		sprite.imageName = imageName;
		sprite.isItem = isItem;
		sprite.name = name;
		sprite.rotation = rotation;
		sprite.tileX = tileX;
		sprite.tileY = tileY;
		sprite.solid = solid;
		sprite.xpos = xpos;
		sprite.ypos = ypos;
		sprite.TYPE = TYPE;
		sprite.classType = spriteClass;
		return sprite;
	}

	public Entity loadEntity(JsonReader reader) throws IOException {
		Class<? extends Sprite> spriteClass = null;
		boolean solid = false, isItem = false;
		int xpos = 0, ypos = 0, TYPE = 0, tileX = 0, tileY = 0, gridX = 0, gridY = 0;
		int flip = 0;
		int rotation = 0;
		String imageName = null, name = null;

		reader.beginObject();
		while (reader.hasNext()) {
			String rName = reader.nextName();
			if (rName.equals("class")) {
				spriteClass = readClass(reader);
			} else if (rName.equals("solid")) {
				solid = reader.nextBoolean();
			} else if (rName.equals("isItem")) {
				isItem = reader.nextBoolean();
			} else if (rName.equals("xpos")) {
				xpos = reader.nextInt();
			} else if (rName.equals("ypos")) {
				ypos = reader.nextInt();
			} else if (rName.equals("TYPE")) {
				TYPE = reader.nextInt();
			} else if (rName.equals("rotation")) {
				rotation = reader.nextInt();
			} else if (rName.equals("flip")) {
				flip = reader.nextInt();
			} else if (rName.equals("gridX")) {
				gridX = reader.nextInt();
			} else if (rName.equals("gridY")) {
				gridY = reader.nextInt();
			} else if (rName.equals("tileX")) {
				tileX = reader.nextInt();
			} else if (rName.equals("tileY")) {
				tileY = reader.nextInt();
			} else if (rName.equals("imageName")) {
				imageName = reader.nextString();
			} else if (rName.equals("name")) {
				name = reader.nextString();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		Entity sprite = null;
		try {
			sprite = (Entity) spriteClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sprite.flip = flip;
		sprite.gridX = gridX;
		sprite.gridY = gridY;
		sprite.imageName = imageName;
		sprite.isItem = isItem;
		sprite.name = name;
		sprite.rotation = rotation;
		sprite.tileX = tileX;
		sprite.tileY = tileY;
		sprite.solid = solid;
		sprite.xpos = xpos;
		sprite.ypos = ypos;
		sprite.TYPE = TYPE;
		sprite.classType = spriteClass;
		return sprite;
	}

	public Item loadItem(JsonReader reader) throws IOException {
		Class<? extends Sprite> spriteClass = null;
		boolean solid = false, isItem = false;
		int xpos = 0, ypos = 0, TYPE = 0, tileX = 0, tileY = 0, gridX = 0, gridY = 0;
		int flip = 0;
		int rotation = 0;
		String imageName = null, name = null;

		reader.beginObject();
		while (reader.hasNext()) {
			String rName = reader.nextName();
			if (rName.equals("class")) {
				spriteClass = readClass(reader);
			} else if (rName.equals("solid")) {
				solid = reader.nextBoolean();
			} else if (rName.equals("isItem")) {
				isItem = reader.nextBoolean();
			} else if (rName.equals("xpos")) {
				xpos = reader.nextInt();
			} else if (rName.equals("ypos")) {
				ypos = reader.nextInt();
			} else if (rName.equals("TYPE")) {
				TYPE = reader.nextInt();
			} else if (rName.equals("rotation")) {
				rotation = reader.nextInt();
			} else if (rName.equals("flip")) {
				flip = reader.nextInt();
			} else if (rName.equals("gridX")) {
				gridX = reader.nextInt();
			} else if (rName.equals("gridY")) {
				gridY = reader.nextInt();
			} else if (rName.equals("tileX")) {
				tileX = reader.nextInt();
			} else if (rName.equals("tileY")) {
				tileY = reader.nextInt();
			} else if (rName.equals("imageName")) {
				imageName = reader.nextString();
			} else if (rName.equals("name")) {
				name = reader.nextString();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		Item sprite = null;
		try {
			sprite = (Item) spriteClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sprite.flip = flip;
		sprite.gridX = gridX;
		sprite.gridY = gridY;
		sprite.imageName = imageName;
		sprite.isItem = isItem;
		sprite.name = name;
		sprite.rotation = rotation;
		sprite.tileX = tileX;
		sprite.tileY = tileY;
		sprite.solid = solid;
		sprite.xpos = xpos;
		sprite.ypos = ypos;
		sprite.TYPE = TYPE;
		sprite.classType = spriteClass;
		return sprite;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Sprite> readClass(JsonReader reader)
			throws IOException {
		Class<? extends Sprite> spriteClass = null;
		try {
			spriteClass = (Class<? extends Sprite>) Class.forName(reader
					.nextString());
		} catch (ClassNotFoundException e) {
			System.out.println("Check the GSON class parser.");
			e.printStackTrace();
		}
		return spriteClass;
	}

	@Override
	public void write(JsonWriter writer, Grid grid) throws IOException {
		writeGrid(writer, grid);
	}

	public void writeGrid(JsonWriter writer, Grid grid) throws IOException {
		Tile[][] tiles = grid.tiles;
		// open up the double array
		writer.beginArray();
		for (int x = 0; x < tiles.length; x++) {
			writer.beginArray();
			for (int y = 0; y < tiles[0].length; y++) {
				writeTile(writer, tiles[x][y]);
			}
			// end array
			writer.endArray();
		}
		// finished editing
		writer.endArray();
	}

	public void writeTile(JsonWriter writer, Tile tile) throws IOException {
		writer.beginObject();
		writer.name("solid").value(tile.solid);
		writer.name("xpos").value(tile.xpos);
		writer.name("ypos").value(tile.ypos);

		if (tile.terrain != null) {
			writer.name("terrain");
			writeTerrain(writer, tile.terrain);
		} else
			writer.name("terrain").nullValue();
		if (tile.item != null) {
			writer.name("item");
			writeItem(writer, tile.item);
		} else
			writer.name("item").nullValue();
		if (tile.base != null) {
			writer.name("base");
			writeTerrain(writer, tile.base);
		} else
			writer.name("base").nullValue();
		if (tile.entity != null) {
			writer.name("entity");
			writeEntity(writer, tile.entity);
		} else
			writer.name("entity").nullValue();
		writer.endObject();
	}

	private void writeEntity(JsonWriter writer, Entity entity)
			throws IOException {
		writer.beginObject();
		writeSprite(writer, entity);
		writer.endObject();
	}

	private void writeItem(JsonWriter writer, Item item) throws IOException {
		writer.beginObject();
		writeSprite(writer, item);
		writer.endObject();
	}

	public void writeTerrain(JsonWriter writer, Terrain terrain)
			throws IOException {
		writer.beginObject();
		writeSprite(writer, terrain);
		writer.endObject();
	}

	public void writeSprite(JsonWriter writer, Sprite sprite)
			throws IOException {
		writer.name("solid").value(sprite.solid);
		writer.name("isItem").value(sprite.isItem);
		writer.name("TYPE").value(sprite.TYPE);
		writer.name("tileX").value(sprite.tileX);
		writer.name("tileY").value(sprite.tileY);
		writer.name("gridX").value(sprite.gridX);
		writer.name("gridY").value(sprite.gridY);
		writer.name("flip").value(sprite.flip);
		writer.name("rotation").value(sprite.rotation);
		writer.name("imageName").value(sprite.imageName);
		writer.name("name").value(sprite.name);
		writer.name("xpos").value(sprite.xpos);
		writer.name("ypos").value(sprite.ypos);
		writer.name("class").value(sprite.classType.getName());
	}
}