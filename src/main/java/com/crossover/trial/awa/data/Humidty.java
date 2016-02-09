package com.crossover.trial.awa.data;

import com.crossover.trial.awa.exception.ValueOutOfBoundsException;

public class Humidty extends DataPoint{
	
	Humidty(int lower, int median, int upper, double mean, int count) throws ValueOutOfBoundsException {
		super(lower, median, upper, mean, count);
		this.type = "Humidity";

		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setMean(double mean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setLower(int lower) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setMedian(int median) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setUpper(int upper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setCount(int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int minValue() {
		return 0;
	}

	@Override
	protected int maxValue() {
		return 100;
	}


}
