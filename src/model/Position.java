package model;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * Handles coordinate position of a player
 * 
 * @author kai
 *
 */
public class Position {

	private double _x;
	/**
	 * get x coordinate
	 * @return x coordinate as a double
	 */
	public double getX() {return _x;}
	/**
	 * Change x coordinate
	 * @param x new x coordinate
	 */
	public void setX(double x) {_x = x;}
	
	private double _y;
	/**
	 * get y coordinate
	 * @return y coordinate as a double
	 */
	public double getY() {return _y;}
	/**
	 * Change y coordinate
	 * @param y new y coordinate
	 */
	public void setY(double y) {_y = y;}
	
	/**
	 * Construct new Position object given an x and y
	 * @param x starting x-coordinate
	 * @param y starting y-coordinate
	 */
	public Position(double x, double y) {
		_x = x;
		_y = y;
	}
	
	/**
	 * Construct a new random Position object given a world and walls
	 * Constructed Position must not intersect the world or walls
	 * 
	 * @param world containing JavaFX pane
	 * @param walls list of rectangles to be treated as solids
	 */
	public Position(Pane world, ArrayList<Rectangle> walls) {
		
		
		boolean incorrect = true;
		
		double x = 0;
		double y = 0;
		
		while(incorrect) {
			x = Person.getRadius() + Math.random() * (world.getWidth() - 2*Person.getRadius());
			y = Person.getRadius() + Math.random() * (world.getHeight() - 2*Person.getRadius());
			
			incorrect = false;
			
			for(Rectangle r: walls) {
				System.out.println(r.getX() + " " + r.getY() + " " + r.getHeight() + "  " + r.getWidth());
				if(	
					y + Person.getRadius() > r.getY() &&
					y - Person.getRadius() < r.getY() + r.getHeight() && 
					x + Person.getRadius() > r.getX() && 
					x - Person.getRadius() < r.getX() + r.getWidth()) {
					
					incorrect = true;
				}
				if(	
					x + Person.getRadius() > r.getX() &&
					x - Person.getRadius() < r.getX() + r.getWidth() &&
					y + Person.getRadius() > r.getY() && 
					y - Person.getRadius() < r.getY() + r.getHeight()) {
					
					incorrect = true;
				}
			}
		}
		
		_x = x;
		_y = y;
		
		
		
	}
	
	/**
	 * Calculate distance between two Position objects
	 * 
	 * @param other another Position object to be measured
	 * @return distance as a double
	 */
	public double distance(Position other) {
		return Math.sqrt(
				Math.pow(_x - other.getX(), 2) + 
				Math.pow(_y - other.getY(), 2));
	}
	
	/**
	 * Change position based on heading and restrictions
	 * 
	 * @param h			Current heading, move in this direction
	 * @param world		JavaFX pane containing Person object, don't move outside borders
	 * @param origin 	Original position of the person to be moved
	 * @param walls		List of walls, don't move into them
	 * @param dist		Maximum distance Person allowed to move from original position
	 * @param player	True is user-controlled player, false otherwise. If player, don't restrict to circle around origin
	 */
	public void move(Heading h, Pane world, Position origin, ArrayList<Rectangle> walls, int dist, boolean player) {
		_x += h.getDx();
		_y += h.getDy();
		
		
		if(_x < Person.getRadius() || _x > world.getWidth() - Person.getRadius() || (distance(origin) > dist && !player)) {
			h.bounceX();
			_x += h.getDx();
		}
		if(_y < Person.getRadius() || _y > world.getHeight() - Person.getRadius() || (distance(origin) > dist && !player)) {
			h.bounceY();
			_y += h.getDy();
		}
		for(Rectangle r: walls) {
			
			double rectRight = r.getX() + r.getWidth();
			double rectLeft = r.getX();
			double rectTop = r.getY();
			double rectBot = r.getY() + r.getHeight();
			
			double circRight = _x + Person.getRadius();
			double circLeft = _x - Person.getRadius();
			double circTop = _y - Person.getRadius();
			double circBot = _y + Person.getRadius();
			
			double circCenterX = _x;
			double circCenterY = _y;
			
			
			
			if(
				circBot > rectTop &&
				circTop < rectBot && 
				(circCenterY > rectBot ||
				circCenterY < rectTop) &&
				circLeft < rectRight &&
				circRight > rectLeft
					) {
				//System.out.println("reflected y");
				h.bounceY();
				_y += h.getDy();
			}
			
			if(
					circRight > rectLeft &&
					circLeft < rectRight &&
					(circCenterX < rectLeft ||
					circCenterX > rectRight)  &&
					circTop < rectBot &&
					circBot > rectTop
					) {
				//System.out.println("reflected x");
				h.bounceX();
				_x += h.getDx();
			}
			
			
		}
	}
	
	/**
	 * Check if this position is within other position's infection radius to determine if disease can be spread
	 * 
	 * @param other Another Position object to be checked
	 * @param rad	radius as a double
	 * @return whether Person objects are colliding
	 */
	public boolean collison(Position other, double rad) {
		return distance(other) < rad;
	}
	
}
