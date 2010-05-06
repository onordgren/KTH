
package kth.projects.slutprojekt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuStartPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel	toolbar,
					startButtons,
					joinToolbar,
					joinButtons;
	
	JButton hostGame,
			joinGame,
			OK;
	
	ImageIcon back = new ImageIcon(this.getClass().getResource("back.png"));
	JLabel background = new JLabel(back);
	
	JTextField IPtextField;
	
	
	Thread thread;
	
	public MenuStartPanel(){
		setLayout(new BorderLayout());
		setBackground(Color.black);
		
		
		
		createStart();
		
		joinGame.addActionListener((ActionListener) this);
		hostGame.addActionListener((ActionListener) this);
		
		try {
			thread = new GameServer();
		} catch (Exception e) {
		}
	}
	
	public void createStart(){
		hostGame = new JButton("Host Game");
		joinGame = new JButton("Join Game");
		
		hostGame.setActionCommand("host");
		joinGame.setActionCommand("join");
		
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
	
	public void joinGame(){
		OK = new JButton("OK");
		OK.setActionCommand("OK");
		OK.addActionListener((ActionListener) this);

		IPtextField = new JTextField();
		IPtextField.setPreferredSize(new Dimension(100, 20));
		
		joinButtons = new JPanel();
		joinButtons.setLayout(new GridLayout(0, 1, 5, 5));
		joinButtons.setBackground(Color.BLACK);
		
		joinButtons.add(IPtextField);
		joinButtons.add(OK);
		
		joinToolbar = new JPanel();
		joinToolbar.setBackground(Color.black);
		joinToolbar.add(joinButtons);
		
		remove(toolbar);
		add(background, BorderLayout.CENTER);
		add(joinToolbar, BorderLayout.WEST);
		updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("join".equals(e.getActionCommand())) {	
			System.out.println("hej");
			joinGame();
		}
		if ("host".equals(e.getActionCommand())) {
			thread.start();
			GameClient.sharedInstance("localhost");
		}
		if ("OK".equals(e.getActionCommand())) {
			String IP = IPtextField.getText();
			GameClient.sharedInstance(IP);
		}
		
	}

}
