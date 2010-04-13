package kth.projects.slutprojekt;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Missile {
	private String missile = "missile.png";
	private double x, y, dx, dy, angle;
	private Image image;
	private boolean visible;
	private int width, height;
	private final double MISSILE_SPEED = 3.5,
						 BOARD_WIDTH   = 800,
						 BOARD_HEIGHT  = 600;
	
	
	public Missile(double x, double y, double angle) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(missile));
		this.image   = ii.getImage();
		this.visible = true;
		this.width   = image.getWidth(null);
		this.height  = image.getHeight(null);
		this.x		 = x;
		this.y		 = y;
		this.angle   = angle;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	/*public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}*/
	public void moveForward() {
		double currentAngle = Math.toRadians(this.angle);
		dx = (this.MISSILE_SPEED)*Math.cos(currentAngle);
		dy = (this.MISSILE_SPEED)*Math.sin(currentAngle);
	}
	public void move() {
		double currentAngle = Math.toRadians(this.angle);
		x += (this.MISSILE_SPEED)*Math.cos(currentAngle);
		y += (this.MISSILE_SPEED)*Math.sin(currentAngle);
	}

	public Image getImage() {
		return this.image;
	}

	public double getAngle() {
		return this.angle;
	}
}
