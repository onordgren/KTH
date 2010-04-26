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
	
	JButton hostGame,
			joinGame;
	
	ImageIcon back = new ImageIcon(this.getClass().getResource("back.png"));
	JLabel background = new JLabel(back);
	
	public MenuStartPanel(){
		setLayout(new BorderLayout());
		setBackground(Color.black);
		
		hostGame = 		new JButton("Host Game");
		joinGame = 		new JButton("Join Game");		
		
		startButtons = new JPanel();
		startButtons.setLayout(new GridLayout(0, 1, 5, 5));
		startButtons.setBackground(Color.BLACK);

		startButtons.add(hostGame);
		startButtons.add(joinGame);
		
		toolbar = new JPanel();
		toolbar.setBackground(Color.black);
		toolbar.add(startButtons);
		
		add(toolbar, BorderLayout.WEST);
	}

}
