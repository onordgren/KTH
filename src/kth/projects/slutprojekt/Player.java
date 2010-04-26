package kth.projects.slutprojekt;

import java.awt.event.KeyEvent;

import kth.projects.slutprojekt.Network.*;

public class Player extends Ship {
	private Sounds sound = new Sounds();
	private String name;
	protected int id;
	
	public Player(double x, double y, String name) {
		super(x, y);
		this.name = name;
	}

	public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_SPACE) {
        	this.fire();
//        	sound.shootSound();
        	NewMissile missile = new NewMissile();
        	missile.angle = this.getAngle();
        	missile.x = this.getX();
        	missile.y = this.getY();
        	missile.thrust = this.getThrust();
        	GameClient.sharedInstance().getClient().sendTCP(missile);
        }

        if (key == KeyEvent.VK_LEFT) {
        	this.setRotatingLeft(true);
        }

        if (key == KeyEvent.VK_RIGHT) {
            this.setRotatingRight(true);
        }

        if (key == KeyEvent.VK_UP) {
        	this.setBoostingForward(true);
        }
    }

	public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            this.setRotatingLeft(false);
        }

        if (key == KeyEvent.VK_RIGHT) {
            this.setRotatingRight(false);
        }

        if (key == KeyEvent.VK_UP) {
        	this.setBoostingForward(false);
        }
	}
	
	public void move() {
    	if(this.isBoostingForward()) {
    		updatePosition();
    		this.moveForward();
    	}
    	else if(this.thrust < minthrust) {
    		//Do nothing;
    	}
    	else {
    		updatePosition();
    		this.deaccelerate();
    	}
    	if(this.isRotatingLeft()) {
    		System.out.println("rorating left");
    		updatePosition();
    		this.rotateLeft();
    	}
    	else if(this.isRotatingRight()) {
    		updatePosition();
    		this.rotateRight();
    	}
    	else if(!this.isRotatingLeft() && !this.isRotatingRight()) {
    		this.stopRotate();
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
    }
	
	public void updatePosition() {
		UpdatePosition position = new UpdatePosition();
    	position.x = this.getX();
    	position.y = this.getY();
    	position.angle = this.getAngle();
    	position.id = this.getID();
    	GameClient.sharedInstance().getClient().sendTCP(position);
	}
	
	public void setID(int ID) {
		this.id = ID;
	}
	
	public int getID() {
		return this.id;
	}
	
	
}
