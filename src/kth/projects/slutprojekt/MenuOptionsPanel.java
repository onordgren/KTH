package kth.projects.slutprojekt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class MenuOptionsPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel optionsButtons;
	
	JButton sound;
    Sequencer sequencer;

	public MenuOptionsPanel(){
		sound = new JButton("Sound off");

		sound.setActionCommand("sound");

		
		setLayout(new BorderLayout());
		setBackground(Color.black);
		optionsButtons = new JPanel();
		optionsButtons.setLayout(new FlowLayout());
		optionsButtons.setBackground(Color.BLACK);
		sound.setActionCommand("soundON");
		optionsButtons.add(sound);
		add(optionsButtons, BorderLayout.WEST);


	 
	}

}
