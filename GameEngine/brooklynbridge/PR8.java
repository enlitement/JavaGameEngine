package brooklynbridge;

import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

/*
 * From images:
 * 240-263
 */
public class PR8 extends RoomManager {
	public FourSidedPhotoRoom fspr0, fspr1;

	public PR8(Sandbox sandbox) {
		super(sandbox);

		maxRooms = 2;
		pRooms = new PhotoRoom[maxRooms];

		fspr0 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/32");
		fspr0.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(null);
				//addRoom(new BridgeMenu(getSandbox()));
				//removeMe();
			}
		});
		pRooms[0] = fspr0;
		createFirstRoom();
		
	}

	@Override
	public void update() {
		pRooms[roomNum].update();
	}

	@Override
	public void paint(Graphics2D g) {
		pRooms[roomNum].paint(g);
	}

	@Override
	public void createFirstRoom() {
		fspr1 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/33");
		fspr1.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				addRoom(new BridgeMenu(getSandbox()));
				removeMe();
			}
		});
		pRooms[1] = fspr1;
	}

	@Override
	public void createSecondRoom() {

	}

	@Override
	public void createThirdRoom() {

	}

	@Override
	public void createFourthRoom() {

	}
}
