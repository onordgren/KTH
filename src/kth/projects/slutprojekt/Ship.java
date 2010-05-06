package kth.projects.slutprojekt;

import java.util.LinkedList;

public class Ship extends Sprite {
    private static String ship = "ship.png";
	private boolean boostingForward, rotatingLeft, rotatingRight = false;
	private LinkedList<Missile> missiles;

    public Ship(double x, double y) {
    	super(ship, x, y, true);
        this.missiles  = new LinkedList<Missile>();
        this.angle     = 270;
        this.thrust    = 0.0;
        this.accel     = 1.8;
        this.deaccel   = 0.99;
        this.maxthrust = 2.0;
        this.minthrust = 0.05;
    }

    
    
    /**
     * Returns an array with all missile in the current ship.
     * @return
     */
    public LinkedList<Missile> getMissiles() {
    	return this.missiles;
    }
    
    /**
     * Adds a missile to the missile array. The missile is given the current x, y
     * position and angle.
     */
	public void fire(int playerID) {
        missiles.add(new Missile(playerID, this.x, this.y, this.angle, this.thrust));
    }
    
	/**
	 * Rotates the ship left.
	 */
    public void rotateLeft() {
    	this.dangle = -1;
    }
    
    /**
     * Rotates the ship right.
     */
	public void rotateRight() {
		this.dangle = 1;
	}
	
	/**
	 * Stops the ship from rotating.
	 */
	public void stopRotate() {
		this.dangle = 0;
	}
    
    /**
     * Moves the ship forward. Sets dx and dy to higher values in the a direction
     * based on current thrust and angle.
     */
	public void moveForward() {
		double currentAngle = Math.toRadians(this.getAngle());
		if(this.thrust <= 0.0) {
			this.thrust = 0.1;
		}
		else if(this.thrust*this.accel > this.maxthrust) {
			this.thrust = this.maxthrust;
		}
		else {
			this.thrust *= this.accel;
		}	
		this.dx = (this.thrust)*Math.cos(currentAngle);
		this.dy = (this.thrust)*Math.sin(currentAngle);
	}
	
	/**
	 * Deaccelerates the ship. 
	 */
	public void deaccelerate() {
		double currentAngle = Math.toRadians(getAngle());
		if(this.thrust <= 0) {
			return;
		}
		else if(this.thrust*this.deaccel < this.minthrust) {
			this.thrust = 0;
		}
		else {
			this.thrust *= this.deaccel;
		}
		
		this.dx = (this.thrust)*Math.cos(currentAngle);		
		this.dy = (this.thrust)*Math.sin(currentAngle);
	}
	
	/**
	 * 
	 * @param rotatingLeft
	 */
	public void setRotatingLeft(boolean rotatingLeft) {
		this.rotatingLeft = rotatingLeft;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isRotatingLeft() {
		return rotatingLeft;
	}
	
	/**
	 * 
	 * @param rotatingRight
	 */
	public void setRotatingRight(boolean rotatingRight) {
		this.rotatingRight = rotatingRight;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isRotatingRight() {
		return rotatingRight;
	}
	
	/**
	 * 
	 * @param boostingForward
	 */
	public void setBoostingForward(boolean boostingForward) {
		this.boostingForward = boostingForward;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isBoostingForward() {
		return boostingForward;
	}

	public double getThrust() {
		return this.thrust;
	}
}
