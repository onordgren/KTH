
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

	private static final long serialVersionUID = 1L;
	
	private JPanel	toolbar,		//Jpanels to hold different buttons
					startButtons,
					joinToolbar,
					joinButtons;
	
	JButton hostGame,	
			joinGame,
			OK;
	
	// Image for the background
	ImageIcon back = new ImageIcon(this.getClass().getResource("back.png"));
	JLabel background = new JLabel(back);
	
	JTextField IPtextField; //Textfield to enter the IP you want to join
	
	Thread thread;	// New thread to run the server on.
	
    Sounds sound = new Sounds();
    
    boolean musicON;

	public MenuStartPanel(boolean musicON){
		this.musicON = musicON;
		
		setLayout(new BorderLayout());
		setBackground(Color.black);
		
		createStart();	// Creates the start menu
		
		joinGame.addActionListener((ActionListener) this);
		hostGame.addActionListener((ActionListener) this);
	}
	
	/**
	 * Creates the start menu
	 */
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
	
	/**
	 * Creates the menu to be shown when we press the join button
	 */
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

	/**
	 * Listens to the actions of the buttons
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("join".equals(e.getActionCommand())) {	
			joinGame(); // Show the join menu
		}
		if ("host".equals(e.getActionCommand())) {
			if(musicON)
				sound.playSound("music");
			try {
				thread = new GameServer(); //Create a new server on the thread
			} catch (Exception e1) {
			}
			thread.start(); //Start the server
			GameClient.sharedInstance("localhost"); //Join your own server
		}
		if ("OK".equals(e.getActionCommand())) {
			if(musicON)
				sound.playSound("music");
			String IP = IPtextField.getText(); 	//Get the IP from the text field
			GameClient.sharedInstance(IP); 		//Join with that IP
		}		
	}
}
