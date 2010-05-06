package kth.projects.slutprojekt;


public class Missile extends Sprite {
	private static String missile = "missile.png";
	private int playerID;
	Ship currentShip;
	
	public Missile(int playerID, double x, double y, int angle, double thrust) {
		super(missile, x, y, true);
		this.playerID  = playerID;
		this.angle     = angle;
		this.thrust    = thrust;
        this.accel     = 1.05;
        this.maxthrust = 4.5;
        this.minthrust = 0.05;
	}
	
	public void move() {
		double currentAngle = Math.toRadians(this.angle);
		
		if(this.thrust <= 0.0) {
			this.thrust = 0.1;
		}
		else if(this.thrust*this.accel > this.maxthrust) {
			this.thrust = this.maxthrust;
		}
		else {
			this.thrust *= this.accel;
		}	
		
		this.x += (this.thrust)*Math.cos(currentAngle);
		this.y += (this.thrust)*Math.sin(currentAngle);
	}
	
	public int getPlayerID() {
		return this.playerID;
	}

}
