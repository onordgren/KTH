package kth.projects.slutprojekt;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Sprite {
	private Image image;
	public double x, y;
	public int width, height;
	public boolean visible;
	
	public Sprite(String spriteImage, double x, double y, boolean visible) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(spriteImage));
		this.image = ii.getImage();
		this.width = this.image.getWidth(null);
		this.height = this.image.getHeight(null);
		this.x = x;
		this.y = y;
		this.visible = visible;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public Image getImage() {
		return this.image;
	}
	
	public Rectangle getBounds() {
		int currentX = (int) this.getX();
    	int currentY = (int) this.getY();
        return new Rectangle(currentX, currentY, this.width, this.height);
	}

	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}