package kth.projects.slutprojekt;

import java.awt.event.KeyEvent;

public class KeyControls {
	private boolean rotatingLeft        = false,
					rotatingRight       = false,
					boostingForward     = false,
					firingMainGun       = false;
	
	private int     rotateLeftButton,
				    rotateRightButton,
				    boostForwardButton,
				    fireMainGunButton;
	
	public KeyControls(KeyEvent left, KeyEvent right, KeyEvent forward, KeyEvent fire) {
		this.rotateLeftButton   = left.getKeyCode();
		this.rotateRightButton  = right.getKeyCode();
		this.boostForwardButton = forward.getKeyCode();
		this.fireMainGunButton  = fire.getKeyCode();
	}

	/**
	 * @param rotatingLeft the rotatingLeft to set
	 */
	public void setRotatingLeft(boolean rotatingLeft) {
		this.rotatingLeft = rotatingLeft;
	}

	/**
	 * @return the rotatingLeft
	 */
	public boolean isRotatingLeft() {
		return rotatingLeft;
	}

	/**
	 * @param rotatingRight the rotatingRight to set
	 */
	public void setRotatingRight(boolean rotatingRight) {
		this.rotatingRight = rotatingRight;
	}

	/**
	 * @return the rotatingRight
	 */
	public boolean isRotatingRight() {
		return rotatingRight;
	}

	/**
	 * @param boostingForward the boostingForward to set
	 */
	public void setBoostingForward(boolean boostingForward) {
		this.boostingForward = boostingForward;
	}

	/**
	 * @return the boostingForward
	 */
	public boolean isBoostingForward() {
		return boostingForward;
	}

	/**
	 * @param firingMainGun the firingMainGun to set
	 */
	public void setFiringMainGun(boolean firingMainGun) {
		this.firingMainGun = firingMainGun;
	}

	/**
	 * @return the firingMainGun
	 */
	public boolean isFiringMainGun() {
		return firingMainGun;
	}

	/**
	 * @param rotateLeftButton the rotateLeftButton to set
	 */
	public void setRotateLeftButton(int rotateLeftButton) {
		this.rotateLeftButton = rotateLeftButton;
	}

	/**
	 * @return the rotateLeftButton
	 */
	public int getRotateLeftButton() {
		return rotateLeftButton;
	}

	/**
	 * @param rotateRightButton the rotateRightButton to set
	 */
	public void setRotateRightButton(int rotateRightButton) {
		this.rotateRightButton = rotateRightButton;
	}

	/**
	 * @return the rotateRightButton
	 */
	public int getRotateRightButton() {
		return rotateRightButton;
	}

	/**
	 * @param boostForwardButton the boostForwardButton to set
	 */
	public void setBoostForwardButton(int boostForwardButton) {
		this.boostForwardButton = boostForwardButton;
	}

	/**
	 * @return the boostForwardButton
	 */
	public int getBoostForwardButton() {
		return boostForwardButton;
	}

	/**
	 * @param fireMainGun the fireMainGun to set
	 */
	public void setFireMainGunButton(int fireMainGun) {
		this.fireMainGunButton = fireMainGun;
	}

	/**
	 * @return the fireMainGun
	 */
	public int getFireMainGunButton() {
		return fireMainGunButton;
	}
}
