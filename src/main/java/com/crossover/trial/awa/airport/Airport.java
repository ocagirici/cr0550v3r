package com.crossover.trial.awa.airport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.crossover.trial.awa.data.DataPoint;
import com.crossover.trial.awa.thread.AtmosphericInformation;
import com.crossover.trial.awa.thread.AtmosphericInformationCreator;
import com.crossover.trial.awa.thread.AtmosphericInformationUpdater;

public class Airport {
    public static final double R = 6372.8;
	private AirportData data;
	private AtmosphericInformation info;
	private ArrayList<AtmosphericInformation> infoLog = new ArrayList<>();
	
	
	public Airport(AirportData data) {
		setData(data);
		info = null;
		
	}
	
	public AirportData getData() {
		return data;
	}

	public void setData(AirportData data) {
		this.data = data;
	}

	public List<AtmosphericInformation> getInfoLog() {
		return infoLog;
	}

	public void addInformation(AtmosphericInformation ai) {
		if(ai.isValid())
			this.infoLog.add(ai);
	}
	
	public void removeInformation(AtmosphericInformation ai) {
		if(infoLog.contains(ai)) {
			infoLog.remove(ai);
		}
		else throw new IllegalArgumentException("No such information in this airport");
	}


	public AtmosphericInformation getInfo() {
		return info;
	}
	
	/**
     * Haversine distance between two airports.
     *
     * @param ad1 airport 1
     * @param ad2 airport 2
     * @return the distance in KM
     */
    public double calculateDistance(Airport other) { 
    	double phi1 = Math.toRadians(this.getData().getLatitude());
    	double phi2 = Math.toRadians(other.getData().getLatitude());
    	double deltaPhi = Math.toRadians(other.getData().getLatitude() - this.getData().getLatitude());
    	double deltaLambda = Math.toRadians(other.getData().getLongitude() - this.getData().getLongitude());

    	double a = Math.sin(deltaPhi/2) * Math.sin(deltaPhi) +
    	        Math.cos(phi1) * Math.cos(phi2) *
    	        Math.sin(deltaLambda/2) * Math.sin(deltaLambda/2);
    	double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    	return R * c;
    }
	

}
