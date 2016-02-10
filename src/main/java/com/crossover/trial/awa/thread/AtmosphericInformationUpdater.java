package com.crossover.trial.awa.thread;

import com.crossover.trial.awa.data.*;
import com.crossover.trial.awa.exception.IncorrectDataPointName;

public class AtmosphericInformationUpdater extends Thread implements Runnable {

	private AtmosphericInformation info;
	private Thread updater;
	protected AtmosphericInformationUpdater() {}
	public AtmosphericInformationUpdater(AtmosphericInformation info, DataPoint dp) throws Exception {
		this.info = info;
		String type = dp.getType();
		if(type.compareToIgnoreCase("wind") == 0)
			updater = new Thread(new WindUpdater(info, (Wind) DataPointBuilder.build(dp)));
		else if(type.compareToIgnoreCase("temperature") == 0)
			updater = new Thread(new TemperatureUpdater(info, (Temperature) DataPointBuilder.build(dp)));
		else if(type.compareToIgnoreCase("humidity") == 0)
			updater = new Thread(new HumidtyUpdater(info, (Humidty) DataPointBuilder.build(dp)));
		else if(type.compareToIgnoreCase("pressure") == 0)
			updater = new Thread(new PressureUpdater(info, (Pressure) DataPointBuilder.build(dp)));
		else if(type.compareToIgnoreCase("cloudcover") == 0)
			updater = new Thread(new CloudCoverUpdater(info, (CloudCover) DataPointBuilder.build(dp)));
		else if(type.compareToIgnoreCase("precipitation") == 0)
			updater = new Thread(new PrecipitationUpdater(info, (Precipitation) DataPointBuilder.build(dp)));

	}

	

	@Override
	public void run() {
		synchronized (info) {
			updater.start();
			info.setLastUpdateTime(System.currentTimeMillis());
		}

	}

}
