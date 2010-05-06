
package kth.projects.slutprojekt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class MenuOptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JPanel toolbar,			//Jpanel to hold different buttons
				   optionsButtons;
	
	JToggleButton music,	//Toggle buttons to turn sound an music on/off
				  sound;

	public MenuOptionsPanel(){
		sound = new JToggleButton("Sound on", true);
		music = new JToggleButton("Music on", true);

		setLayout(new BorderLayout());
		setBackground(Color.black);
		
		optionsButtons = new JPanel();
		optionsButtons.setLayout(new GridLayout(0, 1, 5, 5));
		optionsButtons.setBackground(Color.BLACK);
		optionsButtons.add(sound);
		optionsButtons.add(music);
		
		toolbar = new JPanel();
		toolbar.setBackground(Color.black);
		toolbar.add(optionsButtons);
		add(toolbar, BorderLayout.WEST);
	}
	
	/**
	 * Returns the value of the music button
	 * True if we want music, false otherwise
	 */
	public boolean musicON(){
		return music.isSelected();
	}
	
	/**
	 * Returns the value of the sound button
	 * True if we want sound, false otherwise
	 */
	public boolean soundON(){
		return sound.isSelected();
	}
}
