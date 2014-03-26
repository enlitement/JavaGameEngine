package brooklynbridge;

import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.core.resources.ResourceManager;
import engine.rooms.Room;

public abstract class RoomManager extends Room implements IRoom {
	public int roomNum, maxRooms;
	public PhotoRoom[] pRooms;

	public RoomManager(Sandbox sandbox) {
		super(sandbox);
	}

	@Override
	public void paint(Graphics2D g) {

	}

	@Override
	public void update() {

	}

	public void previousPhotoRoom(RoomManager previousRoomManager) {
		pRooms[roomNum].removeMe();
		int nextRoomNum = roomNum - 1;
		if (nextRoomNum > 0) {
			roomNum--;
			addRoom(pRooms[roomNum]);
		} else {
			ResourceManager.get().images.clearImages();
			addRoom(previousRoomManager);
			removeMe();
		}
	}

	public void nextPhotoRoom(RoomManager nextRoomManager) {
		pRooms[roomNum].removeMe();
		int nextRoomNum = roomNum + 1;
		if (nextRoomNum < pRooms.length) {
			roomNum++;
			addRoom(pRooms[roomNum]);
		} else {
			ResourceManager.get().images.clearImages();
			addRoom(nextRoomManager);
			removeMe();
		}
	}

	
	@Override
	public abstract void createFirstRoom();

	@Override
	public abstract void createSecondRoom();

	@Override
	public abstract void createThirdRoom();

	@Override
	public abstract void createFourthRoom();
}
