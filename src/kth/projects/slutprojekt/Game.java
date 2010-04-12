package kth.projects.slutprojekt;

import javax.swing.JFrame;

public class Game extends JFrame {

    public Game() {

        add(new Board());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Star");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }
}
