package test.gameStates;

import test.core.KeyboardInput;
import test.core.StateManager;

public abstract class AbstractPlayState extends State{
	public boolean pause;
	public KeyboardInput keyboard;
	
	public AbstractPlayState(StateManager stateManager) {
		super(stateManager);
		name = "PlayState";
		pause = false;
		keyboard = getKeyboard();
	}
	
	/*public JButton makePauseButton() {
		JButton pauseButton = new JButton("Pause");
		ActionListener pauseListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addNewState(new PauseState(stateManager), false);
				pause = true;
			}
		};
		pauseButton.addActionListener(pauseListener);
		return pauseButton;
	}
	
	public JButton makeMenuButton() {
		JButton menuButton = new JButton("Menu");
		ActionListener menuListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeMe();
				addNewState(new MenuState(stateManager), false);
			}
		};
		menuButton.addActionListener(menuListener);
		return menuButton;
	}
	*/
	public void unPause() {
		pause = false;
	}
	
	//public abstract void initSwing();
	public abstract void initEntities();
	public abstract void initResources();
}
