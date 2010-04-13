package kth.projects.slutprojekt;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class Craft {

    private String craft = "ship.png";
    private final double ACCEL     = 1.1,
    					 DEACCEL   = 0.99,
    					 MAXTHRUST = 2.0,
    					 MINTHRUST = 0.05;
    private double thrust;
    private double dx;
    private double dy;
    private double x;
    private double y;
    private int width;
    private int height;
    private int angle;
    private int dangle;
    private boolean visible;
    private Image image;
	private boolean keyUp, keyDown, keyLeft, keyRight = false;
	private LinkedList<Missile> missiles;


    public Craft() {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        missiles = new LinkedList<Missile>();
        visible = true;
        x = 40;
        y = 60;
        angle = 270;
        thrust = 0.0;
    }


    public void move() {
    	if(keyUp == true) {
    		moveForward();
    	}
    	else if(keyUp == false) {
    		deaccelerate();
    	}
    	else if(keyDown == true) {
    		moveBackwards();
    	}
    	if(keyLeft == true) {
    		rotateLeft();
    	}
    	else if(keyRight == true) {
    		rotateRight();
    	}
    	else if(keyLeft == false && keyRight == false) {
    		stopRotate();
    	}
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


	public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public double getAngle() {
    	return angle;
    }
    
    public LinkedList<Missile> getMissiles() {
    	return this.missiles;
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

    /*public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }*/

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_SPACE) {
        	fire();
        }

        if (key == KeyEvent.VK_LEFT) {
        	keyLeft = true;
        }

        if (key == KeyEvent.VK_RIGHT) {
            keyRight = true;
        }

        if (key == KeyEvent.VK_UP) {
        	keyUp = true;
        }

        if (key == KeyEvent.VK_DOWN) {
            keyDown = true;
        }
    }

	public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            keyLeft = false;
        }

        if (key == KeyEvent.VK_RIGHT) {
            keyRight = false;
        }

        if (key == KeyEvent.VK_UP) {
        	keyUp = false;
        }

        if (key == KeyEvent.VK_DOWN) {
        	keyDown = false;
        }
	}
	
	public void fire() {
        missiles.add(new Missile(x, y, angle));
    }
    
    public void rotateLeft() {
    	dangle = -1;
    }
	public void rotateRight() {
		dangle = 1;
	}
	private void stopRotate() {
		dangle = 0;
	}

    public void moveBackwards() {
    	double currentAngle = Math.toRadians(getAngle());
		
		dx = -(Math.cos(currentAngle));
		System.out.println(Math.cos(currentAngle));
		
		dy = -(Math.sin(currentAngle));
		System.out.println(Math.sin(currentAngle));
		
	}

	public void moveForward() {
		double currentAngle = Math.toRadians(getAngle());
		if(this.thrust <= 0.0) {
			this.thrust = 0.1;
		}
		else if(this.thrust*ACCEL > MAXTHRUST) {
			this.thrust = MAXTHRUST;
		}
		else {
			this.thrust *= ACCEL;
		}
		
		dx = (this.thrust)*Math.cos(currentAngle);
		dy = (this.thrust)*Math.sin(currentAngle);
	}
	
	public void deaccelerate() {
		double currentAngle = Math.toRadians(getAngle());
		if(this.thrust < MINTHRUST) {
			return;
		}
		else if(this.thrust*DEACCEL < MINTHRUST) {
			this.thrust = MINTHRUST;
		}
		else {
			this.thrust *= DEACCEL;
		}
		
		dx = (this.thrust)*Math.cos(currentAngle);		
		dy = (this.thrust)*Math.sin(currentAngle);
	}
}
