package com.crossover.trial.awa.data;

import com.crossover.trial.awa.exception.ValueOutOfBoundsException;

public class Wind extends DataPoint{

	
	
	protected Wind(int lower, int median, int upper, double mean, int count) throws ValueOutOfBoundsException {
		super(lower, median, upper, mean, count);
		this.type = "Wind";
	}

	

	@Override
	protected int minValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int maxValue() {
		// TODO Auto-generated method stub
		return Integer.MAX_VALUE;
	}

	



}
