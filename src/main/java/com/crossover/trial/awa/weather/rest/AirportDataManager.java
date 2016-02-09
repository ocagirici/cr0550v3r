package com.crossover.trial.awa.weather.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.crossover.trial.awa.airport.Airport;
import com.crossover.trial.awa.airport.AirportData;
import com.crossover.trial.awa.thread.AtmosphericInformation;

public class AirportDataManager {
	
	
	
	/** all known airports */
	private HashMap<String, Airport> airports = new HashMap<>();
	private List<AtmosphericInformation> infoLog = new ArrayList<>();
	private List<AtmosphericInformation> recentInformation = new ArrayList<>();
	
	ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	Runnable periodicTask = new Runnable() {
	    public void run() {
	       	infoLog.addAll(recentInformation);
	    	recentInformation.clear();
	    }
	};

	private Map<String, List<Double>> requestFrequency = new HashMap<String, List<Double>>();

	public AirportDataManager() {
	}

	public AtmosphericInformation getAtmosphericInformation(int idx) {
		return null;
	}

	public void addAirport(AirportData ad) {
		airports.put(ad.getIata(), new Airport(ad));
	}
	
	public void addInfo(AtmosphericInformation info) {
		recentInformation.add(info);
		
	}
	
	public Airport get(String iata) {
		if(airports.containsKey(iata)) {
			return airports.get(iata);
		}
		else throw new IllegalArgumentException("No such airport");
	}
	
	public Set<String> getIatas() {
		return airports.keySet();
	}
	

	public Collection<Airport> getAirports() {
		return airports.values();
	}
	
	public Map<String, List<Double>> getRequestFrequency()
	{
		return Collections.unmodifiableMap(requestFrequency);
	}

	public void clear() {
		airports.clear();
		infoLog.clear();
		recentInformation.clear();
		
	}

	public void addRequest(String iata, Double radius) {
		if(!requestFrequency.containsKey(iata))
			requestFrequency.put(iata, new ArrayList<>());
		
		requestFrequency.get(iata).add(radius);
		
		
	}

	public List<AtmosphericInformation> recentInformation() {
		return recentInformation;
	}


}
