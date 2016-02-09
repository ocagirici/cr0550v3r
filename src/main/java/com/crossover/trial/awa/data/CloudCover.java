package com.crossover.trial.awa.data;

import com.crossover.trial.awa.exception.ValueOutOfBoundsException;

public class CloudCover extends DataPoint{
	
	protected CloudCover(int lower, int median, int upper, double mean, int count) throws ValueOutOfBoundsException {
		super(lower, median, upper, mean, count);
		this.type = "CloudCover";
		// TODO Auto-generated constructor stub
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
