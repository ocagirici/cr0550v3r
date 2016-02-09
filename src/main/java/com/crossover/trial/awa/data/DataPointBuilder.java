package com.crossover.trial.awa.data;
import com.crossover.trial.awa.exception.IncorrectDataPointName;
import com.google.gson.InstanceCreator;

public abstract class DataPointBuilder implements InstanceCreator<DataPoint> {
	
	private DataPointBuilder () {}

	public static DataPoint build(String type, int lower, int median, int upper, double mean, int count) throws Exception{
		if(type.compareToIgnoreCase("wind") == 0)
			return newWind(lower, median, upper, mean, count);
		else if(type.compareToIgnoreCase("temperature") == 0)
			return newTemperature(lower, median, upper, mean, count);
		else if(type.compareToIgnoreCase("humidity") == 0)
			return newHumidty(lower, median, upper, mean, count);
		else if(type.compareToIgnoreCase("pressure") == 0)
			return newPressure(lower, median, upper, mean, count);
		else if(type.compareToIgnoreCase("cloudcover") == 0)
			return newCloudCover(lower, median, upper, mean, count);
		else if(type.compareToIgnoreCase("precipitation") == 0)
			return newPrecipitation(lower, median, upper, mean, count);
		else
			throw new IncorrectDataPointName();

	}
	
	public static DataPoint build(DataPoint dp) throws Exception {
		return build(dp.getType(), dp.getLower(), dp.getMedian(), dp.getUpper(), dp.getMean(), dp.getCount());
		
	}
	private static Wind newWind(int lower, int median, int upper, double mean, int count) throws Exception {
		Wind wind = new Wind(lower, median, upper, mean, count);
		
		return wind;
		
	}
	
	private static Temperature newTemperature(int lower, int median, int upper, double mean, int count) throws Exception {
		Temperature temperature = null;
		try {
			temperature = new Temperature(lower, median, upper, mean, count);
		} catch (Exception e) {
			throw e;
		}
		return temperature;
	}
	
	private static Humidty newHumidty(int lower, int median, int upper, double mean, int count) throws Exception {
		Humidty humidity = null;
		try {
			humidity = new Humidty(lower, median, upper, mean, count);
		} catch (Exception e) {
			throw e;
		}
		return humidity;
	}
	
	private static Pressure newPressure(int lower, int median, int upper, double mean, int count) throws Exception {
		Pressure pressure = null;
		try {
			pressure = new Pressure(lower, median, upper, mean, count);
		} catch (Exception e) {
			throw e;
		}
		return pressure;
	}
	
	private static CloudCover newCloudCover(int lower, int median, int upper, double mean, int count) throws Exception {
		CloudCover cloudCover = null;
		try {
			cloudCover = new CloudCover(lower, median, upper, mean, count);
		} catch (Exception e) {
			throw e;
		}
		return cloudCover;
	}
	
	private static Precipitation newPrecipitation(int lower, int median, int upper, double mean, int count) throws Exception {
		Precipitation precipitation = null;
		try {
			precipitation = new Precipitation(lower, median, upper, mean, count);
		} catch (Exception e) {
			throw e;
		}
		return precipitation;
	}


}
