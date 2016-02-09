package com.crossover.trial.awa.data;

import com.crossover.trial.awa.exception.ValueOutOfBoundsException;

public class Pressure extends DataPoint{

	protected Pressure(int lower, int median, int upper, double mean, int count) throws ValueOutOfBoundsException {
		super(lower, median, upper, mean, count);
		this.type = "Pressure";
		// TODO Auto-generated constructor stub
	}


	@Override
	protected int minValue() {
		// TODO Auto-generated method stub
		return 650;
	}

	@Override
	protected int maxValue() {
		// TODO Auto-generated method stub
		return 800;
	}



}
