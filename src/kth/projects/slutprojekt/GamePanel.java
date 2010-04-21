package kth.projects.slutprojekt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

public class GamePanel extends JPanel implements ActionListener {

    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Timer timer;
    private Ship ship;
    private Asteroid asteroid;
    private Player player;
    private boolean ingame;
    private int B_WIDTH;
    private int B_HEIGHT;
    Server server = new Server();
	

    public GamePanel() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = true;

        setSize(800, 600);
        
        player = new Player();
        ship = player.getShip();
        asteroid = new Asteroid();

        timer = new Timer(5, this);
        timer.start();
    }

    public void addNotify() {
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();   
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (ingame) {
            Graphics2D g2d = (Graphics2D)g;

            if (ship.isVisible()) {
            	ship.draw(g2d); // Draws the ship on the current position
            }
            
            LinkedList<Missile> missiles = ship.getMissiles(); // Array containing the current ship's missiles
            for (int i = 0; i < missiles.size(); i++) {
                Missile missile = missiles.get(i);       
                missile.draw(g2d); // Draws the missile on the current position
            }
            
            if(asteroid.isVisible()) {
            	asteroid.draw(g2d);
            }
            g2d.setColor(Color.WHITE);


        } else {
            String msg = "Game Over";
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = this.getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
                         B_HEIGHT / 2);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    /**
     * Runs every time the timer ticks. Moves all the objects and repaints the panel.
     */
    public void actionPerformed(ActionEvent e) {
    	LinkedList<Missile> missiles = ship.getMissiles();
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = (Missile) missiles.get(i);
            if (missile.isVisible()) { 
            	missile.move();
            }
            else {
            	missiles.remove(i);
            }
        }
        
        ship.move();
        checkCollisions();
        repaint();  
    }

    private void checkCollisions() {
    	checkShipCollisions();
    	checkMissileCollisions();
    }
    
    private void checkShipCollisions() {
    	if(ship.getBounds().intersects(asteroid.getBounds())) {
    		ship.setVisible(false);
    		ingame = false;
    	}
    }
    
    private void checkMissileCollisions() {
    	LinkedList<Missile> missiles = ship.getMissiles();
    	for(int i = 0; i < missiles.size(); i++) {
    		Missile missile = (Missile) missiles.get(i);
    		if(missile.getBounds().intersects(asteroid.getBounds())) {
    			missile.setVisible(false);
    		}
    		missile.checkOuterBounds(B_WIDTH, B_HEIGHT);
    	}
    }
    

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }
}