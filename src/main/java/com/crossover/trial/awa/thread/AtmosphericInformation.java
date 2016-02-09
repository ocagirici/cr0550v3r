package com.crossover.trial.awa.thread;

import com.crossover.trial.awa.data.CloudCover;
import com.crossover.trial.awa.data.DataPoint;
import com.crossover.trial.awa.data.Humidty;
import com.crossover.trial.awa.data.Precipitation;
import com.crossover.trial.awa.data.Pressure;
import com.crossover.trial.awa.data.Temperature;
import com.crossover.trial.awa.data.Wind;
import com.crossover.trial.awa.exception.IncorrectDataPointName;
import com.crossover.trial.awa.exception.ValueOutOfBoundsException;

/**
 * encapsulates sensor information for a particular location
 */
public class AtmosphericInformation {
	

    /** temperature in degrees celsius */
    private Temperature temperature;

    /** wind speed in km/h */
    private Wind wind;

    /** humidity in percent */
    private Humidty humidty;

    /** precipitation in cm */
    private Precipitation precipitation;

    /** pressure in mmHg */
    private Pressure pressure;

    /** cloud cover percent from 0 - 100 (integer) */
    private CloudCover cloudCover;

    /** the last time this data was updated, in milliseconds since UTC epoch */
    private long lastUpdateTime;

   
    protected AtmosphericInformation(Temperature temperature, Wind wind, Humidty humidty, Precipitation precipitation, Pressure pressure, CloudCover cloudCover) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidty = humidty;
        this.precipitation = precipitation;
        this.pressure = pressure;
        this.cloudCover = cloudCover;
        
    }


	public Temperature getTemperature() {
        return temperature;
    }
    public void setTemperature(Temperature temperature) {
    	this.temperature = temperature;
    }
    public Wind getWind() {
        return wind;
    }
    public void setWind(Wind wind) {
        this.wind = wind;

    }
    public Humidty getHumidity() {
        return humidty;
    }
    public void setHumidity(Humidty humidty) {
        this.humidty = humidty;

    }
    public Precipitation getPrecipitation() {
        return precipitation;
    }
    
    public void setPrecipitation(Precipitation precipitation) {
        this.precipitation = precipitation;
    }
    
    public Pressure getPressure() {
        return pressure;
    }
    
    public void setPressure(Pressure pressure) {
        this.pressure = pressure;
    }
    
    public CloudCover getCloudCover() {
        return cloudCover;
    }
    
    public void setCloudCover(CloudCover cloudCover) {
        this.cloudCover = cloudCover;
    }
    
    protected long getLastUpdateTime() {
        return this.lastUpdateTime;
    }
    
    protected void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    protected void updateDataPoint(DataPoint dp) throws IncorrectDataPointName
    {
    	String type = dp.getType();
      	if(type.compareToIgnoreCase("wind") == 0)
			setWind((Wind) dp);
		else if(type.compareToIgnoreCase("temperature") == 0)
			setTemperature((Temperature) temperature);
		else if(type.compareToIgnoreCase("humidity") == 0)
			setHumidity((Humidty) humidty);
		else if(type.compareToIgnoreCase("pressure") == 0)
			setPressure((Pressure) pressure);
		else if(type.compareToIgnoreCase("cloudcover") == 0)
			setCloudCover((CloudCover) cloudCover);
		else if(type.compareToIgnoreCase("precipitation") == 0)
			setPrecipitation((Precipitation) precipitation);
		else
			throw new IncorrectDataPointName();
    }
    
    public boolean isValid() {
    	return getCloudCover() != null 
                || getHumidity() != null
                || getPressure() != null
                || getPrecipitation() != null
                || getTemperature() != null
                || getWind() != null;
    }
    
    public boolean olderThanOneDay() {
    	return lastUpdateTime < System.currentTimeMillis() - 86400000;
    }
}
