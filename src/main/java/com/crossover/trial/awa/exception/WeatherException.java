package com.crossover.trial.awa.exception;

/**
 * An internal exception marker
 */
public class WeatherException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2386468356923911311L; 
	
	public WeatherException() {}
	
	public WeatherException(String message) {
		super(message);
	}
	
	
	// CR: Should be either implemented, or should be made abstract for further inheritance.
}
