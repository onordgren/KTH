package kth.projects.slutprojekt;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel implements ActionListener {

    /**
	 * 
	 */
	Container shell = getRootPane();
	Container menu = new Container();
	Container options = new Container();
	Container instructions = new Container();
	Container about = new Container();
	Container board = new Container();
		
	ImageIcon ii = new ImageIcon("D:\\start.png");
	
	JPanel buttonPanel = new JPanel();
	JPanel backButton = new JPanel();
	JPanel optionButtons = new JPanel();

	JButton startB = new JButton(ii);
	JButton optionsB = new JButton("options");
	JButton intructionsB = new JButton("instructions");
	JButton aboutB = new JButton("about");
	JButton exitB = new JButton("exit");
	JButton backB = new JButton("back");
	JButton back2 = new JButton("back");
	JToggleButton sound = new JToggleButton("Sound", true);
	

	private static final long serialVersionUID = 1L;
	
	public Menu() {        
		setVisible(true);
		setSize(800, 600);
		buttons();
		createMenu();
		add(menu);
		
        exitB.addActionListener((ActionListener) this);
        aboutB.addActionListener((ActionListener) this);
        intructionsB.addActionListener((ActionListener) this);
        optionsB.addActionListener((ActionListener) this);
        startB.addActionListener((ActionListener) this);
        backB.addActionListener((ActionListener) this);
    }
	
//    public static void main(String[] args) {
//        new Menu();
//    }
    
    public void actionPerformed(ActionEvent e) {
        if ("exit".equals(e.getActionCommand())) {
            System.exit(1);
        } 
        else if("start".equals(e.getActionCommand())) {
        	menu.setVisible(false);
        	
        } 
//        else if("about".equals(e.getActionCommand())) {
//        	menu.setVisible(false);
//        	about.setVisible(true);
//        } 
//        else if("instructions".equals(e.getActionCommand())) {
//        	menu.setVisible(false);
//        	instructions.setVisible(true);
//        } 
//        else if("options".equals(e.getActionCommand())) {
//        	options.setVisible(true);

//        	menu.setVisible(false);
        }
//        else if("back".equals(e.getActionCommand())) {
//        	menu.setVisible(true);
//        	options.setVisible(false);
//        	instructions.setVisible(false);
//        }
//    }
    
//    private void createShell(){
////    	shell.setLayout(new FlowLayout(FlowLayout.LEADING));
////    	shell.setBackground(Color.GREEN);
////		shell.setVisible(true);
//		
//    	createMenu();
//		shell.add(menu);
//		
//		createOptions();
//		shell.add(options);
//		buttons();
//
////		createInstructions();
////		shell.add(instructions);
////		
////		createAbout();
////		shell.add(about);
//    }
    private void createMenu(){
    	menu.setLayout(new FlowLayout(FlowLayout.LEADING));
		menu.add(buttonPanel);	
		menu.setBackground(Color.BLACK);
		menu.setVisible(true);
    }
//    
//    private void createOptions(){
//    	options.setLayout(new FlowLayout(FlowLayout.LEADING));
//    	options.add(backButton);
//    	options.setBackground(Color.PINK);
//    	options.setVisible(false);
//
//    }
    
//    private void createInstructions(){
//    	instructions.setLayout(new FlowLayout(FlowLayout.LEADING));
//    	instructions.add(backB);
//    	instructions.setBackground(Color.BLACK);
//    	instructions.setVisible(false);
//
//    }
//    
//    private void createAbout(){
//    	about.setLayout(new FlowLayout(FlowLayout.LEADING));
//    	about.add(backB);
//    	about.setBackground(Color.BLACK);
//    	about.setVisible(false);
//
//    }
    
//    private void createBoard(){
//    	gameBoard = new Board();
//    	board.add(gameBoard);
//    	board.setVisible(true);
//    }
    
    private void buttons(){
    	
    	startB.setPreferredSize(new Dimension(120, 60));
		optionsB.setPreferredSize(new Dimension(120, 60));
		intructionsB.setPreferredSize(new Dimension(120, 60));
		aboutB.setPreferredSize(new Dimension(120, 60));
		exitB.setPreferredSize(new Dimension(120, 60));
		backB.setPreferredSize(new Dimension(120, 60));
		back2.setPreferredSize(new Dimension(120, 60));

    	buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
    	buttonPanel.setBackground(Color.BLACK);
		buttonPanel.add(startB);
		buttonPanel.add(optionsB);
		buttonPanel.add(intructionsB);
		buttonPanel.add(aboutB);
		buttonPanel.add(exitB);
		
		startB.setActionCommand("start");
		optionsB.setActionCommand("options");
		intructionsB.setActionCommand("instructions");
		aboutB.setActionCommand("about");
		exitB.setActionCommand("exit");
		backB.setActionCommand("back");
		
		optionButtons.setVisible(false);
		optionButtons.setLayout(new GridLayout(5, 1, 10, 10));
		optionButtons.setBackground(Color.BLACK);
		optionButtons.add(sound);
		
		backButton.setLayout(new GridLayout(5, 1, 10, 10));
		backButton.setBackground(Color.PINK);
		backButton.setVisible(true);
		backButton.add(new JLabel(""));
		backButton.add(new JLabel(""));
		backButton.add(new JLabel(""));
		backButton.add(new JLabel(""));
		backButton.add(backB);
		

    }

}
