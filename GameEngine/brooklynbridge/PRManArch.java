package brooklynbridge;

import java.awt.Graphics2D;

import engine.core.Sandbox;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;

/*
 * From images:
 * 240-263
 */
public class PRManArch extends RoomManager {
	public UpwardsEightSidedPR espr, espr1, espr2, espr3;

	public PRManArch(Sandbox sandbox) {
		super(sandbox);

		maxRooms = 4;
		pRooms = new PhotoRoom[maxRooms];

		String[] mabbobbers = { "beginning/410", "beginning/411",
				"beginning/412", "beginning/413", "beginning/414", null, null,
				null, "beginning/418", null, null, null, "beginning/4112",
				null, null, "beginning/4115" };
		espr = new UpwardsEightSidedPR(getSandbox(), this, mabbobbers);
		espr.forward.face = ArrowButton.lFace;
		espr.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR6(getSandbox()));
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
		String[] mabbobbers = { "beginning/420", "beginning/421",
				"beginning/422", "beginning/423", null, null, null,
				"beginning/427", null, null, null, null, null, null, null,
				"beginning/4215" };
		espr1 = new UpwardsEightSidedPR(getSandbox(), this, mabbobbers);
		espr1.forward.face = ArrowButton.rFace;
		espr1.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR6(getSandbox()));
			}
		});
		pRooms[1] = espr1;
	}

	@Override
	public void createSecondRoom() {
		String[] mabbobbers = { "beginning/430", null, "beginning/432",
				"beginning/433", null, "beginning/435", null, "beginning/437",
				"beginning/438", null, null, null, "beginning/4312", null,
				"beginning/4314", null };
		espr2 = new UpwardsEightSidedPR(getSandbox(), this, mabbobbers);
		espr2.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR6(getSandbox()));
			}
		});
		pRooms[2] = espr2;
	}

	@Override
	public void createThirdRoom() {
		String[] mabbobbers = { "beginning/440", "beginning/441",
				"beginning/442", "beginning/443", null, null, null,
				"beginning/447", null, null, null, null, null, null, null, null };
		espr3 = new UpwardsEightSidedPR(getSandbox(), this, mabbobbers);
		espr3.forward.face = ArrowButton.rFace;
		espr3.forward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				nextPhotoRoom(new PR6(getSandbox()));
			}
		});
		pRooms[3] = espr3;
	}

	@Override
	public void createFourthRoom() {

	}
}
