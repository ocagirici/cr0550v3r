package com.crossover.trial.awa.data;

import com.crossover.trial.awa.exception.ValueOutOfBoundsException;

public class Temperature extends DataPoint{


	protected Temperature(int lower, int median, int upper, double mean, int count) throws ValueOutOfBoundsException {
		super(lower, median, upper, mean, count);
		this.type = "Temperature";
		
	}

	


	@Override
	protected int minValue() {
		// TODO Auto-generated method stub
		return -50;
	}

	@Override
	protected int maxValue() {
		// TODO Auto-generated method stub
		return 100;
	}


}
