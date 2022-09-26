package model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

/**
 * Handles a person
 * Ability to get infected and spread infection
 * Ability to die
 * 
 * @author kai
 */
public class Person {

	private static double radius = 6;
	/**
	 * get person's visual radius
	 * 
	 * @return visual radius as a double
	 */
	public static double getRadius() {return radius;}
	/**
	 * change person's visual radius
	 * 
	 * @param rad new visual radius
	 */
	public static void setRadius(double rad) {
		radius = rad;
		infectionRadius = (rad * multiplier);
	}
	
	private static double multiplier = 3;
	/**
	 * get Person's visual to infection ratio
	 * 
	 * @return Person's visual to infection ratio as a double
	 */
	public static double getMultiplier() {return multiplier;}
	/**
	 * Set Person's visual to infection ratio
	 * 
	 * @param mult new ratio
	 */
	public static void setMultiplier(double mult) {
		multiplier = mult;
		infectionRadius = (radius * multiplier);
	}
	
	private static double infectionRadius = 18;
	/**
	 * Get Person's infection radius
	 * 
	 * @return infection radius as a double
	 */
	public static double getInfectionRadius() {return infectionRadius;}
	/**
	 * Change Person's infection radius
	 * 
	 * @param rad new infection radius as a double
	 */
	public static void setInfectionRadius(double rad) {
		infectionRadius = rad;
		multiplier = (double)infectionRadius/radius;
		//System.out.println("multiplier " + multiplier);
	}
	
	
	
	public static ArrayList<Rectangle> _walls = new ArrayList<Rectangle>();
	public static ArrayList<Rectangle> getWalls() {return _walls;}
	
	public static int currInfectedCnt = 0;
	
	public static double vaccineSpeed;
	public static double vaccineEffectiveness;
	
	public static int healTime = 5 * 50;
	public static int universalDistance;
	public static int infectedDistance;
	
	public static double maskPercent;
	public static double maskFactor;
	
	public static double vaccineFactor;
	
	protected State _state;
	//get person's infection state
	public State getState() {return _state;}
	//change person's infection state
	public void setState(State s) {
		_state = s;
		_c.setFill(_state.getColor());
	}
	
	private Position _loc;
	//get person's current location as a Position object
	public Position getLoc() {return _loc;}
	
	private Position _origin;
	
	private Heading _heading;
	//get person's current heading as a Heading object
	public Heading getHeading() {return _heading;}
	//change person's heading
	public void setHeading(double x, double y) {
		_heading.setDx(x);
		_heading.setDy(y);
	}
	
	private boolean _mask;
	
	private boolean _vaccinated;
	//return current vaccination status of a person
	public boolean getVaccinated() {return _vaccinated;}
	
	/**
	 * Change a persons vaccination state
	 * 
	 * @param b			true if vaccinated, false if unvaccinated
	 * @param infection infection chance as a decimal
	 * @param death		death chance as a decimal
	 */
	public void setVaccinated(boolean b, double infection, double death) {
		_vaccinated = b;
		_c.setStrokeWidth(3);
		_infectionChance = infection;
		_deathChance = death;
		_infectionChance *= vaccineFactor;
		_deathChance *= vaccineFactor;
	}
	
	private double _infectionChance;
	private double _deathChance;
	protected double _popAdjustedDeathChance;
	private double _infectionRadius;
	private int _distance;
	
	protected Circle _c;
	
	protected Circle _infectionC;
	
	private Text _debugInfo;
	
	private Pane _world;
	
	private int _sickTime = 0;
	
	/**
	 * Construct a person object
	 * 
	 * @param state		Person's infection status
	 * @param world		JavaFX pane the person is added to
	 * @param infection infection chance as a decimal
	 * @param death		death chance as a decimal
	 */
	public Person(State state, Pane world, double infection, double death) {
		
		_state = state;
		_world = world;
		_heading = new Heading();
		_loc = new Position(_world, _walls);
		_origin = new Position(_loc.getX(), _loc.getY());
		
		_mask = Simulation.randomCheck(maskPercent);
		_distance = universalDistance;
		
		_debugInfo = new Text();
		_debugInfo.setVisible(false);
		
		_c = new Circle(radius, _state.getColor());
		_c.setStrokeType(StrokeType.INSIDE);
		
		if(_mask) {
			_infectionRadius = maskFactor * infectionRadius;
			_infectionChance = maskFactor * infection;
			_c.setStroke(Color.BLUE);
			_c.setStrokeWidth(1.5);
		}
		else {
			_infectionRadius = infectionRadius;
			_infectionChance = infection;
			_c.setStroke(Color.BLACK);
		}
		_deathChance = death;
		//System.out.println("mask : " + _mask + " infection radius: " + _infectionRadius);
		
		_infectionC = new Circle(_infectionRadius, Color.TRANSPARENT);
		_infectionC.setStroke(Color.BLACK);
		_infectionC.setStrokeWidth(.5);
		_infectionC.getStrokeDashArray().addAll(4d, 6d);
		
		
		
		_world.getChildren().addAll(_c, _infectionC, _debugInfo);
		
		_infectionC.setOnMouseEntered(e -> {
			_debugInfo.setVisible(true);
		});
		_c.setOnMouseEntered(e -> {
			_debugInfo.setVisible(true);
		});
		_infectionC.setOnMouseExited(e -> {
			_debugInfo.setVisible(false);
		});
		_c.setOnMouseExited(e -> {
			_debugInfo.setVisible(false);
		});
		
	}
	
