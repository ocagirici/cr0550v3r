package com.crossover.trial.awa.weather.rest;

import com.crossover.trial.awa.airport.Airport;
import com.crossover.trial.awa.airport.AirportData;
import com.crossover.trial.awa.thread.AtmosphericInformation;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * The Weather App REST endpoint allows clients to query, update and check health stats. Currently, all data is
 * held in memory. The end point deploys to a single container
 *
 * @author code test administrator
 */
@Path("/query")
public class RestWeatherQueryEndpoint implements WeatherQueryEndpoint {
	
	private AirportDataManager airportDataManager;
	
	public final static Logger LOGGER = Logger.getLogger("WeatherQuery");
    public static final Gson gson = new Gson();
    public RestWeatherQueryEndpoint(AirportDataManager airportDataManager) {
    	this.airportDataManager = airportDataManager;
	}
    /**
     * Retrieve service health including total size of valid data points and request frequency information.
     *
     * @return health stats for the service as a string
     */
    @GET
    @Path("/ping")
    public String ping() {
        Map<String, Object> retval = new HashMap<>();
        
        int datasize = airportDataManager.recentInformation().size();
        retval.put("datasize", datasize);
        Map<String, Double> freq = new HashMap<>();
        
        
        // fraction of queries
        double totalSize = (double)airportDataManager.getRequestFrequency().size();
        
        int m = 0; // maximum radius
        
        for(Map.Entry<String, List<Double>> entry : airportDataManager.getRequestFrequency().entrySet()) {
        	double frac = (double)entry.getValue().size()/ totalSize;
        	freq.put(entry.getKey(), frac);
        	for(Double radius : entry.getValue())
        		if(radius.intValue() > m)
        			m = radius.intValue();
        			
        	
        }
        retval.put("iata_freq", freq);
        
        
        int[] hist = new int[m]; // histogram of requests
        for(Map.Entry<String, List<Double>> entry : airportDataManager.getRequestFrequency().entrySet()) {
        	for(Double radius : entry.getValue()) {
        		int i = radius.intValue() % 10;
        		hist[i] += entry.getValue().size();
        	}        	
        }
        retval.put("radius_freq", hist);
        System.out.println(retval + " " + System.currentTimeMillis());
        return gson.toJson(retval);
    }

    /**
     * Given a query in json format {'iata': CODE, 'radius': km} extracts the requested airport information and
     * return a list of matching atmosphere information.
     *
     * @param iata the iataCode
     * @param radiusString the radius in km
     *
     * @return a list of atmospheric information
     */
    @GET
    @Path("/weather/{iata}/{radius}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("iata") String iata, @PathParam("radius") String radiusString) {
        double radius = radiusString == null || radiusString.trim().isEmpty() ? 0 : Double.valueOf(radiusString);
        List<AtmosphericInformation> retval = new ArrayList<>();
        Airport base = airportDataManager.get(iata);
        retval.add(base.getInfo());
        if (radius == 0) {
            return Response.status(Response.Status.OK).entity(retval).build();
        } else {
        	for(Airport a : airportDataManager.getAirports())
        		if(base.calculateDistance(a) <=  radius)
        			if(a.getInfo().isValid())
        				retval.add(a.getInfo());
        }
        
        
        return Response.status(Response.Status.OK).entity(retval).build();
    }


    /**
     * Records information about how often requests are made
     *
     * @param iata an iata code
     * @param radius query radius
     */
    public void updateRequestFrequency(String iata, Double radius) {
        airportDataManager.addRequest(iata, radius);
    }



  
}
