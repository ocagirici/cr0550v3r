package com.crossover.trial.awa.thread;

import com.crossover.trial.awa.data.*;

public class AtmosphericInformationUpdater extends Thread implements Runnable {

	private AtmosphericInformation info;
	
	public AtmosphericInformationUpdater(AtmosphericInformation info) {
		this.info = info;
		
	}
	public void updateWind(Wind wind) {
			run();
			info.setWind(wind);
	}
	
	public void updateTemperature(Temperature temperature) {
		run();
		info.setTemperature(temperature);
	}
	
	public void updateHumidty(Humidty humidty) {
		run();
		info.setHumidity(humidty);
	}
	
	public void updatePrecipitation(Precipitation precipitation) {
		run();
		info.setPrecipitation(precipitation);
	}
	
	public void updatePressure(Pressure pressure) {
		run();
		info.setPressure(pressure);
	}
	
	public void updateCloudCover(CloudCover cloudCover) {
		run();
		info.setCloudCover(cloudCover);
	}
	
	
	@Override
	public void run() {
		synchronized (info) {
			info.setLastUpdateTime(System.currentTimeMillis());
		}
		
	}

}
