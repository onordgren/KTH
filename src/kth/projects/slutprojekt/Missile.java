package kth.projects.slutprojekt;


public class Missile extends Sprite {
	private static String missile = "missile.png";
	private double angle;
	private final double MISSILE_SPEED = 3.5,
						 BOARD_WIDTH   = 800,
						 BOARD_HEIGHT  = 600;
	
	
	public Missile(double x, double y, double angle) {
		super(missile, x, y, true);
		this.angle = angle;
	}
	
	public void move() {
		double currentAngle = Math.toRadians(this.angle);
		this.x += (this.MISSILE_SPEED)*Math.cos(currentAngle);
		this.y += (this.MISSILE_SPEED)*Math.sin(currentAngle);
	}

	public double getAngle() {
		return this.angle;
	}

}
