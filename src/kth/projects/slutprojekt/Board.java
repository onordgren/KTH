package kth.projects.slutprojekt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

    private Timer timer;
    private Craft craft;
    private Missile missile;
    private boolean ingame;
    private int B_WIDTH;
    private int B_HEIGHT;
	

    public Board() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = true;

        setSize(800, 600);

        craft = new Craft();

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

            if (craft.isVisible()) {
                //g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(),this);
	            AffineTransform origXform = g2d.getTransform();
	            AffineTransform newXform = (AffineTransform)(origXform.clone());
	            //center of rotation is center of the panel
	            double xRot = craft.getX() + craft.getImage().getWidth(null) / 2;
	            double yRot = craft.getY() + craft.getImage().getHeight(null) / 2;
	            newXform.rotate(Math.toRadians(craft.getAngle()), xRot, yRot);
	            g2d.setTransform(newXform);
	            //draw image centered in panel
	            int x = (int)craft.getX();
	            int y = (int)craft.getY();
	            g2d.drawImage(craft.getImage(), x, y, this);
	            g2d.setTransform(origXform);   	
            }
            LinkedList<Missile> ms = craft.getMissiles();
            
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
                g2d.setTransform(origXform); 
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
    	LinkedList<Missile> ms = craft.getMissiles();

        for (int i = 0; i < ms.size(); i++) {
            Missile m = (Missile) ms.get(i);
            if (m.isVisible()) 
                m.move();
            else ms.remove(i);
        }
        craft.move();
        checkCollisions();
        repaint();  
    }

    public void checkCollisions() {

    }
    

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
        }
    }
}