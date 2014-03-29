package brooklynbridge;

import java.awt.Graphics2D;

import engine.core.Sandbox;

/**
 * 
 * @author helson.taveras
 *
 */
public class BrooklynBridgeMain extends Sandbox{
	BridgeMenu menu;
	public BrooklynBridgeMain() {
		super();
		createRooms();
	}
	
	public void createRooms() {
		menu = new BridgeMenu(this);
		addRoom(menu);
	}
	
	@Override
	public void paint(Graphics2D g) {
		for (int i = getRoomList().size() - 1; i >= 0; i--) 
			getRoomList().get(i).paint(g);
	}

	@Override
	public void run() {
		//updateAllRooms();
		updateCurrentRoom();
	}

	public static void main(String[] args) {
		BrooklynBridgeMain bridge = new BrooklynBridgeMain();
		bridge.setTitle("Brookyln Bridge Interactive Experience");
		bridge.runEngine(bridge);
	}

}