	/**
	 * Move a person in the direction of their current heading
	 * 
	 */
	public void move() {
		if(_state != State.DEAD) {
			if(_state == State.INFECTED) {
				_distance = infectedDistance;
			}
			if(_state != State.INFECTED) {
				_distance = universalDistance;
			}
			_loc.move(_heading, _world, _origin, _walls, _distance, this.getClass() == Player.class);
		}
	}
	
	/**
	 * Update graphical representation of person
	 * 
	 */
	public void draw() {
		_c.setRadius(radius);
		_c.setTranslateX(_loc.getX());
		_c.setTranslateY(_loc.getY());
		_c.setFill(_state.getColor());
		
		_infectionC.setRadius(_infectionRadius);
		_infectionC.setTranslateX(_loc.getX());
		_infectionC.setTranslateY(_loc.getY());
		
		_debugInfo.setTranslateX(_loc.getX());
		_debugInfo.setTranslateY(_loc.getY());
		updateDebugInfo();
	}
	
	
	/**
	 * update a person's status
	 * 
	 * @param infection	infection chance as a decimal
	 * @param death		death chance as a decimal
	 * @param popCount	total population number
	 */
	public void update(double infection, double death, int popCount) {
		_infectionChance = infection;
		_deathChance = death;
		if(_mask) {
			_infectionChance *= maskFactor;
		}
		if(_vaccinated) {
			_infectionChance *= vaccineFactor;
			_deathChance *= vaccineFactor;
		}
		_distance = universalDistance;
		if(_state == State.INFECTED) {
			_distance = infectedDistance;
		}
		_popAdjustedDeathChance = _deathChance * ((double)currInfectedCnt * 5.0 / popCount) + _deathChance;
		updateRadii();
		updateDebugInfo();
	}
	/**
	 * Update Person's infection radius
	 * 
	 */
	private void updateRadii() {
		if(_mask) {
			_infectionRadius = maskFactor * infectionRadius;
		}
		else {
			_infectionRadius = infectionRadius;
		}
	}
	/**
	 * Update the debug info displayed when mousing over Person
	 */
	private void updateDebugInfo() {
		_debugInfo.setText(
				"State: " + _state +
				"\nPos: " + _loc.getX() + ", " + _loc.getY() +
				"\nDir: " + _heading.getDx() + ", " + _heading.getDy() +
				"\nInfection Chance: " + _infectionChance + 
				"\nDeath Chance: " + _deathChance +
				"\nPopulation Adjusted Death Chance" + _popAdjustedDeathChance +
				"\nQuarantine Distance: " + _distance +
				"\nInfection Radius: " + _infectionRadius);
		_debugInfo.toFront();
	}
	/**
	 * Check if person collides with another
	 * 
	 * @param other another Person object to check
	 * @param list list of susceptible people, if person infected, remove them
	 */
	public void collisionCheck(Person other, List<Person> list) {
		if(_loc.collison(other._loc, _infectionRadius)) {
			if(other.getState() == State.INFECTED && _state == State.SUSCEPTIBLE) {
				if(Simulation.randomCheck(other._infectionChance)) {
					list.remove(this);
					setState(State.INFECTED);
					currInfectedCnt++;
					resetOrigin();
				}
			}
		}
	}
	/**
	 * If person has been infected long enough, change state to recovered
	 * 
	 * @param list list of unvaccinated susceptible people
	 */
	public void feelBetter(List<Person> list) {
		if(_state == State.INFECTED) {
			_sickTime++;
			if(_sickTime > healTime) {
				setState(State.RECOVERED);
				currInfectedCnt--;
				resetOrigin();
			}
		}	
	}
	
	/**
	 * Randomly apply death chance to infected person, die if random chance
	 * 
	 * @param list ArrayList of unvaccinated susceptible people
	 */
	public void feelWorse(List<Person> list) {
		if(_state == State.INFECTED) {
			if(Simulation.randomCheck(_popAdjustedDeathChance)) {
				setState(State.DEAD);
				currInfectedCnt--;
				list.remove(this);
			}
		}	
	}
	
	/**
	 * Set Person's origin to current position
	 * 
	 */
	public void resetOrigin() {
		_origin = new Position(_loc.getX(), _loc.getY());
	}
	
	
}
