package com.crossover.trial.awa.exception;

public class IncorrectDataPointName extends WeatherException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7895968380990694706L;

	public IncorrectDataPointName() {
		super("The data point name is incorrect.");
	}
	
	public IncorrectDataPointName(String message) {
		super(message);
	}

}
