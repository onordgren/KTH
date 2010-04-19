package kth.projects.slutprojekt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class MenuOptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel optionsPanel,
				   optionsButtons;
	
	JToggleButton sound;
	
	public MenuOptionsPanel(){
		optionsPanel = new JPanel();
		optionsPanel.setLayout(new BorderLayout());
		optionsPanel.setBackground(Color.black);
		optionsButtons = new JPanel();
		optionsButtons.setLayout(new FlowLayout());
		sound = new JToggleButton("Sound on/off");
		optionsButtons.add(sound);
		optionsPanel.add(optionsButtons, BorderLayout.WEST);
	}

}
