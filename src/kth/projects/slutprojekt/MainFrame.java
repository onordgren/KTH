package kth.projects.slutprojekt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MainFrame extends JFrame implements ActionListener {
   
	private static final long serialVersionUID = 1L;
	
	//Creates a new panel. The panel contains the main menu
    MenuPanel menu = new MenuPanel();
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
        
        //add the main menu to the frame
        add(menu);
        
        //ActionListeners for the buttons of the main menu
        menu.exit.addActionListener((ActionListener) this);
        menu.start.addActionListener((ActionListener) this);
        menu.options.addActionListener((ActionListener) this);
        menu.instructions.addActionListener((ActionListener) this);
        menu.about.addActionListener((ActionListener) this);
    }

	/**
	 * Listens to the buttons of the main menu
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// When start is pressed, remove the menu and launch the game
		if ("start".equals(e.getActionCommand())) {
           remove(menu);
           add(new GamePanel());
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
	}
}
