package gui;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;


/**
 * Handles timing of ticks, 60 ticks per second
 * 
 * @author kai
 *
 */
public class MovementTimer extends AnimationTimer {
	private long TPS = 60L;
	private long INTERVAL = (1000000000L/TPS);
	
	private int ticks = 0;
	
	private long last = 0L;
	
	private SocialSimController cont;
	
	private Label tickLabel;
	
	public MovementTimer(SocialSimController cont, Label tickLabel) {
		this.cont = cont;
		this.tickLabel = tickLabel;
	}
	
	
	/**
	 * Step simulation 60 times per second
	 */
	@Override
	public void handle(long now) {
		if(now - last > INTERVAL) {
			cont.step();
			last = now;
		}
	}
	/**
	 * Increment ticks
	 */
	public void tick() {
		ticks++;
	}
	/**
	 * Return ticks
	 * @return number of ticks
	 */
	public int getTicks() {
		return ticks;
	}
	/**
	 * Reset ticks to 0 because new simulation started
	 */
	public void resetTicks() {
		ticks = 0;
		tickLabel.setText("Ticks: " + ticks);
	}
}