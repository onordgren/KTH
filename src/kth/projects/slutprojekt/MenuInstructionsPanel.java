package kth.projects.slutprojekt;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuInstructionsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	ImageIcon instructionsBack = new ImageIcon(this.getClass().getResource("backoptions.png"));
	JLabel backgroundInstructions = new JLabel(instructionsBack);
	
	public MenuInstructionsPanel(){

		setLayout(new BorderLayout());
		setBackground(Color.black);
		add(backgroundInstructions, BorderLayout.CENTER);
	}
}
