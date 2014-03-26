package brooklynbridge;

import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

/**
 * Order: 0. Zero 1. fspr 2. three sided 3. fspr2 4. fspr3
 * 
 * @author helson.taveras
 * 
 */
public class PR1 extends RoomManager {
	public Zero zero;
	public FourSidedPhotoRoom fspr, fspr2, fspr3;
	public FourSidedPhotoRoom threeSided;

	public PR1(Sandbox sandbox) {
		super(sandbox);

		maxRooms = 5;
		pRooms = new PhotoRoom[maxRooms];

		// Created on construction
		zero = new Zero(getSandbox(), this);
		zero.forward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (zero.face < zero.images.length) {
					zero.face++;
					System.out.println("Face:" + zero.face);
				}
				if (zero.face == zero.images.length)
					nextPhotoRoom(new PR2(getSandbox()));
			}
		});
		pRooms[0] = zero;
		
		createFirstRoom();
		createSecondRoom();
		createThirdRoom();
		createFourthRoom();
	}

	public void createFirstRoom() {
		fspr = new FourSidedPhotoRoom(getSandbox(), this, "beginning/4");
		fspr.forward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pRooms[roomNum].face==PhotoRoom.FORWARD)
					nextPhotoRoom(new PR2(getSandbox()));
			}
		});
		pRooms[1] = fspr;
	}

	public void createSecondRoom() {
		threeSided = new FourSidedPhotoRoom(getSandbox(), this, "beginning/50",
				"beginning/51", null, "beginning/53");
		threeSided.forward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextPhotoRoom(new PR2(getSandbox()));
			}
		});
		pRooms[2] = threeSided;
	}

	public void createThirdRoom() {
		fspr2 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/6");
		fspr2.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR2(getSandbox()));
			}
		});
		pRooms[3] = fspr2;
	}

	public void createFourthRoom() {
		fspr3 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/7");
		fspr3.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR2(getSandbox()));
			}
		});
		pRooms[4] = fspr3;
	}

	@Override
	public void update() {
		pRooms[roomNum].update();
	}

	@Override
	public void paint(Graphics2D g) {
		pRooms[roomNum].paint(g);
	}
}
