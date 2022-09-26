package model;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Large-scale container of all people in simulation
 * 
 * @author kai
 *
 */
public class Simulation {
	
	/**
	 * Random dice roll given cha
	 * 
	 * @param chance random chance as a decimal
	 * @return true if random dice roll comes up true, false otherwise
	 */
	public static boolean randomCheck(double chance) {
		return Math.random() < chance;
	}

	private int totalPopCount;
	/**
	 * Get total population of the simulation
	 * 
	 * @return total population
	 */
	public int getTotalPopCount() {return totalPopCount;}
	/**
	 * Change total population count
	 * 
	 * @param d new total population
	 */
	public void setTotalPopCount(int d) {totalPopCount = d;}
	
	private int infectedPopCount;
	/**
	 * Get population of infected people
	 * 
	 * @return infected population 
	 */
	public int getInfectedPopCount() {return infectedPopCount;}
	/**
	 * Change infected population
	 * 
	 * @param d new infected population
	 */
	public void setInfectedPopCount(int d) {infectedPopCount = d;}
	
	private double vaccinationChance;
	/**
	 * Get vaccination chance
	 * 
	 * @return vaccination chance as a decimal
	 */
	public double getVaccinationChance() {return vaccinationChance;}
	/**
	 * Change vaccination chance
	 * 
	 * @param d new vaccination chance
	 */
	public void setVaccinationChance(double d) {vaccinationChance = d;}
	
	private double infectionChance;
	/**
	 * Get infection chance
	 * 
	 * @return infection chance as a decimal
	 */
	public double getInfectionChance() {return infectionChance;}
	/**
	 * Change infection chance
	 * 
	 * @param infectionChance new infection chance
	 */
	public void setInfectionChance(double infectionChance) {
		this.infectionChance = infectionChance;
		update();
	}
	
	private double deathChance;
	/**
	 * Get death chance
	 * 
	 * @return death change as a decimal
	 */
	public double getDeathChance() {return deathChance;}
	/**
	 * Change death chance
	 * 
	 * @param deathChance new chance of death
	 */
	public void setDeathChance(double deathChance) {
		this.deathChance = deathChance;
		update();
	}

	private ArrayList<Person> _people;
	/**
	 * Get list of all people
	 * 
	 * @return ArrayList of all Person objects in simulation
	 */
	public ArrayList<Person> getPeople() {return _people;}
	
	private ArrayList<Person> _unvaccinated;
	
	private Pane world;
	
	private Player _player;
	
	/**
	 * Construct a Simulation
	 * 
	 * @param world		JavaFX pane containing everything
	 * @param total		Starting total population
	 * @param infected	Starting infected population
	 */
	public Simulation(Pane world, int total, int infected) {
		this.totalPopCount = total;
		this.infectedPopCount = infected;
		
		_people = new ArrayList<Person>();
		_unvaccinated = new ArrayList<Person>();
		
		this.world = world;
		
		for(int i = 0; i < infected; i++) {
			_people.add(new Person(State.INFECTED, world, infectionChance, deathChance));

		}		
		for(int i = 0; i < total - infected; i++) {
			Person p = new Person(State.SUSCEPTIBLE, world, infectionChance, deathChance);
			_people.add(p);
			_unvaccinated.add(p);
		}
		
		_player = null;
		
		
		draw();
	}
	
	/**
	 * Change simulation to reflect population change
	 * 
	 * @param world		JavaFX pane containing simulation
	 * @param player	Whether user is playing or not
	 */
	public void changePop(Pane world, boolean player) {
		this.world = world;
		
		_people = new ArrayList<Person>();
		_unvaccinated = new ArrayList<Person>();
		
		for(int i = 0; i < infectedPopCount; i++) {
			_people.add(new Person(State.INFECTED, world, infectionChance, deathChance));

		}
		
		for(int i = 0; i < totalPopCount - infectedPopCount; i++) {
			Person p = new Person(State.SUSCEPTIBLE, world, infectionChance, deathChance);
			_people.add(p);
			_unvaccinated.add(p);
		}
		if(player) {
			System.out.println("player added");
			_player = new Player(State.SUSCEPTIBLE, world, infectionChance, deathChance);
			_people.add(_player);
			_unvaccinated.add(_player);
		}
		
		draw();
	}
	
	/**
	 * Move all people in the simulation
	 */
	public void move() {
		for(Person p: _people) {
			p.move();
		}
	}
	
	/**
	 * Draw all people in the simulation
	 */
	public void draw() {
		for(Person p: _people) {
			p.draw();
		}
		
	}
	
	/**
	 * Draw walls as rectangles
	 */
	public void drawWalls() {
		for(Rectangle r: Person._walls) {
			try {
				world.getChildren().add(r);
			}
			catch(IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Check collisions between every possible pair of people
	 */
	public void resolveCollisions() {
		for(Person p: _people) {
			for(Person q: _people) {
				if(p != q) {
					p.collisionCheck(q, _unvaccinated);
				}
			}
		}
	}
	
	/**
	 * Check recovery time for all people
	 */
	public void feelBetter() {
		for(Person p: _people) {
			p.feelBetter(_unvaccinated);
		}
	}
	
	/**
	 * Check death for all people
	 */
	public void feelWorse() {
		for(Person p: _people) {
			p.feelWorse(_unvaccinated);
		}
	}
	
	/**
	 * Perform a full tick of operations on all people
	 */
	public void fullStep() {
		for(Person p: _people) {
			p.move();
			p.update(infectionChance, deathChance, totalPopCount);
		}
		for(Person p: _people) {
			for(Person q: _people) {
				if(p != q) {
					p.collisionCheck(q, _unvaccinated);
				}
			}
			p.feelBetter(_unvaccinated);
			p.feelWorse(_unvaccinated);
			p.draw();
		}
	}
	
	/**
	 * Reset origins of all people to each of their current positions
	 */
	public void resetOrigins() {
		for(Person p: _people) {
			p.resetOrigin();
		}
	}
	
	/**
	 * Update debug info of all people
	 */
	public void update() {
		for(Person p: _people) {
			p.update(infectionChance, deathChance, totalPopCount);
		}
	}

	/**
	 * Attempt a vaccination, only unvaccinated susceptible people are eligible for vaccination
	 */
	public void vaccinate() {
		if(_unvaccinated.size() != 0 && randomCheck(vaccinationChance)) {
			_unvaccinated.remove((int)(Math.random() * _unvaccinated.size())).setVaccinated(true, infectionChance, deathChance);
		}
	}
	
	/**
	 * Set player's heading to new angle
	 * @param deg angle in degrees
	 */
	public void movePlayer(double deg) {
		if(_player != null)
			_player.move(deg);
	}
	/**
	 * Stop player movement
	 */
	public void stopPlayer() {
		if(_player != null)
			_player.stop();
	}
	/**
	 * Set player's flashing status
	 * @param b flashing if true, solid if false
	 */
	public void setPlayerFlash(boolean b) {
		if(_player != null)
			_player.setFlash(b);
	}
	
}
