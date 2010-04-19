package kth.projects.slutprojekt;

import javax.swing.*;

import java.awt.*;


public class MenuPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	// booleans keep track of which menu we are in
	boolean optionsViewed = false,
			instructionsViewed = false,
			aboutViewed = false;
	
	JPanel wrapper, 		// panel which holds everything
		   toolbar, 		// panel for the main buttons
		   mainMenuPanel, 	//panel for which toolbar-panel will go into
		   optionsPanel, 	// panel to hold options
		   optionsButtons, 	// panel to hold the optionsPanel
		   instructionsPanel,
		   aboutPanel;
	
	JButton start, //Five main buttons for the menu
			options,
			instructions,
			about,
			lol,
			exit;
	
	JToggleButton sound; //button to toggle sound on/off
	
	// The backgrounds in the menus are made of these images
	ImageIcon mainBack,
			  optionsBack,
			  aboutBack;
	// JLabels to show the images
	JLabel background, 
	       backgroundOptions,
	       backgroundAbout;
	
	/**
	 * Constructor
	 * Creates everything needed for a functional menu
	 */
	public MenuPanel(){	
		setLayout(new GridLayout());
							
		createWrapper(); 		// creates the wrapper panel which holds everything
		createBackgrounds(); 	// creates images
		createMainMenu(); 		// creates the main menu
		createOptions(); 		// creates the options panel
		createInstructions(); 	// creates the instructions panel
		createAbout(); 			// creates the about panel
		actionListeners(); 		//creates action listeners to each button
		
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
		optionsBack = new ImageIcon(this.getClass().getResource("backoptions.png"));
		backgroundOptions = new JLabel(optionsBack);
		aboutBack = new ImageIcon(this.getClass().getResource("backAbout.png"));
		backgroundAbout = new JLabel(aboutBack);
	}
	
	/**
	 * Creates the main menu
	 */
	private void createMainMenu(){
		// Instantiate all the buttons
		start = new JButton("Start");
		options = new JButton("Options");
		instructions = new JButton("Instructions");
		about = new JButton("About");
		exit = new JButton("Exit");
		
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
	 * Creates the options panel
	 */
	private void createOptions(){	
			sound = new JToggleButton("Sound on/off");	//button to toggle sound on/off
		
			optionsPanel = new JPanel();
			optionsPanel.setLayout(new BorderLayout());
			optionsPanel.setBackground(Color.black);
			
			optionsButtons = new JPanel();
			optionsButtons.setLayout(new FlowLayout());
			optionsButtons.setBackground(Color.black);
			
			optionsButtons.add(sound);
			optionsPanel.add(optionsButtons, BorderLayout.WEST);
	}
	
	/**
	 * Creates the instructions panel
	 */
	private void createInstructions(){
		instructionsPanel = new JPanel();
		instructionsPanel.setLayout(new BorderLayout());
		instructionsPanel.setBackground(Color.black);
		instructionsPanel.add(backgroundOptions, BorderLayout.CENTER);
	}
	
	/**
	 * Creates the about panel
	 */
	private void createAbout(){
		aboutPanel = new JPanel();
		aboutPanel.setLayout(new BorderLayout());
		aboutPanel.setBackground(Color.black);
		aboutPanel.add(backgroundAbout, BorderLayout.CENTER);
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
	 * When the options button is pressed this method shows the
	 * options panel
	 */
	public void viewOptions(){
		// Check if we are currently in the instructions panel
		// if so, remove it from the wrapper
		if(instructionsViewed){
			wrapper.remove(instructionsPanel);
			wrapper.add(background, BorderLayout.CENTER);
			instructionsViewed = false;
		}
		// Check if we are in the about panel
		if(aboutViewed){
			wrapper.remove(aboutPanel);
			wrapper.add(background, BorderLayout.CENTER);
			aboutViewed = false;
		}

		wrapper.add(optionsPanel, BorderLayout.CENTER); //add the options panel to the wrapper
		wrapper.updateUI();			//update the wrapper
		optionsViewed  = true;		// keep track that we are in the options panel
	}
	
	/**
	 * Show the instructions panel
	 */
	public void viewInstructions(){
		// Check if we are currently in the options or the about panel
		if(optionsViewed)
			wrapper.remove(optionsPanel);
		if(aboutViewed)
			wrapper.remove(aboutPanel);
		
		wrapper.remove(background);								// remove the background so we can add another backround
		wrapper.add(instructionsPanel, BorderLayout.CENTER);	// add the other background
		wrapper.updateUI();
		instructionsViewed = true;
	}
	
	public void viewAbout(){
		if(optionsViewed)
			wrapper.remove(optionsPanel);
		if(instructionsViewed)
			wrapper.remove(instructionsPanel);
		wrapper.remove(background);
		wrapper.add(aboutPanel, BorderLayout.CENTER);
		wrapper.updateUI();
		aboutViewed = true;
	}
}
