package engine.test;

public class MenuState extends State {

	public MenuScreen menu;

	public MenuState(StateManager _manager) {
		super(_manager);
		menu = new MenuScreen(this);
		addRoom(menu);
	}
}
