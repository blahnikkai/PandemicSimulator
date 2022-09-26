package model;

import javafx.scene.paint.Color;

/**
 * Present status of a person
 * 
 * @author kai
 *
 */
public enum State {

	SUSCEPTIBLE(0, "Susceptible", Color.YELLOW, "yellow"), 
	INFECTED(1, "Infected", Color.RED, "red"), 
	RECOVERED(2, "Recovered", Color.GREEN, "green"),
	DEAD(3, "Dead", Color.GRAY, "gray"),
	;
	
	/**
	 * Construct a state
	 * 
	 * @param ind		Index of the state
	 * @param name		Name of the state
	 * @param color		JavaFX color to represent state
	 * @param strColor	text representing state color
	 */
	private State(int ind, String name, Color color, String strColor) {
		_ind = ind;
		_name = name;
		_color = color;
		_strColor = strColor;
	}
	
	private int _ind;
	/**
	 * Return index of the state
	 * @return index
	 */
	public int getInd() {
		return _ind;
	}
	
	private String _name;
	/**
	 * Return name of the state
	 * 
	 * @return name
	 */
	public String getName() {
		return _name;
	}
	
	private Color _color;
	/**
	 * Return color of the state
	 * 
	 * @return JavaFX Color object
	 */
	public Color getColor() {
		return _color;
	}
	
	private String _strColor;
	/**
	 * Get string color of state
	 * 
	 * @return string representing state's color
	 */
	public String getStrColor() {
		return _strColor;
	}
	
	/**
	 * Get state given index
	 * 
	 * @param ind index of state
	 * @return state with associated index
	 */
	public static State getState(int ind) {
		for(State s: State.values()) {
			if(s.getInd() == ind) {
				return s;
			}
		}
		return null;
	}
	
	
}
