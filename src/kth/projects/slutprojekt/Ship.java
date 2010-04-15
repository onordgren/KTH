package kth.projects.slutprojekt;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Ship extends Sprite {

    private static String ship = "ship.png";
    private final double ACCEL     = 1.1,
    					 DEACCEL   = 0.99,
    					 MAXTHRUST = 2.0,
    					 MINTHRUST = 0.05;
    private double thrust;
    private double dx;
    private double dy;
    private int dangle;
	private boolean keyUp, keyDown, keyLeft, keyRight = false;
	private LinkedList<Missile> missiles;


    public Ship() {
    	super(ship, 40, 60, true);
        this.missiles = new LinkedList<Missile>();
        this.angle = 270;
        this.thrust = 0.0;
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
        
        if (x < -60) {
            x = 800;
        }
        if(x > 800) {
        	x= -60;
        }
        if(y > 600) {
        	y = -60;
        }

        if (y < -60) {
            y = 600;
        }
        
        //System.out.println(angle);
    }


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
    
    public LinkedList<Missile> getMissiles() {
    	return this.missiles;
    }
    
	public void fire() {
        missiles.add(new Missile(this.x, this.y, this.angle));
    }
    
    public void rotateLeft() {
    	this.dangle = -1;
    }
	public void rotateRight() {
		this.dangle = 1;
	}
	private void stopRotate() {
		this.dangle = 0;
	}

    public void moveBackwards() {
    	double currentAngle = Math.toRadians(this.getAngle());
		
		this.dx = -(Math.cos(currentAngle));
		System.out.println(Math.cos(currentAngle));
		
		this.dy = -(Math.sin(currentAngle));
		System.out.println(Math.sin(currentAngle));
		
	}

	public void moveForward() {
		double currentAngle = Math.toRadians(this.getAngle());
		if(this.thrust <= 0.0) {
			this.thrust = 0.1;
		}
		else if(this.thrust*ACCEL > MAXTHRUST) {
			this.thrust = MAXTHRUST;
		}
		else {
			this.thrust *= ACCEL;
		}
		
		this.dx = (this.thrust)*Math.cos(currentAngle);
		this.dy = (this.thrust)*Math.sin(currentAngle);
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
		
		this.dx = (this.thrust)*Math.cos(currentAngle);		
		this.dy = (this.thrust)*Math.sin(currentAngle);
	}
}
