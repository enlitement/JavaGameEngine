package test.gameStates;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import test.core.StateManager;
import test.gameInterfaces.Paintable;

public class MenuState extends State implements Paintable{
	public Button play, exit;
	
	public MenuState(final StateManager stateManager) {
		super(stateManager);
		panel = new Panel();
		name = "MenuState";

		play = new Button("Play");
		ActionListener playListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				removeMe();
				addNewState(new PlayState(stateManager), true);
				//stateManager.addTransitionState(new PlayState(stateManager));
			}
		};
		play.addActionListener(playListener);
		
		exit = new Button("Exit");
		ActionListener exitListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				stateManager.gameExit();
			}
		};
		exit.addActionListener(exitListener);
		panel.add(play);
		panel.add(exit);
		
		stateManager.canvas.frame.add(panel);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0,0,unused.Main.GWidth,unused.Main.GHeight);
	}

	@Override
	public void update() {
		handleInput();
	}
	
	public void handleInput() {
		
	}
}
