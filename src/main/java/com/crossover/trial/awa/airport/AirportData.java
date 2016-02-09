package com.crossover.trial.awa.airport;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

// CR: General comment: the code should be divided into packages to be more organized.

/**
 * Basic airport information.
 *
 * @author code test administrator
 */
public class AirportData {

	private static int count = 0;
    String iata;
    double latitude;
    double longitude;

    public AirportData(String iataCode, double latitude, double longitude) { 
    	setIata(iataCode);
    	setLatitude(latitude);
    	setLongitude(longitude);
    	count++;
    } 

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public static int count() {
    	return count;
    }
    
 

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public boolean equals(Object other) { // CR: Better is AirportData implements Comparable<AirportData>
        if (other instanceof AirportData) {
            return ((AirportData)other).getIata().equals(this.getIata()); // CR: Better to use compareTo()
        }

        return false;
    }
}
