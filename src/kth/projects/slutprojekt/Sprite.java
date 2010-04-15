package kth.projects.slutprojekt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

public class Sprite {
	private Image image;
	public double x, y;
	public int width, height, angle;
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
	
	public void draw(Graphics2D g2d) {
        AffineTransform origXform = g2d.getTransform();
        AffineTransform newXform = (AffineTransform)(origXform.clone());
        //center of rotation is center of the sprite
        double xRot = this.getX() + this.getImage().getWidth(null) / 2;
        double yRot = this.getY() + this.getImage().getHeight(null) / 2;
        newXform.rotate(Math.toRadians(this.getAngle()), xRot, yRot);
        g2d.setTransform(newXform);
        //draw image on the current location
        int x = (int)this.getX();
        int y = (int)this.getY();
        g2d.drawImage(this.getImage(), x, y, null);             	
        g2d.setColor(Color.RED);
        g2d.draw3DRect(x, y, this.getWidth(), this.getHeight(), true);
        g2d.setTransform(origXform); 
	}
	
	public int getAngle() {
		return this.angle;
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