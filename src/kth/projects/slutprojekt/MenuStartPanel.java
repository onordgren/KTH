package kth.projects.slutprojekt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuStartPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel	toolbar,
					startButtons;
	
	JButton newProfile,
			loadProfile,
			twoPlayers,
			hostGame,
			joinGame;
	
	ImageIcon back = new ImageIcon(this.getClass().getResource("back.png"));
	JLabel background = new JLabel(back);
	
	public MenuStartPanel(){
		setLayout(new BorderLayout());
		setBackground(Color.black);
		
		newProfile = 	new JButton("New Profile");
		loadProfile = 	new JButton("Load Profile");
		twoPlayers = 	new JButton("2P-game");
		hostGame = 		new JButton("Host Game");
		joinGame = 		new JButton("Join Game");		
		
		startButtons = new JPanel();
		startButtons.setLayout(new GridLayout(0, 1, 5, 5));
		startButtons.setBackground(Color.BLACK);

		startButtons.add(newProfile);
		startButtons.add(loadProfile);
		startButtons.add(twoPlayers);
		startButtons.add(hostGame);
		startButtons.add(joinGame);
		
		toolbar = new JPanel();
		toolbar.setBackground(Color.black);
		toolbar.add(startButtons);
		add(toolbar, BorderLayout.WEST);
		add(background, BorderLayout.CENTER);
	}

}
