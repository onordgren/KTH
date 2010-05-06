package kth.projects.slutprojekt;

import javax.swing.*;
import java.awt.*;


public class MenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	// booleans keep track of which menu we are in
	boolean startViewed			= false,
			optionsViewed 		= false,
			instructionsViewed 	= false,
			aboutViewed 		= false;

	
	JPanel wrapper, 		// panel which holds everything
		   toolbar, 		// panel for the main buttons
		   mainMenuPanel, 	//panel for which toolbar-panel will go into
		   optionsButtons; 	// panel to hold the optionsPanel
	
	JButton start, //Five main buttons for the menu
			options,
			instructions,
			about,
			exit;
	
	// The backgrounds in the menus are made of these images
	ImageIcon mainBack,
			  aboutBack;
	// JLabels to show the images
	JLabel background, 
	       backgroundAbout;
	
	//Create the panels of the different menus
	MenuOptionsPanel menuOptionsPanel;
	MenuInstructionsPanel menuInstructionsPanel;
	MenuAboutPanel menuAboutPanel;
	MenuStartPanel menuStartPanel;
	
	/**
	 * Constructor
	 * Creates everything needed for a functional menu
	 */
	public MenuPanel(){	
	
		setLayout(new GridLayout());
		
		createWrapper(); 		// creates the wrapper panel which holds everything
		createBackgrounds(); 	// creates images
		createMainMenu(); 		// creates the main menu
		actionListeners(); 		//creates action listeners to each button
		
		menuOptionsPanel = 		new MenuOptionsPanel();
		menuInstructionsPanel = new MenuInstructionsPanel();
		menuAboutPanel = 		new MenuAboutPanel();
	
		add(wrapper);			//add the wrapper to the menu panel
	}	 
		
	/**
	 * Creates the wrapper panel.
	 */
	public void createWrapper(){
		wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());		
		wrapper.setBackground(Color.black);
	}
	
	/**
	 * Creates JLabels which contains images for backgrounds
	 */
	public void createBackgrounds(){
		mainBack = new ImageIcon(this.getClass().getResource("back.png"));
		background = new JLabel(mainBack);
		aboutBack = new ImageIcon(this.getClass().getResource("backAbout.png"));
		backgroundAbout = new JLabel(aboutBack);
	}
	
	/**
	 * Creates the main menu
	 */
	private void createMainMenu(){
		// Instantiate all the buttons
		start 			= new JButton("Start");
		options 		= new JButton("Options");
		instructions 	= new JButton("Instructions");
		about 			= new JButton("About");
		exit 			= new JButton("Exit");
		
		toolbar = new JPanel(); 						//create the panel to hold the buttons
		toolbar.setLayout(new GridLayout(0,1, 5, 5)); 	//Gridlayout with one column and spacing 5
		toolbar.setBackground(Color.gray);				// the menu will be gray
		
		// adds all the buttons to the panel
		toolbar.add(start);
		toolbar.add(options);
		toolbar.add(instructions);
		toolbar.add(about);
		toolbar.add(exit);
		
		mainMenuPanel = new JPanel(); 					//create the last panel for the main buttons
		mainMenuPanel.setBackground(Color.gray);
		mainMenuPanel.add(toolbar); 					// add the panel with the buttons
		
		wrapper.add(mainMenuPanel, BorderLayout.WEST); 	// add the main menu to the menu panel	
		wrapper.add(background, BorderLayout.CENTER);	// add a background image
	}	
	
	/**
	 * Creates actioncommands for each button
	 */
	private void actionListeners() {	
		start.setActionCommand("start");
		options.setActionCommand("options");
		instructions.setActionCommand("instructions");
		about.setActionCommand("about");
		exit.setActionCommand("exit");			
	}
	
	/**
	 * Show the start panel
	 */
	public void viewStart(boolean soundON){
		menuStartPanel = new MenuStartPanel(soundON);
		removeViewed(); //removes the menu which we are currently at
		wrapper.add(background, BorderLayout.CENTER);
		wrapper.add(menuStartPanel, BorderLayout.CENTER);	// add the other background
		wrapper.updateUI();
		startViewed = true;
	}
	
	/**
	 * When the options button is pressed this method shows the
	 * options panel
	 */
	public void viewOptions(){
		removeViewed();
		wrapper.add(background, BorderLayout.CENTER);
		wrapper.add(menuOptionsPanel, BorderLayout.CENTER); //add the options panel to the wrapper
		wrapper.updateUI();			//update the wrapper
		optionsViewed  = true;		// keep track that we are in the options panel
	}
	
	/**
	 * Show the instructions panel
	 */
	public void viewInstructions(){
		removeViewed();
		wrapper.remove(background);								// remove the background so we can add another backround
		wrapper.add(menuInstructionsPanel, BorderLayout.CENTER);	// add the other background
		wrapper.updateUI();
		instructionsViewed = true;
	}
	
	/**
	 * Shows the about panel
	 */
	public void viewAbout(){
		removeViewed();		
		wrapper.remove(background);
		wrapper.add(menuAboutPanel, BorderLayout.CENTER);
		wrapper.updateUI();
		aboutViewed = true;
	}
	
	/**
	 * Removes the menu panel we are currently looking at.
	 * Then we can add another menu to the panel.
	 */
	public void removeViewed(){
		if(startViewed){
			wrapper.remove(menuStartPanel);
			startViewed = false;
		}
		if(optionsViewed){
			wrapper.remove(menuOptionsPanel);
			optionsViewed = false;
		}
		if(aboutViewed){
			wrapper.remove(menuAboutPanel);
			aboutViewed = false;
		}
		if(instructionsViewed){
			wrapper.remove(menuInstructionsPanel);
			instructionsViewed = false;
		}
	}
	
	/**
	 * Check whether the music button in the options menu
	 * is set to on or off
	 * @return true if music in on, false otherwise
	 */
	public boolean musicON(){
		return menuOptionsPanel.musicON();
	}
}