package kth.projects.slutprojekt;

import java.awt.event.KeyEvent;

import kth.projects.slutprojekt.Network.*;

public class Player {
	private Ship ship;
	private Sounds sound = new Sounds();
	protected int id;
	
	public Player() {
		this.ship = new Ship();
	}

	public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_SPACE) {
        	ship.fire();
        	sound.shootSound();
        	NewMissile missile = new NewMissile();
        	missile.angle = ship.getAngle();
        	missile.x = ship.getX();
        	missile.y = ship.getY();
        	missile.thrust = ship.getThrust();
        	GameClient.sharedInstance().getClient().sendTCP(missile);
        }

        if (key == KeyEvent.VK_LEFT) {
        	ship.setRotatingLeft(true);
        }

        if (key == KeyEvent.VK_RIGHT) {
            ship.setRotatingRight(true);
        }

        if (key == KeyEvent.VK_UP) {
        	UpdatePosition position = new UpdatePosition();
        	position.x = ship.getX();
        	position.y = ship.getY();
        	position.angle = ship.getAngle();
        	position.id = this.getID();
        	GameClient.sharedInstance().getClient().sendTCP(position);
        	ship.setBoostingForward(true);
        }
    }

	public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            ship.setRotatingLeft(false);
        }

        if (key == KeyEvent.VK_RIGHT) {
            ship.setRotatingRight(false);
        }

        if (key == KeyEvent.VK_UP) {
        	ship.setBoostingForward(false);
        }
	}
	
	public Ship getShip() {
		return this.ship;
	}
	
	public void setID(int ID) {
		this.id = ID;
	}
	
	public int getID() {
		return this.id;
	}
	
	
}
