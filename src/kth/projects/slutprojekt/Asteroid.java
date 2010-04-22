package kth.projects.slutprojekt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Asteroid extends Sprite {
	private static String asteroid = "asteroid.png";
	
	public Asteroid () {
		super(asteroid, 400, 300, true);
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
}
