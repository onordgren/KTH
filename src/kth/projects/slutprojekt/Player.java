package kth.projects.slutprojekt;

import java.awt.event.KeyEvent;

public class Player extends KeyControls {
	private String name;
	private Ship ship;
	private Sounds sound = new Sounds();
	
	public Player(String name, Ship ship) {
		super();
		this.setName(name);
		this.ship = ship;
	}
	
	public void changeKeyControls(int left, int right, int forward, int fire) {
		this.setRotateLeftButton(left);
		this.setRotateRightButton(right);
		this.setBoostForwardButton(forward);
		this.setFireMainGunButton(fire);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_SPACE) {
        	ship.fire();
        	sound.shootSound();
        	
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
	
	
}
