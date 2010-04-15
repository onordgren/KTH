package kth.projects.slutprojekt;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainFrame() {

        add(new GamePanel());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Awsm");
        setResizable(false);
        setVisible(true);
    }
}
