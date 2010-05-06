
package kth.projects.slutprojekt;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JApplet;

public class Sounds extends JApplet{

	private static final long serialVersionUID = 1L;

	 AudioFormat audioFormat;
	 AudioInputStream audioInputStream;
	 SourceDataLine sourceDataLine;
   
	public Sounds() {
	}
	
	/**
	 * Reads a sound file.
	 * @param file specifies which file we want to play. 
	 * Currently the only sounds are music or missile.
	 */
	public void playSound(String file){
		try{
		      File soundFile = new File("src/kth/projects/slutprojekt/resources/" + file + ".wav");
		      audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		      audioFormat = audioInputStream.getFormat();
		      System.out.println(audioFormat);

		      DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

		      sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);

		      //Create a thread to play the sound on.
		      new PlayThread().start();
		      }catch (Exception e) {
		    }
		  }

	/**
	 * Plays the sound
	 */
	class PlayThread extends Thread{
	    byte tempBuffer[] = new byte[10000];

	    public void run(){
	    	// Try to open the sound file
		    try{
		      sourceDataLine.open(audioFormat);
		      sourceDataLine.start();

		      int cnt;
		      //Keep looping until the input read method
		      // returns -1 for empty stream 
		      while((cnt = audioInputStream.read(tempBuffer,0,tempBuffer.length)) != -1){
		        if(cnt > 0){
		          //Write data to the internal buffer of
		          // the data line where it will be
		          // delivered to the speaker.
		          sourceDataLine.write(tempBuffer, 0, cnt);
		        }
		      }
		      //Block and wait for internal buffer of the
		      // data line to empty.
		      sourceDataLine.drain();
		      sourceDataLine.close();	      
		      }catch (Exception e) {
		      }
		  }
	}
}



