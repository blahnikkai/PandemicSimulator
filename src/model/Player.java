package model;

import java.util.List;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Handles a user-controlled player that acts like a person
 * 
 * @author kai
 *
 */
public class Player extends Person {
	
	private FlashTimer _flash;
	private RotateTransition _rt;
	
	/**
	 * Constructs a  player
	 *  
	 * @param state		starting state enumerator of a player
	 * @param world		JavaFX pane that contains circle
	 * @param infection chance of infection within each tick
	 * @param death		chance of death if infected each tick
	 */
	public Player(State state, Pane world, double infection, double death) {
		super(state, world, infection, death);
		_flash = new FlashTimer(this);
		stop();
		
		_rt = new RotateTransition(Duration.millis(2000), _infectionC);
	    _rt.setToAngle(360);
	    _rt.setInterpolator(Interpolator.LINEAR);
	    _rt.setCycleCount(Timeline.INDEFINITE);
	    _rt.play();
	}
	
	
	/**
	 * Changes player's current heading to degree parameter
	 * 
	 * @param deg angle in degrees
	 */
	public void move(double deg) {
		super.getHeading().convertDegrees(deg);
	}
	/**
	 * Stop player movement
	 */
	public void stop() {
		super.setHeading(0, 0);
	}
	
	/**
	 * Set player's visibility
	 * 
	 * @param b visible if true, invisible if false
	 */
	public void vis(boolean b) {
		super._c.setVisible(b);
		super._infectionC.setVisible(b);
	}
	/**
	 * Set flashing status
	 * 
	 * @param b flashing if true, solid if false
	 */
	public void setFlash(boolean b) {
		if(b) {
			_flash.start();
			_rt.stop();
		}
		else {
			_flash.stop();
			_rt.play();
			vis(true);
		}
	}
	
	/**
	 * Random roll to kill player if infected
	 * 
	 */
	@Override
	public void feelWorse(List<Person> list) {
		if(_state == State.INFECTED) {
			if(Simulation.randomCheck(_popAdjustedDeathChance)) {
				setState(State.DEAD);
				currInfectedCnt--;
				list.remove(this);
				_rt.stop();
			}
		}	
	}
	
	/**
	 * Timer to control player flashing
	 * 
	 * @author kai
	 *
	 */
	private class FlashTimer extends AnimationTimer {

		private int ticks;
		
		private Player _player;
		
		/**
		 * Construct timer
		 * 
		 * @param player Player object to be flashed
		 */
		public FlashTimer(Player player) {
			_player = player;
		}
		
		/**
		 * Every tick, check if a player should be flashed and flash if necessary
		 * 
		 */
		@Override
		public void handle(long arg0) {
			ticks++;
			if(ticks % 40 > 20) {
				_player.vis(true);
			}
			else {
				_player.vis(false);
			}
		}
		
	}
	

}
