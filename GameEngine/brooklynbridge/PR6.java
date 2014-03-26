package brooklynbridge;

import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

/*
 * From images:
 * 240-263
 */
public class PR6 extends RoomManager {
	public FourSidedPhotoRoom fspr0, fspr1, fspr2, fspr3;

	public PR6(Sandbox sandbox) {
		super(sandbox);

		maxRooms = 2;
		pRooms = new PhotoRoom[maxRooms];
		
		createFirstRoom();
		createSecondRoom();
		createThirdRoom();
		createFourthRoom();
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
		fspr1 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/250",
				"beginning/251", "beginning/252", "beginning/253");
		fspr1.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR7(getSandbox()));
			}
		});
		pRooms[0] = fspr1;
	}

	@Override
	public void createSecondRoom() {
		fspr2 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/260",
				"beginning/261", "beginning/262", "beginning/263");
		fspr2.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR7(getSandbox()));
			}
		});
		pRooms[1] = fspr2;
	}

	@Override
	public void createThirdRoom() {

	}

	@Override
	public void createFourthRoom() {
		
	}
}
