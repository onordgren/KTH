package kth.projects.slutprojekt;

import java.awt.event.KeyEvent;

public class Player {
	private Ship ship;
	private Profile profile;
	
	public Player() {
		this.ship = new Ship();
	}

	public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_SPACE) {
        	ship.fire();
        }

        if (key == KeyEvent.VK_LEFT) {
        	ship.setRotatingLeft(true);
        }

        if (key == KeyEvent.VK_RIGHT) {
            ship.setRotatingRight(true);
        }

        if (key == KeyEvent.VK_UP) {
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
	
	
}
