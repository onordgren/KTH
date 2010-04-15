package kth.projects.slutprojekt;

import javax.swing.JFrame;

public class Game extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Game() {

        add(new Board());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Awsm");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }
}
