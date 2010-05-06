
package kth.projects.slutprojekt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.sound.midi.Sequencer;
import javax.swing.JPanel;
import javax.swing.JToggleButton;


public class MenuOptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel toolbar,
				   optionsButtons;
	
	JToggleButton music,
				  sound;
    Sequencer sequencer;

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
	
	public boolean musicON(){
		return music.isSelected();
	}
	
	public boolean soundON(){
		return sound.isSelected();
	}
}
