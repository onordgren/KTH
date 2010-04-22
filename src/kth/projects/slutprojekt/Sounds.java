package kth.projects.slutprojekt;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Sounds {
	Sequence music,
			 missile;
	Sequencer sequencer, sequencer2;
	
	 
     
	public Sounds(){
		try {
			
			sequencer = MidiSystem.getSequencer();
	        sequencer2 = MidiSystem.getSequencer();
	        
			// Create a sequencer for the sequence
	        sequencer.open();
	        sequencer2.open();
	        
	        // From file
	        music = MidiSystem.getSequence(new File("src/kth/projects/slutprojekt/resources/SMB.mid"));	        
	        
	        missile = MidiSystem.getSequence(new File("src/kth/projects/slutprojekt/resources/mj.mid"));
	        
				sequencer2.setSequence(missile);
			
	       
	        
	        
	    } catch (IOException b) {
	    } catch (MidiUnavailableException b) {
	    } catch (InvalidMidiDataException b) {
	    }

	}
	
	public void startMusic(){
		try {
			sequencer.setSequence(music);
		} catch (InvalidMidiDataException e) {			
		}
		sequencer.start();
		sequencer.setLoopCount(100);	
	}
	
	public void shootSound(){
		
		if(sequencer2.isRunning()){
			sequencer2.stop();
			sequencer2.close();
			try {
				sequencer2.open();
		        missile = MidiSystem.getSequence(new File("src/kth/projects/slutprojekt/resources/mj.mid"));
		        sequencer2.setSequence(missile);
			} catch (IOException b) {
		    } catch (MidiUnavailableException b) {
		    } catch (InvalidMidiDataException b) {
		    }
		}
		sequencer2.start();
	}
}
