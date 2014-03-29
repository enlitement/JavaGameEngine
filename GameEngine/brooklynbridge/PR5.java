package brooklynbridge;

import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

/*
 * From images:
 * 200-233
 */
public class PR5 extends RoomManager {
	public FourSidedPhotoRoom fspr0, fspr1, fspr2, fspr3, fspr4;

	public PR5(Sandbox sandbox) {
		super(sandbox);

		maxRooms = 5;
		pRooms = new PhotoRoom[maxRooms];

		fspr0 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/20");
		fspr0.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PRManArch(getSandbox()));
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
		fspr1 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/21");
		fspr1.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PRManArch(getSandbox()));
			}
		});
		pRooms[1] = fspr1;
	}

	@Override
	public void createSecondRoom() {
		fspr2 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/22");
		fspr2.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PRManArch(getSandbox()));
			}
		});
		pRooms[2] = fspr2;
	}

	@Override
	public void createThirdRoom() {
		fspr3 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/23");
		fspr3.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PRManArch(getSandbox()));
			}
		});
		pRooms[3] = fspr3;
	}

	@Override
	public void createFourthRoom() {
		fspr4 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/240",
				"beginning/241", "beginning/242", "beginning/243");
		fspr4.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PRManArch(getSandbox()));
			}
		});
		pRooms[4] = fspr4;
	}
}
