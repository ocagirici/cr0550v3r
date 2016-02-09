package com.crossover.trial.awa.thread;

import com.crossover.trial.awa.data.*;
public class AtmosphericInformationCreator extends Thread implements Runnable {

	private AtmosphericInformation info;
	
	public AtmosphericInformationCreator(DataPoint dp) {
		try {
			DataPointBuilder.build(dp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public AtmosphericInformationCreator(Temperature temperature, Wind wind, Humidty humidty, Precipitation precipitation, Pressure pressure, CloudCover cloudCover) {
		this.info = new AtmosphericInformation(temperature, wind, humidty, precipitation, pressure, cloudCover);
	}
	
	

	public AtmosphericInformation create()
	{
		run();
		return info;
	}
	
	
	@Override
	public void run() {
		synchronized (info) {
			info.setLastUpdateTime(System.currentTimeMillis());
		}
	}
	
	

}
