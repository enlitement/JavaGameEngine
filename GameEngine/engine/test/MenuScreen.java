package engine.test;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.core.Screen;
import engine.editor.MapState;
import engine.gui.ActionEvent;
import engine.gui.ActionListener;
import engine.gui.GradientButton;
import engine.gui.MenuLayoutManager;
import engine.rooms.Room;

public class MenuScreen extends Room {

	private MenuState _state;
	public GradientButton play, edit, exit;
	public MenuLayoutManager _layout;

	public MenuScreen(MenuState menu_manager) {
		this._state = menu_manager;
		_layout = new MenuLayoutManager(this);
		play = new GradientButton("Play");
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(engine.gui.ActionEvent arg0) {
				_state.addState(new PlayState(_state.getStateManager()));
				_state.removeThis();

			}
		});

		edit = new GradientButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				_state.addState(new MapState(_state.getStateManager()));
				_state.removeThis();

			}
		});

		exit = new GradientButton("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Screen.exit();
			}

		});

		_layout.addButton(play);
		_layout.addButton(edit);
		_layout.addButton(exit);
		_layout.validate();
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
		_layout.paint(g);
	}

	@Override
	public void update() {
		_layout.update();
	}
}
