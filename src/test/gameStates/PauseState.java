package test.gameStates;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import unused.StateManager;

public class PauseState extends State {
	
	public PauseState(final StateManager stateManager) {
		name = "PauseState";
		panel = new JPanel();
		this.stateManager = stateManager;
		JButton play = new JButton("Play");
		ActionListener playListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				stateManager.canvas.frame.remove(panel);
				stateManager.states.pop();
				((javax.swing.JFrame) stateManager.canvas.frame).validate();
				System.out.println(stateManager.states.peek().name);
			}
		};
		play.addActionListener(playListener);
		
		JButton menu = new JButton("Menu");
		ActionListener menuListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				stateManager.states.remove(panel);
				stateManager.states.pop();
				stateManager.states.push(new MenuState(stateManager));
				stateManager.canvas.validate();
				System.out.println(stateManager.states.peek().name);
			}
		};
		menu.addActionListener(menuListener);
		
		JButton exit = new JButton("Exit");
		ActionListener exitListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
				stateManager.gameExit();
			}
		};
		exit.addActionListener(exitListener);
		panel.add(play);
		panel.add(menu);
		panel.add(exit);
		
		stateManager.canvas.frame.add(panel);
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

}
