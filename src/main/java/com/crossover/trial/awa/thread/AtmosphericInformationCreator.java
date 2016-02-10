package com.crossover.trial.awa.thread;

import com.crossover.trial.awa.airport.Airport;
import com.crossover.trial.awa.data.*;
import com.crossover.trial.awa.exception.ValueOutOfBoundsException;
public class AtmosphericInformationCreator extends Thread implements Runnable {

	private Airport airport;
	private AtmosphericInformation info;
	
	public AtmosphericInformationCreator(Airport airport) throws ValueOutOfBoundsException {
		this.airport = airport;
		DataPoint[] data = DataPointBuilder.buildEmptyDataPointSet();
		this.info = new AtmosphericInformation((Temperature) data[0], (Wind) data[1], 
				(Humidty) data[2], (Precipitation) data[3], (Pressure) data[4], (CloudCover) data[5]);
	}

	public AtmosphericInformationCreator(Airport airport, Temperature temperature, Wind wind, Humidty humidty, Precipitation precipitation, Pressure pressure, CloudCover cloudCover) {
		this.info = new AtmosphericInformation(temperature, wind, humidty, precipitation, pressure, cloudCover);
	}
	
	

	@Override
	public void run() {
		synchronized (airport) {
			airport.setAtmosphericInformation(info);
			info.setLastUpdateTime(System.currentTimeMillis());
		}
	}
	
	

}
