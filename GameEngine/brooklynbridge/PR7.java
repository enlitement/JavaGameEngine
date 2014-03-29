package brooklynbridge;

import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

/*
 * From images:
 * 240-263
 */
public class PR7 extends RoomManager {
	public FourSidedPhotoRoom fspr0, fspr1, fspr2, fspr3, fspr4;

	public PR7(Sandbox sandbox) {
		super(sandbox);

		maxRooms = 5;
		pRooms = new PhotoRoom[maxRooms];

		fspr0 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/27");
		fspr0.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR7(getSandbox()));
			}
		});
		pRooms[0] = fspr0;
		
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
		fspr1 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/28");
		fspr1.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR8(getSandbox()));
			}
		});
		pRooms[1] = fspr1;
	}

	@Override
	public void createSecondRoom() {
		fspr2 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/29");
		fspr2.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR8(getSandbox()));
			}
		});
		pRooms[2] = fspr2;
	}

	@Override
	public void createThirdRoom() {
		fspr3 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/30");
		fspr3.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR8(getSandbox()));
			}
		});
		pRooms[3] = fspr3;
	}

	@Override
	public void createFourthRoom() {
		fspr4 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/31");
		fspr4.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR8(getSandbox()));
			}
		});
		pRooms[4] = fspr4;
	}
}
