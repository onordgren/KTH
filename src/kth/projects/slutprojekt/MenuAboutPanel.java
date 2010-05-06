package kth.projects.slutprojekt;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuAboutPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// An image that will be set as background
	ImageIcon aboutBack = new ImageIcon(this.getClass().getResource("backAbout.png"));
	JLabel backgroundAbout = new JLabel(aboutBack);
	
	public MenuAboutPanel(){
		setLayout(new BorderLayout());
		setBackground(Color.black);
		add(backgroundAbout, BorderLayout.CENTER);
	}
}