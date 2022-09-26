package model;

/**
 * Handles direction of movement
 * 
 * @author kai
 *
 */
public class Heading {

	public static double SPEED;
	
	private double _dx;
	public double getDx() {return _dx * SPEED;}
	public void setDx(double dx) {_dx = dx;}
	
	private double _dy;
	public double getDy() {return _dy * SPEED;}
	public void setDy(double dy) {_dy = dy;}
	
	/**
	 * Constructs a heading object with preset direction
	 * 
	 * @param dx x-direction of movement, ranging from -1 to 1
	 * @param dy y-direction of movement, ranging from -1 to 1
	 */
	public Heading(double dx, double dy) {
		_dx = dx;
		_dy = dy;
	}
	
	/**
	 * Constructs a heading object with random direction, 
	 * ranging from -1 to 1
	 */
	public Heading() {
		double dir = Math.random() * 2 * Math.PI;
		_dx = Math.sin(dir);
		_dy = Math.cos(dir);
	}
	
	/**
	 * Reflects the x-direction of the heading
	 */
	public void bounceX() {
		_dx *= -1;
	}
	
	/**
	 * Reflects the y-direction of the heading
	 */
	public void bounceY() {
		_dy *= -1;
	}
	
	/**
	 * Converts a given angle into radians and sets direction to that heading
	 * 
	 * @param deg angle in degrees
	 */
	public void convertDegrees(double deg) {
		_dx = 1.1 * Math.cos(deg * Math.PI / 180.0);
		_dy = 1.1 * -Math.sin(deg * Math.PI / 180.0);
	}
	
	
 }
