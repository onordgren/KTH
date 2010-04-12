package kth.projects.slutprojekt;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Craft {

    private String craft = "arrow.png";

    private int dx;
    private int dy;
    private int x;
    private int y;
    private int width;
    private int height;
    private int angle;
    private int dangle;
    private boolean visible;
    private Image image;


    public Craft() {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        visible = true;
        x = 40;
        y = 60;
        angle = 270;
    }


    public void move() {
    	angle += dangle;
    	
    	if(angle == 0) {
    		angle = 360;
    	}
        if(angle > 360 || angle <= -360) {
        	angle = 0;
        }
        
        x += dx;
    	y += dy;
        
        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
        
        //System.out.println(angle);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public double getAngle() {
    	return angle;
    }

    public Image getImage() {
        return image;
    }


    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        	dangle = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dangle = 1;
        }

        if (key == KeyEvent.VK_UP) {
        	moveForward();
        }

        if (key == KeyEvent.VK_DOWN) {
            moveBackwards();
        }
    }

    private void moveBackwards() {
    	double currentAngle = Math.toRadians(getAngle());
		
		dx = (int) (Math.round(Math.cos(currentAngle)));
		System.out.println(Math.cos(currentAngle));
		
		dy = (int) (Math.round(Math.sin(currentAngle)));
		System.out.println(Math.sin(currentAngle));
		
	}


	private void moveForward() {
		double currentAngle = Math.toRadians(getAngle());
		
		dx = (int) -(Math.round(Math.cos(currentAngle)));
		System.out.println(Math.cos(currentAngle));
		
		dy = (int) -(Math.round(Math.sin(currentAngle)));
		System.out.println(Math.sin(currentAngle));
	}


	public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dangle = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dangle = 0;
        }

        if (key == KeyEvent.VK_UP) {
        	dx = 0;
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
        	dx = 0;
            dy = 0;
        }
    }
}
