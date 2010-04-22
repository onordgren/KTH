package kth.projects.slutprojekt;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JFrame;

public class MainFrame extends JFrame implements ActionListener {
   
	private static final long serialVersionUID = 1L;
	
	//Creates a new panel. The panel contains the main menu
    MenuPanel menu = new MenuPanel();;
    Container contentPane;
    Sounds sound = new Sounds();
    
	/*
	 * Sets up a new JFrame
	 */
	public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Awsm");
        setResizable(false);
        setVisible(true);
        

        contentPane = this.getContentPane();

        
        //add the main menu to the frame
        contentPane.add(menu);
        
        //ActionListeners for the buttons of the main menu
        menu.exit.addActionListener((ActionListener) this);
        menu.start.addActionListener((ActionListener) this);
        menu.options.addActionListener((ActionListener) this);
        menu.instructions.addActionListener((ActionListener) this);
        menu.about.addActionListener((ActionListener) this);
        menu.menuOptionsPanel.sound.addActionListener((ActionListener) this);
    }

	/**
	 * Listens to the buttons of the main menu
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// When start is pressed, remove the menu and launch the game
		if ("start".equals(e.getActionCommand())) {	
			sound.startMusic();
			GamePanel gamePanel = new GamePanel();
			contentPane.add(gamePanel); // Add gamepanel to the contentpane
			contentPane.remove(menu); // Remove menu from the contentpane
			//menu.viewStart();
			
        } 
		if ("options".equals(e.getActionCommand())) {
	          menu.viewOptions(); // show options
	       } 
		if ("instructions".equals(e.getActionCommand())) {
	          menu.viewInstructions(); // show instructions
	       } 
		if ("about".equals(e.getActionCommand())) {
	          menu.viewAbout(); //show about
	       } 
		if ("exit".equals(e.getActionCommand())) {
            System.exit(1); //exit
        } 
		if ("sound".equals(e.getActionCommand())) {
//			System.out.println("hej");
//			try {
//		        // From file
//		        Sequence sequence = MidiSystem.getSequence(new File("D:\\mj.mid"));
//		        Sequencer sequencer = MidiSystem.getSequencer();
//		        
//		        // Create a sequencer for the sequence
//		        sequencer.open();
//		        sequencer.setSequence(sequence);
//		        // Start playing
//		        sequencer.start();
//		    } catch (IOException b) {
//		    } catch (MidiUnavailableException b) {
//		    } catch (InvalidMidiDataException b) {
//		    }
		}
	}
}
