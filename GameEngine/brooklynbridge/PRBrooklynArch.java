package brooklynbridge;

import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

/*
 * From images:
 * 240-263
 */
public class PRBrooklynArch extends RoomManager {
	public UpwardsEightSidedPR espr, espr1, espr2;
	public FourSidedPhotoRoom fspr0;
	public PRBrooklynArch(Sandbox sandbox) {
		super(sandbox);

		maxRooms = 4;
		pRooms = new PhotoRoom[maxRooms];

		String[] mabbobbers = { "beginning/340", "beginning/341", null,
				"beginning/343", null, null, null, null, "beginning/348", null,
				null, null, "beginning/3412", null, null, null };
		espr = new UpwardsEightSidedPR(getSandbox(), this, mabbobbers);
		espr.forward.face = ArrowButton.rFace;
		espr.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR3(getSandbox()));
			}
		});
		pRooms[0] = espr;
		espr.layoutManager.vadlidate();

		createFirstRoom();
		createSecondRoom();
		createThirdRoom();

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
		String[] mabbobbers = { "beginning/350", "beginning/351", null,
				"beginning/353", null, null, null, null, null, null,
				null, "beginning/3511", null, null, null, null };
		espr1 = new UpwardsEightSidedPR(getSandbox(), this, mabbobbers);
		espr1.forward.face = ArrowButton.lFace;
		espr1.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR3(getSandbox()));
			}
		});
		pRooms[1] = espr1;
		espr1.layoutManager.vadlidate();
	}

	@Override
	public void createSecondRoom() {
		String[] mabbobbers = { "beginning/360", null, null,
				null, null, "beginning/365", "beginning/366", null, null,
				"beginning/369", null, "beginning/3611", null, null, null,null };
		espr2 = new UpwardsEightSidedPR(getSandbox(), this, mabbobbers);
		espr2.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR3(getSandbox()));
			}
		});
		pRooms[2] = espr2;
	}

	@Override
	public void createThirdRoom() {
		fspr0 = new FourSidedPhotoRoom(getSandbox(), this, "beginning/370",
				null, null, "beginning/373");
		fspr0.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR3(getSandbox()));
			}
		});
		fspr0.forward.face = ArrowButton.lFace;
		pRooms[3] = fspr0;
	}

	@Override
	public void createFourthRoom() {

	}
}
