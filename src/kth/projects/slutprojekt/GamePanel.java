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
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer timer;
    private Ship ship;
    private Asteroid asteroid;
    private boolean ingame;
    private int B_WIDTH;
    private int B_HEIGHT;
	

    public GamePanel() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = true;

        setSize(800, 600);

        ship = new Ship();
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
                //g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(),this);
	            AffineTransform origXform = g2d.getTransform();
	            AffineTransform newXform = (AffineTransform)(origXform.clone());
	            //center of rotation is center of the panel
	            double xRot = ship.getX() + ship.getImage().getWidth(null) / 2;
	            double yRot = ship.getY() + ship.getImage().getHeight(null) / 2;
	            newXform.rotate(Math.toRadians(ship.getAngle()), xRot, yRot);
	            g2d.setTransform(newXform);
	            //draw image centered in panel
	            int x = (int)ship.getX();
	            int y = (int)ship.getY();
	            g2d.drawImage(ship.getImage(), x, y, this);             	
	            g2d.setColor(Color.RED);
	            g2d.draw3DRect(x, y, ship.getWidth(), ship.getHeight(), true);
	            g2d.setTransform(origXform); 
            }
            LinkedList<Missile> ms = ship.getMissiles();
            
            for (int i = 0; i < ms.size(); i++) {
                Missile m = ms.get(i);
                
                AffineTransform origXform = g2d.getTransform();
	            AffineTransform newXform = (AffineTransform)(origXform.clone());
                double xRot = m.getX() + m.getImage().getWidth(null) / 2;
                double yRot = m.getY() + m.getImage().getHeight(null) / 2;
                newXform.rotate(Math.toRadians(m.getAngle()), xRot, yRot);
                g2d.setTransform(newXform);
                int x = (int)m.getX();
                int y = (int)m.getY();
                g2d.drawImage(m.getImage(), x, y, this);
                g2d.setColor(Color.RED);
	            g2d.draw3DRect(x, y, m.getWidth(), m.getHeight(), true);
                g2d.setTransform(origXform); 
            }
            if(asteroid.isVisible()) {
            	int x = (int)asteroid.getX();
            	int y = (int)asteroid.getY();
            	g2d.drawImage(asteroid.getImage(), x, y, this);
            	g2d.setColor(Color.RED);
            	g2d.draw3DRect(x, y, asteroid.getWidth(), asteroid.getHeight(), false);
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


    public void actionPerformed(ActionEvent e) {
    	LinkedList<Missile> ms = ship.getMissiles();

        for (int i = 0; i < ms.size(); i++) {
            Missile m = (Missile) ms.get(i);
            if (m.isVisible()) 
                m.move();
            else ms.remove(i);
        }
        ship.move();
        checkCollisions();
        repaint();  
    }

    public void checkCollisions() {
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
    	LinkedList<Missile> ms = ship.getMissiles();
    	for(int i = 0; i < ms.size(); i++) {
    		Missile m = (Missile) ms.get(i);
    		if(m.getBounds().intersects(asteroid.getBounds())) {
    			m.setVisible(false);
    		}
    		else if(m.getX() > this.B_WIDTH || m.getY() > this.B_HEIGHT) {
    			m.setVisible(false);
    		}
    	}
    }
    

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            ship.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            ship.keyPressed(e);
        }
    }
}