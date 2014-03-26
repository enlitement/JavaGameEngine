package brooklynbridge;

import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

/*
 * From images:
 * 120 - 153
 */
public class PR3 extends RoomManager {
	public FourSidedPhotoRoom fspr0, fspr1, fspr2, fspr3;

	public PR3(Sandbox sandbox) {
		super(sandbox);

		maxRooms = 4;
		pRooms = new PhotoRoom[maxRooms];

		fspr0 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/12");
		fspr0.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
					nextPhotoRoom(new PR4(getSandbox()));
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
		fspr1 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/13");
		fspr1.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
					nextPhotoRoom(new PR4(getSandbox()));
			}
		});
		pRooms[1] = fspr1;
	}

	@Override
	public void createSecondRoom() {
		fspr2 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/14");
		fspr2.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
					nextPhotoRoom(new PR4(getSandbox()));
			}
		});
		pRooms[2] = fspr2;
	}

	@Override
	public void createThirdRoom() {
		fspr3 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/15");
		fspr3.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
					nextPhotoRoom(new PR4(getSandbox()));
			}
		});
		pRooms[3] = fspr3;
	}

	@Override
	public void createFourthRoom() {
		
	}
}
